package org.fivotal.net;

import java.util.HashMap;
import java.util.Map;

public class RequestPropertyManager {
	private Map<String, String> requestProperties;
	
	public RequestPropertyManager() {
		requestProperties = new HashMap<String, String>();
	}
	
	public void addRequestProperty(String key, String value) {
		requestProperties.put(key, value);
	}
	
	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}
}
