package victorluproductions.thestockmonitor.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import victorluproductions.thestockmonitor.Rest.Services.YahooApiService;

/**
 * Created by victorlu on 15-03-02.
 */
public class RestClient
{
	private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";
	private YahooApiService apiService;

	public RestClient()
	{
		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(new YahooAdapterFactory())
				.setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.setEndpoint(BASE_URL)
				.setConverter(new GsonConverter(gson))
				.build();

		apiService = restAdapter.create(YahooApiService.class);
	}

	public YahooApiService getYahooApiService()
	{
		return apiService;
	}
}