package view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.Connector;
import controller.Sender;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mail.system.Email;
import mail.system.IllegalEmailException;

public class SendView implements Scenery {

	private String pathIcon = "resources/mailIcon.png";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	
	@Override
	public void view(Stage st, Sender sender, Connector connectDB) {
		// TODO Auto-generated method stub

		Menu m1 = new Menu("Home");
		m1.getStyleClass().add("menuBar");

		m1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					(new LogIN()).start(st);
				} catch (Exception e1) {
					System.err.println("Log In Does Not Function");
				}
			}

		});

		Menu m2 = new Menu("Content");
		m2.getStyleClass().add("menuBar");

		MenuItem sent = new MenuItem("Sent");
		MenuItem received = new MenuItem("Received");

		m2.getItems().add(sent);
		m2.getItems().add(received);

		sent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				(new SentEmails()).view(st, sender, connectDB);
			}

		});
		received.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				(new ReceivedEmails()).view(st, sender, connectDB);
			}

		});

		MenuBar mb = new MenuBar();

		mb.getMenus().add(m1);
		mb.getMenus().add(m2);

		TextField rcTxt = new TextField();
		rcTxt.setPromptText("To :");

		VBox h1 = new VBox();
		h1.setSpacing(20);
		h1.getChildren().addAll(rcTxt);

		TextField bcc1 = new TextField();
		bcc1.setPromptText("BCC :");

		VBox h2 = new VBox();
		h2.setSpacing(25);
		h2.getChildren().addAll(bcc1);

		TextField cc1 = new TextField();
		cc1.setPromptText("CC :");

		VBox h3 = new VBox();
		h3.setSpacing(20);
		h3.getChildren().addAll(cc1);

		TextField subject1 = new TextField();
		subject1.setPromptText("Subject :");

		VBox h4 = new VBox();
		h4.setSpacing(20);
		h4.getChildren().addAll(subject1);

		Label content = new Label("Message :");
		content.setTextFill(Color.BLACK);
		content.setFont(Font.font("Calibri", FontWeight.LIGHT, 20));

		TextArea content1 = new TextArea();

		VBox h5 = new VBox();
		h5.setSpacing(20);
		h5.getChildren().addAll(content, content1);

		Button Send = new Button("Send");
		Send.getStyleClass().add("enterButton");
		
		//Separate sends into different methods if cc is empty or if both bcc and cc are left empty;
		//Add a if clause where you check if any of the necessary fields is empty. ( to, subject, message);
		//Treat subject as necessary because it is used deletion at the emails database.
		
		Send.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				LocalDateTime now = LocalDateTime.now(); 
								
				try {
					
					List<Email> to = connectDB.getAllEmailsFromString(rcTxt.getText());
					List<Email> bcc = connectDB.getAllEmailsFromString(bcc1.getText());
					List<Email> cc = connectDB.getAllEmailsFromString(bcc1.getText());
					String subject = subject1.getText();
					String content = content1.getText();
					String date = dtf.format(now);
					
					connectDB.addNewContent(new Email(sender.getUsername()), to, bcc, cc, subject, content, date);
					sender.send(to, bcc, cc, subject, content);
					
					(new SendView()).view(st, sender, connectDB);
					
				} catch (IllegalEmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NullPointerException e2) {
					System.out.println("Null Pointer Exception");
				}
				
			}
		});

		Button logOut = new Button("Log Out");
		logOut.getStyleClass().add("cancelButton");
		logOut.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				try {
					(new LogIN()).start(st);
				} catch (Exception e1) {
					System.err.println("Log In Does Not Function");
				}
			}

		});

		
		HBox h6 = new HBox();
		h6.setSpacing(25);
		h6.setAlignment(Pos.CENTER);

		h6.getChildren().addAll(Send, logOut);

		GridPane main = new GridPane();

		/*
		 * Adding Children to the main Grid Pane
		 */

		main.add(mb, 0, 0);
		main.add(h1, 0, 2);
		main.add(h2, 0, 3);
		main.add(h3, 0, 4);
		main.add(h4, 0, 5);
		main.add(h5, 0, 6);
		main.add(h6, 0, 7);

		main.setPadding(new Insets(10, 10, 10, 10));
		main.setVgap(10);
		main.getStyleClass().add("mainGridPane");

		Scene sc = new Scene(main, 600, 600);
		sc.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		st.getIcons().add(new Image(pathIcon));
		st.setTitle("Email");
		st.setScene(sc);
		st.show();
	}

}
