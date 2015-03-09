package victorluproductions.stockpile.Rest;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapterFactory;

import java.io.IOException;

/**
 * Created by victorlu on 15-03-02.
 */
public class YahooAdapterFactory implements TypeAdapterFactory {

	public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

		final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

		return new TypeAdapter<T>() {

			public void write(JsonWriter out, T value) throws IOException {
				delegate.write(out, value);
			}

			public T read(JsonReader in) throws IOException {

				JsonElement jsonElement = elementAdapter.read(in);
				if (jsonElement.isJsonObject()) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					if (jsonObject.has("data") && jsonObject.get("data").isJsonObject())
					{
						jsonElement = jsonObject.get("data");
					}
				}

				return delegate.fromJsonTree(jsonElement);
			}
		}.nullSafe();
	}
}
