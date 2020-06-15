package bsm.profile;

import bsm.generics.Initializer;

public class CreateNewProfileButtonInitializer extends Initializer<CreateNewProfileButtonController> {

	public CreateNewProfileButtonInitializer createInstance() {
		return (CreateNewProfileButtonInitializer) super.createNewInstance("CreateNewProfileButton.fxml");
	}
}
