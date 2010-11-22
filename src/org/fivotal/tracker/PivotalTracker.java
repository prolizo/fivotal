package org.fivotal.tracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.fivotal.models.Story;

public class PivotalTracker {

	public String generateUrl(String projectId, String storyId) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://www.pivotaltracker.com/services/v3/projects/")
				 .append(projectId).append("/stories/").append(storyId);
		
		return urlString.toString();
	}

	public Story getStory(String projectId, String storyId, String apiKey) throws Exception {
		URL url = new URL(generateUrl(projectId, storyId));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("X-TrackerToken", apiKey);
		connection.connect();
		
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append('\n'); // preserve new lines in the response
			}
			in.close();
			
			InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(sb.toString()));
			Document doc = db.parse(is);
			in.close();
			
			NodeList stories = doc.getElementsByTagName("story");
			Element storyElement = (Element) stories.item(0); // should be only one story
			
			return generateStory(storyElement);
		} else {
			Story story = new Story();
			story.setId(storyId);
			story.setDescription("Unable to get story information from Pivotal Tracker.");
			
			return story;
		}
	}
	
	private Story generateStory(Element storyElement) {
		Story story = new Story();
		story.setDescription(getNodeValue(storyElement, "description")); 
		story.setId(getNodeValue(storyElement, "id"));
		story.setTitle(getNodeValue(storyElement, "name"));
		story.setRequestedBy(getNodeValue(storyElement, "requested_by"));
		story.setOwnedBy(getNodeValue(storyElement, "owned_by"));
		story.setCurrentState(getNodeValue(storyElement, "current_state"));
		story.setUrl(getNodeValue(storyElement, "url"));
		
		return story;
	}
	
	private String getNodeValue(Element element, String tag) {
		try {
			return element.getElementsByTagName(tag).item(0).getFirstChild().getNodeValue();
		} catch (NullPointerException e) {
			return "";
		}
	}
	
	/* Example Response from Pivotal
	 * 
	 * <?xml version="1.0" encoding="UTF-8"?>
		<story>
		  <id type="integer">5982529</id>
		  <project_id type="integer">50298</project_id>
		  <story_type>feature</story_type>
		  <url>http://www.pivotaltracker.com/story/show/5982529</url>
		  <estimate type="integer">2</estimate>
		  <current_state>accepted</current_state>
		  <description>As a user, when I view a page on the /new-homes/ path, 
		I should see the title, meta description, and meta keywords as described in the attached spreadsheet.</description>
		  <name>(8) Titles and Tags New-Homes Path</name>
		  <requested_by>Joe Woods</requested_by>
		  <created_at type="datetime">2010/11/01 13:41:28 EDT</created_at>
		  <updated_at type="datetime">2010/11/19 12:38:36 EST</updated_at>
		  <accepted_at type="datetime">2010/11/19 12:38:36 EST</accepted_at>
		  <labels>ahg,p1,seo,seo_accepted</labels>
		  <notes type="array">
		    <note>
		      <id type="integer">3158047</id>
		      <text>SEO Accepted</text>
		      <author>Joe Woods</author>
		      <noted_at type="datetime">2010/11/18 14:55:12 EST</noted_at>
		    </note>
		    <note>
		      <id type="integer">3176275</id>
		      <text>QA Pass</text>
		      <author>Charles Tootle</author>
		      <noted_at type="datetime">2010/11/19 12:38:32 EST</noted_at>
		    </note>
		  </notes>
		  <attachments type="array">
		    <attachment>
		      <id type="integer">687329</id>
		      <filename>New-Homes_Path_titles___tags_-_Joe.xlsx</filename>
		      <description></description>
		      <uploaded_by>Joe Woods</uploaded_by>
		      <uploaded_at type="datetime">2010/11/01 13:42:05 EDT</uploaded_at>
		      <url>http://www.pivotaltracker.com/resource/download/687329</url>
		    </attachment>
		  </attachments>
		</story>
	 * 
	 */
}
