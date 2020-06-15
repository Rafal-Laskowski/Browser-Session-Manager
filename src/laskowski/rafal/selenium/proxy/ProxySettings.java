package laskowski.rafal.selenium.proxy;

import java.io.Serializable;

public class ProxySettings implements Serializable {
	private static final long serialVersionUID = 4763114077291136714L;
	private String url, port;
	
	public ProxySettings setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public ProxySettings setPort(String port) {
		this.port = port;
		return this;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getPort() {
		return port;
	}
	
	public String toString() {
		return url + ":" + port;
	}
}
