package victorluproductions.stockpile.Rest.Models;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by victorlu on 15-03-02.
 */
@Parcel
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
