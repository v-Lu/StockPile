package victorluproductions.stockpile.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import victorluproductions.stockpile.R;

/**
 * Created by victorlu on 15-03-23.
 */
public class NewsFragment extends Fragment{

	private static String ARG_NEWSTITLES = "titles";
	private static String ARG_NEWSLINKS = "links";
	private ArrayList<String> newsTitles  = new ArrayList<String>();
	private ArrayList<String> newsLinks  = new ArrayList<String>();

	public static NewsFragment newInstance (ArrayList<String> newsTitles, ArrayList<String> newsLinks){
		NewsFragment f = new NewsFragment();
		Bundle b = new Bundle();
		b.putStringArrayList(ARG_NEWSTITLES, newsTitles);
		b.putStringArrayList(ARG_NEWSLINKS, newsLinks);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsTitles = getArguments().getStringArrayList(ARG_NEWSTITLES);
		newsLinks = getArguments().getStringArrayList(ARG_NEWSLINKS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());


		if (newsTitles != null) {
			View V = inflater.inflate(R.layout.news_data, container, false);

			ListView lv = (ListView) V.findViewById(R.id.news_data_list_view);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_text_view, newsTitles);

			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Uri uri = Uri.parse(newsLinks.get(position));
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);

				}
			});

			lv.setAdapter(adapter);
			params.setMargins(margin, margin, margin, margin);
			lv.setLayoutParams(params);
			lv.setLayoutParams(params);


			fl.addView(lv);
		}

		return fl;
	}
}










