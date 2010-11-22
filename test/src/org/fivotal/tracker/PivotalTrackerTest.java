package org.fivotal.tracker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import org.fivotal.models.Story;
import org.fivotal.settings.Settings;


public class PivotalTrackerTest {
	private PivotalTracker tracker;
	
	@Before
	public void setUp() {
		tracker = new PivotalTracker();
	}
	
	@Test
	public void generateUrlGetsStoryIdAndProjectId() {
		assertEquals("http://www.pivotaltracker.com/services/v3/projects/projectId/stories/storyId", tracker.generateUrl("projectId", "storyId"));
	}
	
	@Test
	public void getStoryReturnsStringFromPivotal() throws Exception {
		// TODO:  Need to mock this out.
		Settings settings = mock(Settings.class);
		when(settings.getProjectId("green")).thenReturn("50298");
		when(settings.apiKey()).thenReturn("3f45685fb2f5c1ba8ca838764f8d09e4");
		
		Story returnedStory = tracker.getStory(settings.getProjectId("green"), "5982529", settings.apiKey());
		
		assertEquals("As a user, when I view a page on the /new-homes/ path, \nI should see the title, meta description, and meta keywords as described in the attached spreadsheet.", returnedStory.getDescription());
		assertEquals("5982529", returnedStory.getId());
		assertEquals("(8) Titles and Tags New-Homes Path", returnedStory.getTitle());
		assertEquals("", returnedStory.getOwnedBy());
		assertEquals("Joe Woods", returnedStory.getRequestedBy());
		assertEquals("accepted", returnedStory.getCurrentState());
		assertEquals("http://www.pivotaltracker.com/story/show/5982529", returnedStory.getUrl());
	}
}
