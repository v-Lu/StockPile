package victorluproductions.thestockmonitor.Helpers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorlu on 2015-02-21.
 */
public class  JSONResponseHandler implements ResponseHandler<List<String>> {

	private static final String date = "Date";
	private static final String open = "Open";
	private static final String high = "High";
	private static final String low = "Low";
	private static final String close = "Close";
	private static final String squery = "query";
	private static final String sresults = "results";
	private static final String squote = "quote";

	public JSONResponseHandler () {

	}

	@Override
	public List<String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		List<String> result = new ArrayList<String>();
		String JSONResponse = new BasicResponseHandler().handleResponse(response);

		try {
			JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();
			JSONObject stockQuery = responseObject.getJSONObject(squery);
			JSONObject stockResults = stockQuery.getJSONObject(sresults);


			if (stockResults == null)
				return result;

			JSONArray stockQuotes = stockResults.getJSONArray(squote);

			for (int i=0; i < stockQuotes.length(); i++) {
				JSONObject quote = stockQuotes.getJSONObject(i);
				if (quote != null) {
					result.add(quote.get(date) + ": " +
							open + "[" + quote.get(open) + "], " +
							high + "[" + quote.get(high) + "], " +
							low + "[" + quote.get(low) + "], " +
							close + "[" + quote.get(close) + "] ");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
