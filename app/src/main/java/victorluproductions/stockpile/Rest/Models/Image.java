package victorluproductions.stockpile.Rest.Models;

/**
 * Created by victorlu on 15-03-23.
 */
public class Image {

	private String url;
	private String tbUrl;
	private String originalContextUrl;
	private String publisher;
	private String tbWidth;
	private String tbHeight;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTbUrl() {
		return tbUrl;
	}

	public void setTbUrl(String tbUrl) {
		this.tbUrl = tbUrl;
	}

	public String getOriginalContextUrl() {
		return originalContextUrl;
	}

	public void setOriginalContextUrl(String originalContextUrl) {
		this.originalContextUrl = originalContextUrl;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTbWidth() {
		return tbWidth;
	}

	public void setTbWidth(String tbWidth) {
		this.tbWidth = tbWidth;
	}

	public String getTbHeight() {
		return tbHeight;
	}

	public void setTbHeight(String tbHeight) {
		this.tbHeight = tbHeight;
	}
}
