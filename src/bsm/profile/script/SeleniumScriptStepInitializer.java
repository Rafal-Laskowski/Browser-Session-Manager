package bsm.profile.script;

import java.util.Observer;

import bsm.exceptions.InstanceNotCreatedException;
import bsm.generics.Initializer;
import javafx.scene.control.TitledPane;

public class SeleniumScriptStepInitializer extends Initializer<SeleniumScriptStepController> {

	@Override
	public Initializer<SeleniumScriptStepController> createInstance() {
		return super.createNewInstance("SeleniumScriptStep.fxml");
	}

	public void setStepNumber(int stepNumber) {
		this.getController().setStepNumber(stepNumber);
	}
	
	public void addObserver(Observer o) {
		this.getController().addObserver(o);
	}

	public TitledPane getTitledPane() {
		TitledPane titledPane = this.getController().getTitledPane();
		if (titledPane != null) {
			return titledPane;
		} else {
			throw new InstanceNotCreatedException(this);
		}
	}
}
