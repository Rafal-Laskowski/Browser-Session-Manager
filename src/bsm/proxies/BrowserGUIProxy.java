package bsm.proxies;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import bsm.session.ActiveSessionInitializer;
import bsm.session.SingleSessionController;
import laskowski.rafal.selenium.browsers.ConsoleLogs;
import laskowski.rafal.selenium.browsers.SessionChanged;
import laskowski.rafal.selenium.browsers.WebBrowser;

public class BrowserGUIProxy implements Observer {
	private SingleSessionController singleSessionController;

	public BrowserGUIProxy(WebBrowser browser, SingleSessionController singleSessionController) {
		browser.addObserver(this);
		this.singleSessionController = singleSessionController;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SessionChanged) {
			new ActiveSessionInitializer().getInstance().getController().removeSingleSessionPane(singleSessionController);
		} else if (arg instanceof ConsoleLogs) {
			List<String> logs = ((ConsoleLogs) arg).getLogs();

			logs.forEach(log -> singleSessionController.addLog(log));
		}
	}
}
