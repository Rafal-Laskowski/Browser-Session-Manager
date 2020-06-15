package bsm.session;

import bsm.generics.Initializer;

public class ActiveSessionInitializer extends Initializer<ActiveSessionController> {

	public ActiveSessionInitializer createInstance() {
		return (ActiveSessionInitializer) super.createNewInstance("ActiveSessionPanel.fxml");
	}
	
	public ActiveSessionInitializer getInstance() {
		return (ActiveSessionInitializer) super.getInstance();
	}
}
