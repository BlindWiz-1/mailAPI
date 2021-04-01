package view;

import java.util.List;

import controller.Connector;
import controller.Sender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mail.system.Email;
import mail.system.EmailData;
import mail.system.IllegalEmailException;

public class SentEmails implements Scenery {

	private String pathIcon = "resources/mailIcon.png";
	private ObservableList<EmailData> data = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	@Override
	public void view(Stage st, Sender send, Connector connectDB) {
		// TODO Auto-generated method stub

		Button goBack = new Button("Go Back");
		goBack.getStyleClass().add("menuBar");
		goBack.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				(new SendView()).view(st, new Sender(send.getUsername(), send.getPassword()), connectDB);

			}

		});

		HBox h = new HBox();
		h.setAlignment(Pos.CENTER_LEFT);
		h.setSpacing(20);
		h.getChildren().add(goBack);

		TableView<EmailData> table = new TableView<>();

		TableColumn<EmailData, Email> column0 = new TableColumn<>("Sender");
		column0.setMinWidth(220);
		column0.setCellValueFactory(new PropertyValueFactory<>("sender"));
		column0.getStyleClass().add("column");

		TableColumn<EmailData, List<Email>> column1 = new TableColumn<>("To");
		column1.setMinWidth(220);
		column1.setCellValueFactory(new PropertyValueFactory<>("to"));
		column1.getStyleClass().add("column");

		TableColumn<EmailData, List<Email>> column2 = new TableColumn<>("BCC");
		column2.setMinWidth(220);
		column2.setCellValueFactory(new PropertyValueFactory<>("bcc"));
		column2.getStyleClass().add("column");

		TableColumn<EmailData, List<Email>> column3 = new TableColumn<>("CC");
		column3.setMinWidth(220);
		column3.setCellValueFactory(new PropertyValueFactory<>("cc"));
		column3.getStyleClass().add("column");

		TableColumn<EmailData, String> column4 = new TableColumn<>("Subject");
		column4.setMinWidth(220);
		column4.setCellValueFactory(new PropertyValueFactory<>("subject"));
		column4.getStyleClass().add("column");

		TableColumn<EmailData, String> column5 = new TableColumn<>("Content");
		column5.setMinWidth(220);
		column5.setCellValueFactory(new PropertyValueFactory<>("content"));
		column5.getStyleClass().add("column");

		TableColumn<EmailData, String> column6 = new TableColumn<>("Date");
		column6.setMinWidth(220);
		column6.setCellValueFactory(new PropertyValueFactory<>("date"));
		column6.getStyleClass().add("column");

		table.getColumns().addAll(column0, column1, column2, column3, column4, column5, column6);

		try {
			data = getEmails(new Email(send.getUsername()));
		} catch (IllegalEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setItems(data);
		table.setEditable(true);

		Button delete = new Button("Delete");
		delete.getStyleClass().add("cancelButton");
		delete.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

			}

		});

		HBox h0 = new HBox();
		h0.setAlignment(Pos.CENTER);
		h0.setSpacing(20);
		h0.getChildren().addAll(delete);

		VBox v1 = new VBox();
		v1.setAlignment(Pos.CENTER);
		v1.setSpacing(20);
		v1.getChildren().addAll(table, h0);

		GridPane main = new GridPane();

		/*
		 * Adding Children to the main Grid Pane
		 */

		main.add(h, 0, 0);
		main.add(v1, 0, 1);

		main.setVgap(30);
		main.setAlignment(Pos.CENTER);
		main.getStyleClass().add("mainGridPane");

		Scene sc = new Scene(main, 1500, 600);
		sc.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		st.getIcons().add(new Image(pathIcon));
		st.setTitle("Sent");
		st.setScene(sc);
		st.show();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ObservableList<EmailData> getEmails(Email to) {

		Connector connect = new Connector();
		Object[] list = connect.getEmailsSendBy(to).toArray();
		Text[] arr = new Text[list.length];

		for (int i = 0; i < list.length; i++) {

			arr[i] = new Text("Sender: " + ((EmailData) list[i]).getSender() + "To: " + ((EmailData) list[i]).getTo()
					+ "BCC: " + ((EmailData) list[i]).getBcc() + "CC: " + ((EmailData) list[i]).getCc() + "Subject: "
					+ ((EmailData) list[i]).getSubject() + "Content: " + ((EmailData) list[i]).getContent() + "Date: "
					+ ((EmailData) list[i]).getDate());

		}

		ObservableList data = FXCollections.observableArrayList(list);
		return data;

	}

}
