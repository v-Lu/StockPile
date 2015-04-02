package victorluproductions.stockpile.Rest.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import victorluproductions.stockpile.Rest.Models.Image;
import victorluproductions.stockpile.Rest.Models.RelatedStory;
import victorluproductions.stockpile.Rest.Models.Result;

/**
 * Created by victorlu on 15-03-31.
 */
public class CustomRelatedStoriesDeserializer implements JsonDeserializer<Result> {
	@Override
	public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Result result = new Result();

		if (json.getAsJsonObject().has("GsearchResultClass")) {
			result.setGsearchResultClass(json.getAsJsonObject().get("GsearchResultClass").toString());
			result.setClusterUrl(json.getAsJsonObject().get("clusterUrl").toString());
			result.setContent(json.getAsJsonObject().get("content").toString());
			result.setUnescapedUrl(json.getAsJsonObject().get("unescapedUrl").toString());
			result.setUrl(json.getAsJsonObject().get("url").toString());
			result.setTitle(json.getAsJsonObject().get("title").toString());
			result.setTitleNoFormatting(json.getAsJsonObject().get("titleNoFormatting").toString());
			result.setLocation(json.getAsJsonObject().get("location").toString());

			if (json.getAsJsonObject().has("image")) {
				Image i = new Image();
				i = context.deserialize(json.getAsJsonObject().get("image"), Image.class);
				result.setImage(i);
			}
			if (json.getAsJsonObject().has("relatedStories")) {
				json = json.getAsJsonObject().get("relatedStories");

				List<RelatedStory> rsList = new ArrayList<RelatedStory>();
				if (json.isJsonArray()) {
					JsonArray jArray = new JsonArray();
					jArray = json.getAsJsonArray();

					for (JsonElement j : jArray) {
						RelatedStory q = new RelatedStory();
						q = context.deserialize(j, RelatedStory.class);
						rsList.add(q);
					}

				} else if (json.isJsonObject()) {
					JsonObject jObject = new JsonObject();
					jObject = json.getAsJsonObject();

					RelatedStory q = new RelatedStory();
					q = context.deserialize(jObject, RelatedStory.class);
					rsList.add(q);
				}
				result.setRelatedStories(rsList);
			}
		}
		return result;
	}
}
