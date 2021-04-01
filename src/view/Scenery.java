package view;

import controller.Connector;
import controller.Sender;
import javafx.stage.Stage;

public interface Scenery {
	void view(Stage st, Sender send, Connector connectDB);
}
