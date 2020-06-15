package bsm.profile;

import bsm.generics.Initializer;
import laskowski.rafal.selenium.profiles.Profile;

public class ProfileInitializer extends Initializer<ProfileController> {

	public ProfileInitializer createInstance() {
		return createInstance(null);
	}

	public ProfileInitializer createInstance(Profile profile) {
		ProfileInitializer initializer = (ProfileInitializer) super.createNewInstance("ProfilePanel.fxml");
		if (profile != null) {
			ProfileController controller = initializer.getController();
			controller.applyProfile(profile);
		}
		return initializer;
	}

	public ProfileInitializer openInDialog(String dialogName) {
		super.openInDialog(dialogName);
		
		return this;
	}
}
