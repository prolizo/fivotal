package org.fivotal.net;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestPropertyManagerTest {
	private RequestPropertyManager manager;
	
	@Before
	public void setUp() {
		manager = new RequestPropertyManager();
	}
	
	@Test
	public void addRequestPropertyAddsToMap() {
		assertEquals(0, manager.getRequestProperties().size());
		
		manager.addRequestProperty("key", "value");
		
		Map<String, String> requestProperties = manager.getRequestProperties();
		
		assertEquals(1, requestProperties.size());
		assertEquals("value", requestProperties.get("key"));
	}
}
