package bsm.session;

import java.net.URL;
import java.util.ResourceBundle;

import bsm.proxies.BrowserGUIProxy;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import laskowski.rafal.selenium.browsers.WebBrowser;

public class ActiveSessionController implements Initializable {

	@FXML
	private VBox activeSessionVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void addSingleSessionPane(WebBrowser browser) {
		SingleSessionController singleSessionController = new SingleSessionInitializer().createInstance()
				.getController();

		AnchorPane panel = singleSessionController.addSession(browser.getBrowserType());
		new BrowserGUIProxy(browser, singleSessionController);

		activeSessionVBox.getChildren().add(panel);
	}

	public void removeSingleSessionPane(SingleSessionController singleSessionController) {
		Platform.runLater(() -> activeSessionVBox.getChildren().remove(singleSessionController.getPanel()));
	}
}
