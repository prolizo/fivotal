package org.fivotal.fitnesse;

import org.fivotal.models.Story;

import fitnesse.html.HtmlTag;
import fitnesse.html.HtmlUtil;

public class HtmlRenderer {
	public static String renderStory(Story story) {
		HtmlTag headDiv = HtmlUtil.makeDivTag("collapse_rim");
		headDiv.add(createExpandCollapseDiv());
		headDiv.add(createCollapsableArrow(story));
		headDiv.add(createStorySpan(story));
		
		headDiv.add(createHiddenStoryDiv(story));
		
		return headDiv.html();
	}
	
	private static HtmlTag createExpandCollapseDiv() {
		HtmlTag expandCollapseDiv = HtmlUtil.makeDivTag("meta");
		expandCollapseDiv.addAttribute("style", "float: right;");
		expandCollapseDiv.add(HtmlUtil.makeLink("javascript:expandAll();", "Expand All"));
		expandCollapseDiv.add(" | ");
		expandCollapseDiv.add(HtmlUtil.makeLink("javascript:collapseAll();", "Collapse All"));
		
		return expandCollapseDiv;
	}
	
	private static HtmlTag createHiddenStoryDiv(Story story) {
		HtmlTag hidden = HtmlUtil.makeDivTag("hidden");
		hidden.addAttribute("id", story.getId());
		story.setDescription(story.getDescription().replaceAll("\n", HtmlUtil.BRtag));
		hidden.add(HtmlUtil.makeBold(story.getTitle()));
		addLineBreak(hidden);
		hidden.add(HtmlUtil.makeItalic("Requested By:&nbsp;" + story.getRequestedBy()));
		addLineBreak(hidden);
		hidden.add(HtmlUtil.makeItalic("Owned By:&nbsp;" + story.getOwnedBy()));
		addLineBreak(hidden);
		hidden.add(HtmlUtil.makeItalic("Current state:&nbsp;" + story.getCurrentState()));
		addLineBreak(hidden);
		hidden.add(story.getDescription());
		addLineBreak(hidden);
		hidden.add(HtmlUtil.makeLink(story.getUrl(), "Pivotal Tracker Story"));
		
		return hidden;
	}
	
	private static HtmlTag createStorySpan(Story story) {
		return HtmlUtil.makeSpanTag("meta", "Story ID: " + story.getId());
	}
	
	private static String createCollapsableArrow(Story story) {
		return "<a href=\"javascript:toggleCollapsable('" + story.getId() + "');\"><img src=\"/files/images/collapsableClosed.gif\" class=\"left\" id=\"img" + story.getId() + "\"></a>&nbsp;";
	}
	
	private static void addLineBreak(HtmlTag tag) {
		tag.add(HtmlUtil.BR);
		tag.add(HtmlUtil.BR);
	}
}
