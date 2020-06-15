package bsm.profile;

import bsm.generics.Initializer;

public class SelectProfileInitializer extends Initializer<SelectProfileController> {

	public SelectProfileInitializer createInstance() {
		return (SelectProfileInitializer) super.createNewInstance("SelectProfile.fxml");
	}
	
	public SelectProfileInitializer openInDialog() {
		super.openInDialog("Select Profile Dialog");
		
		return this;
	}
}
