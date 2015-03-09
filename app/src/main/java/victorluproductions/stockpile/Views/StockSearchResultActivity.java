package victorluproductions.stockpile.Views;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import victorluproductions.stockpile.Rest.Models.HistoricalDataQuery;
import victorluproductions.stockpile.Rest.Models.Quote;
import victorluproductions.stockpile.Rest.RestClient;
import victorluproductions.stockpile.R;


public class StockSearchResultActivity extends ActionBarActivity {
	private final static String TAG = StockSearchResultActivity.class.getSimpleName();

	@InjectView(R.id.stock_result_list_view)
	protected ListView stock_result_lv;

	protected List<String> yahooResults = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_search_result);
		ButterKnife.inject(this);
		//setup action bar
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Toolbar toolbar = (Toolbar) findViewById(R.id.searchResultToolbar);
		//setSupportActionBar(toolbar);

		Intent intent = getIntent();
		Bundle intentBundle = intent.getExtras();

		String startDate = intentBundle.get("startDate").toString();
		String endDate = intentBundle.get("endDate").toString();
		String ticker =	intentBundle.get("ticker").toString();
		String yql = "select * from yahoo.finance.historicaldata where symbol =\"{0}\" and startDate = \"{1}\" and endDate = \"{2}\"";

		MessageFormat mf = new MessageFormat(yql);
		yql = mf.format(yql, ticker, startDate, endDate);

		RestClient rc = new RestClient();
		rc.getYahooApiService().getStockHistoricalData(yql,
				new Callback<HistoricalDataQuery>()
				{
					@Override
					public void success(HistoricalDataQuery results, Response response)
					{
						if (results.getQuery() != null)
						{
							for(Quote quote: results.getQuery().getResults().getQuotes()) {
								yahooResults.add(quote.getDate() + ": " +
										"Open[" + quote.getOpen() + "], " +
										"High[" + quote.getHigh() + "], " +
										"Low[" + quote.getLow() + "], " +
										"Close[" + quote.getClose() + "] ");

							}
						}
						if (!yahooResults.isEmpty())
						{
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockSearchResultActivity.this, android.R.layout.simple_list_item_1, yahooResults);
							stock_result_lv.setAdapter(adapter);
						}
					}

					@Override
					public void failure(RetrofitError error)
					{
						Log.e(TAG, "Error : " + error.getMessage());


					}
				});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_stock_search_result, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
	}
}
