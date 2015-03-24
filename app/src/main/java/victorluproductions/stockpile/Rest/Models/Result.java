package victorluproductions.stockpile.Rest.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorlu on 15-03-23.
 */
public class Result {
	private String GsearchResultClass;
	private String clusterUrl;
	private String content;
	private String unescapedUrl;
	private String url;
	private String title;
	private String titleNoFormatting;
	private String location;
	private String publisher;
	private String publishedDate;
	private String signedRedirectUrl;
	private String language;
	private Image image;
	private List<RelatedStory> relatedStories = new ArrayList<RelatedStory>();

	public String getGsearchResultClass() {
		return GsearchResultClass;
	}

	public void setGsearchResultClass(String GsearchResultClass) {
		this.GsearchResultClass = GsearchResultClass;
	}

	public String getClusterUrl() {
		return clusterUrl;
	}

	public void setClusterUrl(String clusterUrl) {
		this.clusterUrl = clusterUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUnescapedUrl() {
		return unescapedUrl;
	}

	public void setUnescapedUrl(String unescapedUrl) {
		this.unescapedUrl = unescapedUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleNoFormatting() {
		return titleNoFormatting;
	}

	public void setTitleNoFormatting(String titleNoFormatting) {
		this.titleNoFormatting = titleNoFormatting;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getSignedRedirectUrl() {
		return signedRedirectUrl;
	}

	public void setSignedRedirectUrl(String signedRedirectUrl) {
		this.signedRedirectUrl = signedRedirectUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<RelatedStory> getRelatedStories() {
		return relatedStories;
	}

	public void setRelatedStories(List<RelatedStory> relatedStories) {
		this.relatedStories = relatedStories;
	}

}
