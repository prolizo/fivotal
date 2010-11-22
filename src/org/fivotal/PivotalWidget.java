package org.fivotal;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fivotal.fitnesse.HtmlRenderer;
import org.fivotal.settings.Settings;
import org.fivotal.tracker.PivotalTracker;

import fitnesse.wikitext.WikiWidget;
import fitnesse.wikitext.widgets.ParentWidget;

public class PivotalWidget extends WikiWidget {

	public static final String REGEXP = "!pivotal\\{[\\w]+,[\\w]+\\}";
	private static final Pattern pattern = Pattern.compile("!pivotal\\{([\\w]+),([\\w]+)\\}");
	
	private String storyId;
	private String team;
	private Settings settings;
	private PivotalTracker tracker;
	
	public PivotalWidget(ParentWidget parent, String text) throws Exception {
		super(parent);
		final Matcher matcher = pattern.matcher(text);
		matcher.find();
		team = matcher.group(1);
		storyId = matcher.group(2);
		
		settings = new Settings(new Properties());
		settings.load("fivotal.properties");
		tracker = new PivotalTracker();
	}

	public String render() throws Exception {
		return HtmlRenderer.renderStory(tracker.getStory(settings.getProjectId(team), storyId, settings.apiKey()));
	}

	public String asWikiText() throws Exception {
		return "!pivotal{" + team + "," + storyId + "}";
	}

}