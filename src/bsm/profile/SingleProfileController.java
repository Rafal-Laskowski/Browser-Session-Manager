package bsm.profile;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import bsm.generics.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import laskowski.rafal.selenium.profiles.DefaultProfile;
import laskowski.rafal.selenium.profiles.Profile;
import laskowski.rafal.utils.ImageLoader;

public class SingleProfileController implements Initializable {
	private String selectedProfileName;

	@FXML
	private AnchorPane singleProfilePane;

	@FXML
	private Button btnProfile;

	@FXML
	private ImageView imageView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setImage(ImageLoader.load("trinity.png"));
	}

	@FXML
	private void setProfile(ActionEvent actionEvent) {
		selectedProfileName = btnProfile.getText();
		Dialog.close(actionEvent);
	}

	public Optional<Profile> getProfile() {
		if (selectedProfileName != null) {
			DefaultProfile profile = new DefaultProfile();
			return Optional.of(profile.load(selectedProfileName));
		}

		return Optional.empty();
	}

	public void setProfile(Profile profile) {
		btnProfile.setText(profile.getName());
	}
}
