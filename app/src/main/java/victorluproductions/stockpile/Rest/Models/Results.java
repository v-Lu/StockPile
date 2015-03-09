package victorluproductions.stockpile.Rest.Models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorlu on 15-03-05.
 */
@Parcel
public class Results {

	private List<Quote> quote = new ArrayList<Quote>();

	public List<Quote> getQuotes() {
		return quote;
	}
}
