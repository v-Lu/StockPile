package victorluproductions.stockpile.Helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import victorluproductions.stockpile.Fragments.CardFragment;
import victorluproductions.stockpile.Fragments.HistoricalDataFragment;

/**
 * Created by victorlu on 15-03-13.
 */
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
		return CardFragment.newInstance(position);
	}

}
