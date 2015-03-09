package victorluproductions.stockpile.Rest.Models;

/**
 * Created by victorlu on 15-03-05.
 */

public class Query {

	private int count;
	private String created;
	private String lang;
	private Results results;

	public int getCount() {
		return count;
	}

	public String getCreated() {
		return created;
	}

	public String getLang() {
		return lang;
	}

	public Results getResults() {
		return results;
	}
}
