package victorluproductions.thestockmonitor.Rest.Models;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by victorlu on 15-03-02.
 */
@Parcel
public class HistoricalData {

	@SerializedName("ticker")
	private String ticker;

	@SerializedName("date")
	private Date date;

	@SerializedName("open")
	private double open;

	@SerializedName("close")
	private double close;

	@SerializedName("high")
	private double high;

	@SerializedName("low")
	private double low;

	public String getTicker() {
		return ticker;
	}

	public Date getDate() {
		return date;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}
}
