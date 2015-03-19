package victorluproductions.stockpile.Rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import victorluproductions.stockpile.Rest.Models.Quote;
import victorluproductions.stockpile.Rest.Models.Results;

/**
 * Created by victorlu on 15-03-18.
 */
public class CustomResultsDeserializer implements JsonDeserializer<Results> {
	@Override
	public Results deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Gson gson = new Gson();
		String jsonString = gson.toJson(json);
		JsonParser jParser = new JsonParser();
		ArrayList<Quote> arrayQuotes = new ArrayList<Quote>();

		JsonObject jo = new JsonObject();
		jo = json.getAsJsonObject();
		json = jo.get("quote");

		if (json.isJsonArray()) {
			JsonArray jArray = new JsonArray();
			jArray =  json.getAsJsonArray();

			for(int i=0; i < jArray.size(); i++) {
				Quote q = new Quote();
				q = context.deserialize(jArray.get(i), Quote.class);
				arrayQuotes.add(q);
			}

		} else if (json.isJsonObject()) {
			JsonObject jObject = new JsonObject();
			jObject =  json.getAsJsonObject();

			Quote q = new Quote();
			q = context.deserialize(jObject, Quote.class);
			arrayQuotes.add(q);

		}
		return new Results(arrayQuotes);
	}
}
