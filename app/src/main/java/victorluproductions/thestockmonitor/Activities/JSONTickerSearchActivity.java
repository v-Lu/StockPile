package victorluproductions.thestockmonitor.Activities;

import android.app.ListActivity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import victorluproductions.thestockmonitor.DateService;
import victorluproductions.thestockmonitor.JSONResponseHandler;
import victorluproductions.thestockmonitor.R;

/**
 * Created by victorlu on 15-02-23.
 */
public class JSONTickerSearchActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState, String url) {
		super.onCreate(savedInstanceState);
		HttpGetTask getTask = new HttpGetTask(url);
		getTask.execute();
	}

	private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

		private static final String authToken = "&auth_token=QZH5XZzz1NZDZW2CHEr6";
		private String url = "https://www.quandl.com/api/v1/datasets/WIKI/%s.json?sort_order=desc%s%s";
		private String trimStart = "&trim_start=";
		private String trimEnd = "&trim_end=";

		AndroidHttpClient client;

		private HttpGetTask(String url) {
			this.url = getUrl();
			client = AndroidHttpClient.newInstance("");
		}

		private String getUrl() {
			EditText startDate = (EditText) findViewById(R.id.start_date);
			EditText endDate = (EditText) findViewById(R.id.end_date);
			EditText ticker = (EditText) findViewById(R.id.ticker_symbol);

			String tickerSymbol = ticker.getText().toString();

			String parsedDate;
			DateService dateService = new DateService();

			// get string date for startDate
			Calendar c = dateService.parseDate(startDate.getText().toString());
			parsedDate = dateService.getDate(c);
			trimStart.concat(parsedDate);

			// get string date for endDate
			c = dateService.parseDate(endDate.getText().toString());
			parsedDate = dateService.getDate(c);
			trimEnd.concat(parsedDate);

			return String.format(url, tickerSymbol, trimStart, trimEnd);
		}

		@Override
		protected List<String> doInBackground(Void... params) {
			HttpGet request = new HttpGet(url);
			JSONResponseHandler responseHandler = new JSONResponseHandler();

			try {
				return client.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e ) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<String> results) {
			if (client != null)
				client.close();

			ListView listView = (ListView) findViewById(R.id.json_list_view);

			listView.setAdapter(new ArrayAdapter<String>(JSONTickerSearchActivity.this,
					android.R.layout.simple_list_item_1, results));

		}
	}
}
