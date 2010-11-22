package org.fivotal.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private Properties properties;
	
	public Settings(Properties props) {
		properties = props;
	}
	
	public String apiKey() {
		return properties.getProperty("api.key");
	}

	public String getProjectId(String teamName) {
		return properties.getProperty(teamName + ".id");
	}

	public void load(String fileName) throws FileNotFoundException, IOException {
		properties.load(new FileInputStream(fileName));
	}

}
