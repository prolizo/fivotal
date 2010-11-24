package org.fivotal.tracker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import org.fivotal.models.Story;
import org.fivotal.net.HttpManager;

public class PivotalTrackerTest {
	private PivotalTracker tracker;
	private HttpManager httpManager;
	
	@Before
	public void setUp() {
		httpManager = mock(HttpManager.class);
		tracker = new PivotalTracker(httpManager);
	}
	
	@Test
	public void generateUrlGetsStoryIdAndProjectId() {
		assertEquals("http://www.pivotaltracker.com/services/v3/projects/projectId/stories/storyId", tracker.generateUrl("projectId", "storyId"));
	}
	
	@Test
	public void getStoryReturnsStringFromPivotal() throws Exception {
		String storyResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							   "\t\t<story>\n" + 
							   "\t\t\t<id type=\"integer\">storyId</id>\n" +
							   "\t\t\t<project_id type=\"integer\">project_id</project_id>\n" + 
							   "\t\t\t<story_type>feature</story_type>\n" +
							   "\t\t\t<url>http://www.pivotaltracker.com/story/show/storyId</url>\n" +
							   "\t\t\t<current_state>accepted</current_state>\n" + 
							   "\t\t\t<description>As a user, when I do something, \nI should see something else.</description>\n" +
							   "\t\t\t<name>story title</name>\n" +
							   "\t\t\t<requested_by>Person A</requested_by>\n" + 
							   "\t\t</story>";
		when(httpManager.get("http://www.pivotaltracker.com/services/v3/projects/projectId/stories/storyId")).thenReturn(storyResponse);
		
		Story returnedStory = tracker.getStory("projectId", "storyId");
		
		assertEquals("As a user, when I do something, \nI should see something else.", returnedStory.getDescription());
		assertEquals("storyId", returnedStory.getId());
		assertEquals("story title", returnedStory.getTitle());
		assertEquals("", returnedStory.getOwnedBy());
		assertEquals("Person A", returnedStory.getRequestedBy());
		assertEquals("accepted", returnedStory.getCurrentState());
		assertEquals("http://www.pivotaltracker.com/story/show/storyId", returnedStory.getUrl());
	}
}
