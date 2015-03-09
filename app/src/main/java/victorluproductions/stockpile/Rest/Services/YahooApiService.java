package victorluproductions.stockpile.Rest.Services;

import retrofit.Callback;
import retrofit.http.GET;
import victorluproductions.stockpile.Rest.Models.HistoricalDataQuery;

/**
 * Created by victorlu on 15-03-02.
 */
public interface YahooApiService {
	@GET("/yql?&format=json&env=store://datatables.org/alltableswithkeys")
	public void getStockHistoricalData(@retrofit.http.Query("q") String q,
									   Callback<HistoricalDataQuery> callback);
}
