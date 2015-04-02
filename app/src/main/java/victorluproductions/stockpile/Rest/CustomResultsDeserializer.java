package victorluproductions.stockpile.Rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import victorluproductions.stockpile.Rest.Models.Quote;
import victorluproductions.stockpile.Rest.Models.Result;
import victorluproductions.stockpile.Rest.Models.Results;

/**
 * Created by victorlu on 15-03-18.
 */
public class CustomResultsDeserializer implements JsonDeserializer<Results> {

	@Override
	public Results deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		ArrayList<Quote> arrayQuotes = new ArrayList<Quote>();
		ArrayList<Result> arrayNews = new ArrayList<Result>();

		if (json.getAsJsonObject().has("quote")) {
			json = json.getAsJsonObject().get("quote");


			if (json.isJsonArray()) {
				JsonArray jArray = new JsonArray();
				jArray = json.getAsJsonArray();

				for (JsonElement j : jArray) {
					Quote q = new Quote();
					q = context.deserialize(j, Quote.class);
					arrayQuotes.add(q);
				}

			} else if (json.isJsonObject()) {
				JsonObject jObject = new JsonObject();
				jObject = json.getAsJsonObject();

				Quote q = new Quote();
				q = context.deserialize(jObject, Quote.class);
				arrayQuotes.add(q);
			}
			return new Results(arrayQuotes);

		} else {
			// if we make it into here, it means we are parsing for news
			json = json.getAsJsonObject().get("results");
			JsonArray jArray = new JsonArray();
			jArray = json.getAsJsonArray();

			for (JsonElement j : jArray) {
				Result r = new Result();
				r = context.deserialize(j, Result.class);
				arrayNews.add(r);
			}
			return new Results(arrayNews);
		}
	}
}
