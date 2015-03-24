package victorluproductions.stockpile.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import java.util.ArrayList;
import java.util.Collections;
import victorluproductions.stockpile.R;

/**
 * Created by victorlu on 15-03-15.
 */
public class ChartFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
		OnChartGestureListener, OnChartValueSelectedListener {
	private static final String ARG_POSITION = "position";
	private static final String ARG_RESULTSX = "resultsX";
	private static final String ARG_RESULTSY = "resultsY";

	private int position;
	private ArrayList<String> xAxis;
	private ArrayList<String> yAxis;

	private LineChart lineChart;
	private SeekBar xSeek;

	public static ChartFragment newInstance(int position, ArrayList<String> graphX, ArrayList<String> graphY) {
		ChartFragment f = new ChartFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		b.putStringArrayList(ARG_RESULTSX, graphX);
		b.putStringArrayList(ARG_RESULTSY, graphY);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
		xAxis = getArguments().getStringArrayList(ARG_RESULTSX);
		yAxis = getArguments().getStringArrayList(ARG_RESULTSY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());


		if (xAxis.size() > 1) {
			View V = inflater.inflate(R.layout.data_chart, container, false);

			lineChart = (LineChart) V.findViewById(R.id.chart);

			// need to reverse our arraylists (for charting purposes)
			Collections.reverse(xAxis);
			Collections.reverse(yAxis);

			ArrayList<String> xVals = new ArrayList<String>();
			for (int i = 0; i < xAxis.size(); i++) {
				xVals.add(xAxis.get(i));
			}

			ArrayList<Entry> yVals = new ArrayList<Entry>();
			for(int i=0; i < yAxis.size(); i++) {
				yVals.add(new Entry(Float.parseFloat(yAxis.get(i)), i));
			}

			LineDataSet lineDataSet = new LineDataSet(yVals, "Closing values per day");
			// set the line to be drawn like this "- - - - - -"
			lineDataSet.enableDashedLine(10f, 5f, 0f);
			lineDataSet.setColor(Color.BLACK);
			lineDataSet.setCircleColor(Color.BLACK);
			lineDataSet.setLineWidth(1f);
			lineDataSet.setCircleSize(3f);
			lineDataSet.setDrawCircleHole(false);
			lineDataSet.setValueTextSize(9f);
			lineDataSet.setFillAlpha(65);
			lineDataSet.setFillColor(Color.BLACK);

			LineData lineData = new LineData(xVals, lineDataSet);

			lineChart.setData(lineData);

			params.setMargins(margin, margin, margin, margin);
			lineChart.setLayoutParams(params);
			lineChart.setLayoutParams(params);

			fl.addView(lineChart);
		}

		return fl;
	}

	public void setData() {

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// redraw
		lineChart.invalidate();
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onChartLongPressed(MotionEvent me) {
		Log.i("LongPress", "Chart longpressed.");
	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		Log.i("DoubleTap", "Chart double-tapped.");
	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		Log.i("SingleTap", "Chart single-tapped.");
	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
		Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		Log.i("Entry selected", e.toString());
	}

	@Override
	public void onNothingSelected() {
		Log.i("Nothing selected", "Nothing selected.");
	}
}
