package victorluproductions.stockpile.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import victorluproductions.stockpile.Rest.Models.Result;
import victorluproductions.stockpile.Rest.Models.Results;
import victorluproductions.stockpile.Rest.Services.CustomRelatedStoriesDeserializer;
import victorluproductions.stockpile.Rest.Services.YahooApiService;

import static retrofit.RestAdapter.*;

/**
 * Created by victorlu on 15-03-02.
 */
public class RestClient
{
	private static final String BASE_URL = "http://query.yahooapis.com/v1/public";
	private YahooApiService apiService;

	public RestClient()
	{
		Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(new YahooAdapterFactory())
			.registerTypeAdapter(Results.class, new CustomResultsDeserializer())
			.registerTypeAdapter(Result.class, new CustomRelatedStoriesDeserializer())
			.create();

		RestAdapter restAdapter = new Builder()
				.setLogLevel(LogLevel.FULL)
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