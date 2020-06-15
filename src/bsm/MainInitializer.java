package bsm;

import bsm.generics.Initializer;

public class MainInitializer extends Initializer<MainController> {

	public Initializer<MainController> createInstance() {
		return super.createNewInstance("Start.fxml");
	}
}
