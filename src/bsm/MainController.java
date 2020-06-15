package bsm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bsm.profile.CreateNewProfileButtonInitializer;
import bsm.profile.ProfileInitializer;
import bsm.profile.SelectProfileInitializer;
import bsm.session.ActiveSessionInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import laskowski.rafal.enums.BrowserType;
import laskowski.rafal.selenium.browsers.WebBrowser;
import laskowski.rafal.selenium.profiles.Profile;
import laskowski.rafal.utils.ImageLoader;

public class MainController implements Initializable {

	@FXML
	private ImageView chromeImage;

	@FXML
	private ImageView firefoxImage;

	@FXML
	private ImageView ieImage;

	@FXML
	private BorderPane activeSessionBorderPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chromeImage.setImage(ImageLoader.load("chrome.png"));
		firefoxImage.setImage(ImageLoader.load("firefox.png"));
		ieImage.setImage(ImageLoader.load("ie.png"));
	}

	public void setCenter(Node node) {
		activeSessionBorderPane.setCenter(node);
	}

	@FXML
	private void startChrome(ActionEvent event) {
		startBrowserProcess(BrowserType.CHROME);
	}

	@FXML
	private void startFirefox(ActionEvent event) throws IOException {
		startBrowserProcess(BrowserType.FF);
	}

	@FXML
	private void startIE(ActionEvent event) throws IOException {
		startBrowserProcess(BrowserType.IE);
	}

	@FXML
	private void addProfile() {
		new CreateNewProfileButtonInitializer().createInstance().getController().openProfileCreationDialog();
	}

	@FXML
	private void editProfile() {
		Profile profile = new SelectProfileInitializer().createInstance().openInDialog().getController()
				.getSelectedProfile();

		if (profile != null) {
			new ProfileInitializer().createInstance(profile).openInDialog("Edit Profile dialog");
		}
	}

	@FXML
	private void deleteProfile() {
		Profile profile = new SelectProfileInitializer().createInstance().openInDialog().getController()
				.getSelectedProfile();

		if (profile != null) {
			profile.delete();
		}
	}

	private void startBrowserProcess(BrowserType browserType) {
		Profile selectedProfile = showSelectProfilePanel();
		if (selectedProfile != null) {
			WebBrowser browser = new WebBrowser(selectedProfile);

			browser.start(browserType);
			browser.executeScript();

			addSessionPane(browser);
		}
	}

	private void addSessionPane(WebBrowser browser) {
		ActiveSessionInitializer asInit = new ActiveSessionInitializer();
		if (!asInit.hasInstance()) {
			asInit = asInit.createInstance();
		} else {
			asInit = asInit.getInstance();
		}

		this.setCenter(asInit.getParent());

		asInit.getController().addSingleSessionPane(browser);
	}

	private Profile showSelectProfilePanel() {
		return new SelectProfileInitializer().createInstance().openInDialog().getController().getSelectedProfile();
	}
}
