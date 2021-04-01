package view;

import java.sql.SQLException;

import controller.Connector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mail.system.Email;
import mail.system.IllegalEmailException;

public class SignUpView {

	private String pathIcon = "resources/mailIcon.png";

	public void view(Stage st, Connector connectDB) {

		// TODO Auto-generated method stub
		Label email = new Label("Email :");
		email.setTextFill(Color.BLACK);
		email.setFont(Font.font("Calibri", FontWeight.MEDIUM, 20));

		TextField email1 = new TextField();
		email1.setPromptText("Enter your username");

		HBox h = new HBox();
		h.setSpacing(20);

		h.getChildren().addAll(email, email1);

		Label us = new Label("Username :");
		us.setTextFill(Color.BLACK);
		us.setFont(Font.font("Calibri", FontWeight.MEDIUM, 20));

		TextField us1 = new TextField();
		us1.setPromptText("Enter your username");

		HBox h1 = new HBox();
		h1.setSpacing(20);

		h1.getChildren().addAll(us, us1);

		Label pass = new Label("Password :");
		pass.setTextFill(Color.BLACK);
		pass.setFont(Font.font("Calibri", FontWeight.MEDIUM, 20));

		PasswordField pass1 = new PasswordField();
		pass1.setPromptText("Enter your password ");

		HBox h2 = new HBox();
		h2.setSpacing(25);

		h2.getChildren().addAll(pass, pass1);

		Button enter = new Button("Enter");
		enter.getStyleClass().add("enterButton");
		enter.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String username = us1.getText();
				String password = pass1.getText();
				String email = email1.getText();

				if (username == null || password == null || email == null) {
					Alert a = new Alert(AlertType.WARNING, "One of the fields is empty", ButtonType.OK);
					a.show();
				} else {
					 try {
						connectDB.addANewEmail(username, password, new Email(email));
					} catch (SQLException | IllegalEmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				try {
					(new LogIN()).start(st);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Button cancel = new Button("Cancel");
		cancel.getStyleClass().add("enterButton");
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					(new LogIN()).start(st);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		HBox h3 = new HBox();
		h3.setSpacing(25);
		h3.setAlignment(Pos.CENTER);

		h3.getChildren().addAll(enter, cancel);

		GridPane main = new GridPane();

		/*
		 * Adding Children to the main Grid Pane
		 */
		main.add(h, 0, 1);
		main.add(h1, 0, 2);
		main.add(h2, 0, 3);
		main.add(h3, 0, 4);
		
		main.setPadding(new Insets(10, 10, 10, 10));
		main.setVgap(10);
		main.getStyleClass().add("mainGridPane");

		Scene sc = new Scene(main, 400, 200);
		sc.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		st.getIcons().add(new Image(pathIcon));
		st.setTitle("Email");
		st.setScene(sc);
		st.show();

	}

}
