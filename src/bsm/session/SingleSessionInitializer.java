package bsm.session;

import bsm.generics.Initializer;

public class SingleSessionInitializer extends Initializer<SingleSessionController> {

	public SingleSessionInitializer createInstance() {
		return (SingleSessionInitializer) super.createNewInstance("SingleSessionPanel.fxml");
	}
}
