package bsm.profile;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreateNewProfileButtonController implements Initializable {

	@FXML
	public void openProfileCreationDialog() {
		new ProfileInitializer().createInstance().openInDialog("Create Profile Dialog");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
