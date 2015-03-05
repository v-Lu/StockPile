package victorluproductions.thestockmonitor.Services;

import android.app.ListActivity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import victorluproductions.thestockmonitor.Helpers.DateHandler;
import victorluproductions.thestockmonitor.Helpers.JSONResponseHandler;
import victorluproductions.thestockmonitor.R;

/**
 * Created by victorlu on 15-02-23.
 */
public class JSONTickerSearchActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle intentBundle = intent.getExtras();

		String startDate = intentBundle.get(getString(R.string.start_date_string)).toString();
		String endDate = intentBundle.get(getString(R.string.end_date_string)).toString();
		String ticker =	intentBundle.get(getString(R.string.ticker_string)).toString();

		HttpGetTask getTask = new HttpGetTask(startDate, endDate, ticker);
		try {

			getTask.execute();
		}
		catch (Exception e) {

		}
	}

	private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

		private String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo." +
				"finance.historicaldata%20where%20symbol%20%3D%20{0}%20and%20startDate%20%3D%20{1}%20and%20" +
				"endDate%20%3D%20{2}&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		AndroidHttpClient client = AndroidHttpClient.newInstance("");

		private HttpGetTask(String startDate, String endDate, String ticker) {
			this.url = getUrl(startDate, endDate, ticker);
		}

		private String getUrl(String startDate, String endDate, String ticker) {
			MessageFormat mf = new MessageFormat(url);

			try {
				startDate = URLEncoder.encode("\"" +  startDate + "\"", "UTF-8");
				endDate = URLEncoder.encode("\"" +  endDate + "\"", "UTF-8");
				ticker = URLEncoder.encode("\"" +  ticker + "\"", "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return mf.format(url, ticker, startDate, endDate);

		}


		@Override
		protected List<String> doInBackground(Void... params) {
			HttpGet request = new HttpGet(url);
			JSONResponseHandler responseHandler = new JSONResponseHandler();

			try {
				return	client.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		/*@Override
		protected void onPostExecute(List<String> results) {


		}*/

		@Override
		protected void onPostExecute(List<String> results) {
			super.onPostExecute(results);

			if (client != null)
				client.close();

			ListView listView = (ListView) findViewById(R.id.json_list_view);

			listView.setAdapter(new ArrayAdapter<String>(JSONTickerSearchActivity.this,
					android.R.layout.simple_list_item_1, results));

		}
	}
}
