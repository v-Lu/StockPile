package victorluproductions.stockpile.Rest.Models;

/**
 * Created by victorlu on 15-03-02.
 */
public class Quote {

	private String Symbol;

	private String Date;

	private double Open;

	private double Close;

	private double High;

	private double Low;

	public String getSymbol() {
		return Symbol;
	}

	public String getDate() {
		return Date;
	}

	public double getOpen() {
		return Open;
	}

	public double getClose() {
		return Close;
	}

	public double getHigh() {
		return High;
	}

	public double getLow() {
		return Low;
	}
}
