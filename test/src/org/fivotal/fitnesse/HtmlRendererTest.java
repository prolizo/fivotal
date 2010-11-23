package org.fivotal.fitnesse;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import org.fivotal.models.Story;

public class HtmlRendererTest {
	
	private Story story;
	
	@Before
	public void setUp() {
		story = new Story();
		story.setId("6020029");
		story.setTitle("story title/name");
		story.setCurrentState("accepted");
		story.setOwnedBy("Owner");
		story.setRequestedBy("Requester");
		story.setUrl("http://www.pivotaltracker.com/story/show/id");
	}
	
	private String getExpectedString(String description) {
		return "<div class=\"collapse_rim\">\n" + 
			   "\t<div class=\"meta\" style=\"float: right;\">\n" + 
			   "\t\t<a href=\"javascript:expandAll();\">Expand All</a>\n" +
			   " | \n" + 
			   "\t\t<a href=\"javascript:collapseAll();\">Collapse All</a>\n" + 
			   "\t</div>\n<a href=\"javascript:toggleCollapsable('6020029');\">" + 
			   "<img src=\"/files/images/collapsableClosed.gif\" class=\"left\" id=\"img6020029\"></a>&nbsp;\n" + 
			   "\t<span class=\"meta\">Story ID: 6020029</span>\n" + 
			   "\t<div class=\"hidden\" id=\"6020029\">\n\t\t<b>story title/name</b>\n<br/><br/>\n" + 
			   "\t\t<i>Requested By:&nbsp;Requester</i>\n<br/><br/>\n" + 
			   "\t\t<i>Owned By:&nbsp;Owner</i>\n<br/><br/>\n" + 
			   "\t\t<i>Current state:&nbsp;accepted</i>\n<br/><br/>" + 
			   description + "<br/><br/>\n" + 
			   "\t\t<a href=\"http://www.pivotaltracker.com/story/show/id\">Pivotal Tracker Story</a>\n\t</div>\n</div>\n";
	}
	
	@Test
	public void renderReturnsHtmlForStoryTag() {
		story.setDescription("story description");
		
		assertEquals(getExpectedString("story description"), HtmlRenderer.renderStory(story));
	}
	
	@Test
	public void renderReturnsHtmlForStoryTagWithNewLinesInDescription() {
		story.setDescription("As a user,\nGiven I do something,\nI should see something.");
		
		assertEquals(getExpectedString("As a user,<br/>Given I do something,<br/>I should see something."), HtmlRenderer.renderStory(story));
	}
}
