package victorluproductions.stockpile.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import victorluproductions.stockpile.R;

/**
 * Created by victorlu on 15-03-14.
 */
public class HistoricalDataFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private static final String ARG_RESULTS = "results";

	private int position;
	private ArrayList<String> results;

	public static HistoricalDataFragment newInstance(int position, ArrayList<String> results) {
		HistoricalDataFragment f = new HistoricalDataFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		b.putStringArrayList(ARG_RESULTS, results);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
		results = getArguments().getStringArrayList(ARG_RESULTS);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());


		if (results != null) {
			View V = inflater.inflate(R.layout.historical_data, container, false);

			ListView lv = (ListView) V.findViewById(R.id.stock_result_list_view);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_text_view, results);
			lv.setAdapter(adapter);

			params.setMargins(margin, margin, margin, margin);
			lv.setLayoutParams(params);
			lv.setLayoutParams(params);


			fl.addView(lv);
		}

		return fl;
	}
}
