package victorluproductions.stockpile.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import victorluproductions.stockpile.Fragments.ChartFragment;
import victorluproductions.stockpile.Fragments.HistoricalDataFragment;
import victorluproductions.stockpile.Fragments.NewsFragment;
import victorluproductions.stockpile.R;
import victorluproductions.stockpile.Rest.Models.HistoricalDataQuery;
import victorluproductions.stockpile.Rest.Models.NewsQuery;
import victorluproductions.stockpile.Rest.Models.Quote;
import victorluproductions.stockpile.Rest.Models.RelatedStory;
import victorluproductions.stockpile.Rest.Models.Result;
import victorluproductions.stockpile.Rest.RestClient;


public class StockSearchResultActivity extends ActionBarActivity {
	//retrofit tag
	private final static String TAG = StockSearchResultActivity.class.getSimpleName();

	@InjectView(R.id.tabs)
	protected PagerSlidingTabStrip tabs;

	@InjectView(R.id.pager)
	protected ViewPager pager;

	private MyPagerAdapter adapter;
	private final Handler handler = new Handler();

	protected ArrayList<String> yahooResults = new ArrayList<String>();
	protected ArrayList<String> graphX = new ArrayList<String>();
	protected ArrayList<String> graphY = new ArrayList<String>();
	protected ArrayList<String> newsTitles  = new ArrayList<String>();
	protected ArrayList<String> newsLinks  = new ArrayList<String>();

	protected String ticker;
	protected String startDate;
	protected String endDate;
	protected boolean openCheckbox;
	protected boolean highCheckbox;
	protected boolean lowCheckbox;
	protected boolean closeCheckbox;

	ProgressDialog progress;
	protected int callbackCount = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_search_result);
		ButterKnife.inject(this);

		progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Wait while loading...");
		progress.show();

		// get results from main page
		Intent intent = getIntent();
		//yahooResults = intent.getExtras().getStringArrayList("results");
		//graphX = intent.getExtras().getStringArrayList("graphX");
		//graphY = intent.getExtras().getStringArrayList("graphY");
		//newsTitles = intent.getExtras().getStringArrayList("newsTitles");
		ticker = intent.getExtras().get("ticker").toString();
		startDate = intent.getExtras().get("startDate").toString();
		endDate = intent.getExtras().get("endDate").toString();
		openCheckbox = intent.getExtras().getBoolean("openCheckbox");
		highCheckbox = intent.getExtras().getBoolean("highCheckbox");
		lowCheckbox = intent.getExtras().getBoolean("lowCheckbox");
		closeCheckbox = intent.getExtras().getBoolean("closeCheckbox");

		RestClient rc = new RestClient();

		// find news based on ticker symbol (somewhat buggy for symbols like 'DATA')
		String yql = "select * from google.news where q =\"" + ticker + "\"";
		rc.getYahooApiService().getStockNews(yql,
				new Callback<NewsQuery>()
				{
					@Override
					public void success(NewsQuery results, Response response) {

						newsTitles.clear();

						if (results.getQuery().getResults() != null) {
							List<Result> queryResults = results.getQuery().getResults().getResults();

							for (Result r : queryResults) {
								for (RelatedStory rs : r.getRelatedStories()) {
									newsTitles.add(rs.getTitleNoFormatting().toString());
									newsLinks.add(rs.getSignedRedirectUrl().toString());
								}

								newsTitles.add(r.getTitleNoFormatting().toString());
								newsLinks.add(r.getSignedRedirectUrl().toString());
							}
						}
						// if both callbacks have been executed, disable spinner
						checkProcessCount();
					}

					@Override
					public void failure(RetrofitError error)
					{
						Log.e(TAG, "Error : " + error.getMessage());
					}
				});

		yql = "select * from yahoo.finance.historicaldata where symbol = \"{0}\" and startDate = \"{1}\" and endDate = \"{2}\"";
		MessageFormat mf = new MessageFormat(yql);
		yql = mf.format(yql, ticker, startDate, endDate);

		rc.getYahooApiService().getStockHistoricalData(yql,
				new Callback<HistoricalDataQuery>()
				{
					@Override
					public void success(HistoricalDataQuery results, Response response) {
						yahooResults.clear();
						graphX.clear();
						graphY.clear();

						// parse historical data results
						if (results.getQuery().getResults() != null)
						{
							List<Quote> q = results.getQuery().getResults().getResults();

							for(Quote quote : q) {

								String output = quote.getDate() + ": ";

								if (openCheckbox)
									output += "Open[" + quote.getOpen() + "], ";

								if (highCheckbox)
									output += "High[" + quote.getHigh() + "], ";

								if (lowCheckbox)
									output += "Low[" + quote.getLow() + "], ";

								if (closeCheckbox)
									output += "Close[" + quote.getClose() + "]";

								output = output.replaceAll(",$", "");
								yahooResults.add(output);

								graphX.add(quote.getDate());
								graphY.add(String.valueOf(quote.getOpen()));
							}
						}
						// if both callbacks have been executed, disable spinner
						checkProcessCount();
					}

					@Override
					public void failure(RetrofitError error)
					{
						Log.e(TAG, "Error : " + error.getMessage());
					}
				});
	}

	public void checkProcessCount() {
		if (callbackCount == 0) {
			// setup actionbar
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			// Initialize the ViewPager adapter and PagerSlidingTabStrip
			adapter = new MyPagerAdapter(getSupportFragmentManager());
			pager.setAdapter(adapter);
			final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
					.getDisplayMetrics());
			pager.setPageMargin(pageMargin);
			tabs.setViewPager(pager);
			progress.dismiss();

		} else {
			callbackCount--;
		}
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

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getSupportActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Historical", "Dashboard", "News Feed"};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0)
				return HistoricalDataFragment.newInstance(position, yahooResults);
			else if (position == 1)
				return ChartFragment.newInstance(position, graphX, graphY);
			else if (position == 2)
				return NewsFragment.newInstance(newsTitles, newsLinks);

			return null;
		}

	}

	public class ContactPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

		private final int[] ICONS = { R.drawable.stock_logo };

		public ContactPagerAdapter() {
			super();
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public int getPageIconResId(int position) {
			return ICONS[position];
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return null;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object view) {
			container.removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View v, Object o) {
			return v == ((View) o);
		}

	}
}
