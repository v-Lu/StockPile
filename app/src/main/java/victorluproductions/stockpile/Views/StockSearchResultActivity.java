package victorluproductions.stockpile.Views;

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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import victorluproductions.stockpile.Fragments.ChartFragment;
import victorluproductions.stockpile.Fragments.HistoricalDataFragment;
import victorluproductions.stockpile.R;


public class StockSearchResultActivity extends ActionBarActivity {
	//retrofit tag
	private final static String TAG = StockSearchResultActivity.class.getSimpleName();

	@InjectView(R.id.tabs)
	protected PagerSlidingTabStrip tabs;

	@InjectView(R.id.pager)
	protected ViewPager pager;

	private MyPagerAdapter adapter;
	private ContactPagerAdapter contactAdapter;
	private final Handler handler = new Handler();
	private int currentColor = 0xFF666666;
	protected ArrayList<String> yahooResults = new ArrayList<String>();
	protected ArrayList<String> graphX = new ArrayList<String>();
	protected ArrayList<String> graphY = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_search_result);
		ButterKnife.inject(this);

		// setup actionbar
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Initialize the ViewPager adapter and PagerSlidingTabStrip
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);

		// get results from main page
		Intent intent = getIntent();
		yahooResults = intent.getExtras().getStringArrayList("results");
		graphX = intent.getExtras().getStringArrayList("graphX");
		graphY = intent.getExtras().getStringArrayList("graphY");
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
			else if (position == 1 || position == 2)
				return ChartFragment.newInstance(position, graphX, graphY);

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
			// looks a little bit messy here
			//TextView v = new TextView(getActivity());
			//v.setBackgroundResource(R.color.background_window);
			//v.setText("PAGE " + (position + 1));
			//final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources()
			//		.getDisplayMetrics());
			//v.setPadding(padding, padding, padding, padding);
			//v.setGravity(Gravity.CENTER);
			//container.addView(v, 0);
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
