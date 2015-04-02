package victorluproductions.stockpile.Rest.Services;

import retrofit.Callback;
import retrofit.http.GET;
import victorluproductions.stockpile.Fragments.NewsFragment;
import victorluproductions.stockpile.Rest.Models.HistoricalDataQuery;
import victorluproductions.stockpile.Rest.Models.NewsQuery;

/**
 * Created by victorlu on 15-03-02.
 */
public interface YahooApiService {
	@GET("/yql?&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
	void getStockHistoricalData(@retrofit.http.Query("q") String q, Callback<HistoricalDataQuery> callback);

	@GET("/yql?&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
	void getStockNews(@retrofit.http.Query("q") String q, Callback<NewsQuery> callback);
}
