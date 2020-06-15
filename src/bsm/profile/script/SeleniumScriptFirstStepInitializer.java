package bsm.profile.script;

import bsm.generics.Initializer;
import javafx.scene.control.TitledPane;

public class SeleniumScriptFirstStepInitializer extends Initializer<SeleniumScriptFirstStepController> {

	@Override
	public Initializer<SeleniumScriptFirstStepController> createInstance() {
		return super.createNewInstance("SeleniumScriptFirstStep.fxml");
	}
	
	public TitledPane getTitledPane() {
		return this.getController().getTitledPane();
	}

}
