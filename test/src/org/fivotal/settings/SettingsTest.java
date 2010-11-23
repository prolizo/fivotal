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
	public void apiTokenReturnsKeyFromProperties() {
		when(properties.getProperty("api.key")).thenReturn("key");
		
		assertEquals("key", settings.apiKey());
	}
	
	@Test
	public void getProjectIdReturnsFromProperties() {
		when(properties.getProperty("green.id")).thenReturn("id");
		
		assertEquals("id", settings.getProjectId("green"));
	}
	
	@Test(expected=FileNotFoundException.class)
	public void loadShouldThrowExceptionSinceFileDoesNotExist() throws FileNotFoundException, IOException {
		settings.load("fivotal.properties");
	}
}
