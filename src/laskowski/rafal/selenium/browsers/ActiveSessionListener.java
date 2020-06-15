package laskowski.rafal.selenium.browsers;

public class ActiveSessionListener extends Thread implements Runnable {
	private WebBrowser browser;
	private volatile boolean running = true;

	public ActiveSessionListener(WebBrowser browser) {
		this.browser = browser;
	}

	@Override
	public void run() {
		while (this.isAlive() && running) {
			if (!browser.isActive()) {
				 browser.stop();
			} else {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					terminate();
				}
			}
		}
	}

	public void terminate() {
		running = false;
	}

}
