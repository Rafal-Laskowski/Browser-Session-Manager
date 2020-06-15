package bsm.session;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import laskowski.rafal.enums.BrowserType;
import laskowski.rafal.utils.ImageLoader;

public class SingleSessionController implements Initializable {

	@FXML
	private AnchorPane singleSessionPane;

	@FXML
	private BorderPane borderPane;

	@FXML
	private ImageView sessionImage;

	@FXML
	private ListView<String> logList;

	@FXML
	private Label cosnoleErrorsLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void addLog(String logMessage) {
		Platform.runLater(() -> logList.getItems().add(logMessage));
	}

	private AnchorPane getImageAndLogs(Image image) {
		sessionImage.setImage(image);

		return singleSessionPane;
	}

	private AnchorPane getImageWithoutLogs(Image image) {
		getImageAndLogs(image);

		logList.setVisible(false);
		cosnoleErrorsLabel.setVisible(false);

		borderPane.setTop(null);
		borderPane.setLeft(null);
		borderPane.setCenter(sessionImage);

		return singleSessionPane;
	}

	public AnchorPane getPanel() {
		return singleSessionPane;
	}

	public AnchorPane addSession(BrowserType browserType) {
		switch (browserType) {
		case CHROME:
			return addChromeSession();
		case FF:
			return addFirefoxSession();
		case IE:
			return addIESession();
		default:
			throw new IllegalArgumentException(browserType.name());
		}
	}

	public AnchorPane addChromeSession() {
		return getImageAndLogs(ImageLoader.load("chrome.png"));
	}

	public AnchorPane addFirefoxSession() {
		return getImageWithoutLogs(ImageLoader.load("firefox.png"));
	}

	public AnchorPane addIESession() {
		return getImageWithoutLogs(ImageLoader.load("ie.png"));
	}
}
