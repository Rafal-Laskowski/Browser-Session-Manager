package bsm.generics;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialog {
	private Parent parent;

	public Dialog(Parent parent) {
		this.parent = parent;
	}

	public void open(String title) {
		if (parent != null) {
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} else {
			throw new NullPointerException("Cannot create dialog. Parent element is null");
		}
	}

	public static void close(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}
