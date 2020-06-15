package bsm.profile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import bsm.generics.Initializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import laskowski.rafal.selenium.profiles.DefaultProfile;
import laskowski.rafal.selenium.profiles.Profile;

public class SelectProfileController implements Initializable {
	private List<SingleProfileController> profileControllers = new ArrayList<>();

	@FXML
	private FlowPane selectProfileFlowPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (!DefaultProfile.exist()) {
			createDefaultProfile();
		}

		DefaultProfile.loadAll().forEach(profile -> {
			Initializer<SingleProfileController> spi = new SingleProfileInitializer().createInstance();
			SingleProfileController controller = spi.getController();
			controller.setProfile(profile);
			profileControllers.add(controller);
			selectProfileFlowPane.getChildren().add(spi.getParent());
		});
	}

	public Profile getSelectedProfile() {
		Optional<Profile> optProf = profileControllers.stream().map(SingleProfileController::getProfile)
				.filter(Optional::isPresent).map(Optional::get).findFirst();
		return (optProf.orElse(null));
	}

	private void createDefaultProfile() {
		DefaultProfile defaultProfile = new DefaultProfile();
		defaultProfile.save();
	}
}
