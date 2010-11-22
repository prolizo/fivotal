package org.fivotal.settings;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SettingsTest {
	
	private Settings settings;
	private Properties properties;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		properties = mock(Properties.class);
		settings = new Settings(properties);
	}
	
	@Test
	public void apiTokenReturnsProlizosKey() {
		when(properties.getProperty("api.key")).thenReturn("3f45685fb2f5c1ba8ca838764f8d09e4");
		
		assertEquals("3f45685fb2f5c1ba8ca838764f8d09e4", settings.apiKey());
	}
	
	@Test
	public void projectIdAlwaysReturnsGreenTeam() {
		when(properties.getProperty("green.id")).thenReturn("50298");
		
		assertEquals("50298", settings.getProjectId("green"));
	}
	
	@Test(expected=FileNotFoundException.class)
	public void loadShouldThrowExceptionSinceFileDoesNotExist() throws FileNotFoundException, IOException {
		settings.load("fivotal.properties");
	}
}
