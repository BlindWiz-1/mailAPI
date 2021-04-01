package view;

import controller.Connector;
import controller.Sender;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogIN extends Application {

	private String pathImage = "resources/images.jpg";
	private String pathIcon = "resources/mailIcon.png";
	private Connector connectDB = new Connector();

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(Stage st) throws Exception {

		DropShadow ds = new DropShadow();

		Text mail = new Text("Mail System");
		mail.getStyleClass().add("title");
		mail.setEffect(ds);
		mail.setFont(Font.font("Tahoma", FontWeight.MEDIUM, FontPosture.ITALIC, 70));

		Label us = new Label("Username :");
		us.setTextFill(Color.BLACK);
		us.setFont(Font.font("Calibri", FontWeight.MEDIUM, 20));

		HBox h = new HBox();
		h.setSpacing(20);

		h.getChildren().add(mail);

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
		pass1.requestFocus();

		pass1.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				if (e.getCode().equals(KeyCode.ENTER)) {

					checkLogIn(st, us1.getText(), pass1.getText());

				}
			}
		});

		HBox h2 = new HBox();
		h2.setSpacing(25);

		h2.getChildren().addAll(pass, pass1);

		Button Enter = new Button("Enter");
		Enter.getStyleClass().add("enterButton");

		Enter.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				checkLogIn(st, us1.getText(), pass1.getText());

			}

		});

		Button Cancel = new Button("Cancel");
		Cancel.getStyleClass().add("cancelButton");
		Cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				st.close();
			}
		});

		HBox h3 = new HBox();
		h3.setSpacing(25);
		h3.setAlignment(Pos.CENTER);

		h3.getChildren().addAll(Enter, Cancel);

		Text t = new Text("Are you new to Mail System? Sign up now!");
		t.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		t.setStyle("-fx-text-fill: rgb(22, 204, 91);");

		Button SignUp = new Button("Sign Up");
		SignUp.getStyleClass().add("signUpButton");
		SignUp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				(new SignUpView()).view(st, connectDB);
			}
		});

		VBox h4 = new VBox();
		h4.setAlignment(Pos.CENTER);
		h4.setSpacing(10);
		h4.getChildren().addAll(t, SignUp);

		Image i = new Image(pathImage);
		ImageView iv = new ImageView(i);
		HBox h0 = new HBox();
		h0.setSpacing(20);

		h0.setAlignment(Pos.CENTER);
		h0.getChildren().add(iv);

		GridPane main = new GridPane();

		/*
		 * Adding Children to the main Grid Pane
		 */
		main.add(h0, 0, 0);
		main.add(h, 0, 1);
		main.add(h1, 0, 2);
		main.add(h2, 0, 3);
		main.add(h3, 0, 4);
		main.add(h4, 0, 5);

		main.setVgap(30);
		main.setAlignment(Pos.CENTER);
		main.getStyleClass().add("mainGridPane");

		Scene sc = new Scene(main, 600, 600);
		sc.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		st.getIcons().add(new Image(pathIcon));
		st.setTitle("Log In");
		st.setScene(sc);
		st.show();
	}

	private void checkLogIn(Stage st, String username, String password) {
		
		if (connectDB.checkEmailAddress(username, password) == true) {

			String email = connectDB.getEmailGivenUsername(username).getEmail();
			(new SendView()).view(st, new Sender(email, password), connectDB);

		} else {
			Alert a = new Alert(AlertType.WARNING, "Wrong credentials", ButtonType.OK);
			a.show();
		}
	}

}
