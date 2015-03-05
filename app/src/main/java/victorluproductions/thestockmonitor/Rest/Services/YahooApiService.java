package victorluproductions.thestockmonitor.Rest.Services;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by victorlu on 15-03-02.
 */
public interface YahooApiService {
	@GET("select * from yahoo.finance.historicaldata where symbol = \"{ticker}\" and startDate = \"{startDate}\" and " +
			"endDate = \"{endDate}\"&env=store://datatables.org/alltableswithkeys")

	public void getStockHistoricalData(@Path("ticker") String ticker,
									   @Path("startDate") String startDate,
									   @Path("endDate") String endDate);
}
