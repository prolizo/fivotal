package org.fivotal.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpManager {
	private RequestPropertyManager manager;
	
	public HttpManager(RequestPropertyManager manager) {
		manager = new RequestPropertyManager();
	}
	
	public String get(String urlString) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		Map<String, String> requestProperties = manager.getRequestProperties();
		
		for(String key : requestProperties.keySet()) {
			connection.setRequestProperty(key, requestProperties.get(key));
		}
		
		connection.connect();
		
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append('\n'); // preserve new lines in the response
			}
			in.close();
			
			return sb.toString();
		} else {
			return "";
		}
	}
}
