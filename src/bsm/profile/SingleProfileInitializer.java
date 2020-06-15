package bsm.profile;

import bsm.generics.Initializer;

public class SingleProfileInitializer extends Initializer<SingleProfileController> {

	public Initializer<SingleProfileController> createInstance() {
		return super.createNewInstance("SingleProfilePanel.fxml");
	}
}
