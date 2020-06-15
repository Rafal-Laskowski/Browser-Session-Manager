package bsm.generics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bsm.exceptions.InstanceNotCreatedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class Initializer<TController> {
	private static List<Initializer<?>> cache = new ArrayList<>();
	private TController controller;
	private FXMLLoader loader;
	private Parent parent;

	public abstract Initializer<TController> createInstance();

	protected Initializer<TController> createNewInstance(String fxmlFile) {
		loader = new FXMLLoader(getClass().getResource(fxmlFile));
		try {
			parent = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		controller = loader.<TController>getController();

		cache.add(this);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Initializer<TController> getInstance() {
		Optional<Initializer<?>> a = cache.stream().filter(initializer -> initializer.compare(this)).findFirst();

		if (a.isPresent()) {
			return (Initializer<TController>) a.get();
		} else {
			throw new InstanceNotCreatedException(this);
		}
	}

	public boolean hasInstance() {
		return cache.stream().anyMatch(initializer -> initializer.compare(this));
	}

	public TController getController() {
		return controller;
	}

	public Parent getParent() {
		if (parent != null) {
			return parent;
		} else {
			throw new IllegalArgumentException(
					"No instance was created. Use `createNewInstance` method in child class");
		}
	}

	protected FXMLLoader getLoader() {
		return loader;
	}

	public boolean compare(Initializer<?> initializer) {
		return this.getClass().getGenericSuperclass().equals(initializer.getClass().getGenericSuperclass());
	}
	
	protected Initializer<TController> openInDialog(String dialogName) {
		new Dialog(getParent()).open(dialogName);
		
		return this;
	}
}
