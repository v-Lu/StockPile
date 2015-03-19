package victorluproductions.stockpile.Rest.Models;

import java.util.ArrayList;
import java.util.List;

import victorluproductions.stockpile.Rest.CustomResultsDeserializer;

/**
 * Created by victorlu on 15-03-05.
 */
public class Results {

	private List<Quote> quotes = new ArrayList<Quote>();

	public Results(ArrayList<Quote> quotes) {
		this.quotes = quotes;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}
}
