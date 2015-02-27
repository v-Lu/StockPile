package victorluproductions.thestockmonitor;

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
public class JSONResponseHandler implements ResponseHandler<List<String>> {

	private static final String date = "Date";
	private static final String open = "Open";
	private static final String high = "High";
	private static final String low = "Low";
	private static final String close = "Close";
	private static final String stockHeader = "data";

	@Override
	public List<String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		List<String> result = new ArrayList<String>();
		String JSONResponse = new BasicResponseHandler().handleResponse(response);

		try {

			JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

			JSONArray stocks = responseObject.getJSONArray(stockHeader);

			for (int i=0; i < stocks.length(); i++) {

				JSONObject stock = (JSONObject) stocks.get(i);

				if (stock != null) {
					result.add(stock.get(date) + ": " +
							open + "[" + stock.get(open) + "], " +
							high + "[" + stock.get(high) + "], " +
							low + "[" + stock.get(low) + "], " +
							close + "[" + stock.get(close) + "] ");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
