package victorluproductions.stockpile.Views;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import victorluproductions.stockpile.Helpers.DateHandler;
import victorluproductions.stockpile.Fragments.DatePickerFragment;
import victorluproductions.stockpile.R;

public class MainActivity extends FragmentActivity
						  implements DatePickerFragment.OnDateSetListener {
	//retrofit tag
	private final static String TAG = StockSearchResultActivity.class.getSimpleName();

	@InjectView(R.id.start_date)
	protected EditText startDate;

	@InjectView(R.id.end_date)
	protected EditText endDate;

	@InjectView(R.id.ticker_symbol)
	protected EditText ticker;

	@InjectView(R.id.search)
	protected Button searchButton;

	@InjectView(R.id.open_checkbox)
	protected CheckBox openCheckbox;

	@InjectView(R.id.high_checkbox)
	protected CheckBox highCheckbox;

	@InjectView(R.id.low_checkbox)
	protected CheckBox lowCheckbox;

	@InjectView(R.id.close_checkbox)
	protected CheckBox closeCheckbox;

	protected int startDateId;
	protected int endDateId;
	protected ArrayList<String> yahooResults = new ArrayList<String>();
	protected ArrayList<String> graphX = new ArrayList<String>();
	protected ArrayList<String> graphY = new ArrayList<String>();
	protected ArrayList<String> newsTitles  = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.inject(this);

		startDateId = startDate.getId();
		endDateId = endDate.getId();

		// setup date pickers
		setDatePickerOnClickListener(startDate);
		setDatePickerOnClickListener(endDate);
		setSearchButtonOnClickListener(searchButton);
	}

	public void setSearchButtonOnClickListener(Button searchButton)	{
		searchButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// validate that ticker symbol and date fields are populated
				if (!validFields())
					return;

				Intent intent = new Intent(MainActivity.this, StockSearchResultActivity.class);

				intent.putExtra("ticker", ticker.getText());
				intent.putExtra("startDate", startDate.getText());
				intent.putExtra("endDate", endDate.getText());
				intent.putExtra("openCheckbox", openCheckbox.isChecked());
				intent.putExtra("highCheckbox", highCheckbox.isChecked());
				intent.putExtra("lowCheckbox", lowCheckbox.isChecked());
				intent.putExtra("closeCheckbox", closeCheckbox.isChecked());
				startActivity(intent);

			}
		});
	}

	public void setDatePickerOnClickListener (EditText textBox) {
		textBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment datePicker = new DatePickerFragment();
				int currYear;
				int currMonth;
				int currDay;
				EditText et = (EditText) v;

				String textBoxValue = et.getText().toString();

				if (textBoxValue.isEmpty()) {
					Calendar c = Calendar.getInstance();
					currYear = c.get(Calendar.YEAR);
					currMonth = c.get(Calendar.MONTH);
					currDay = c.get(Calendar.DAY_OF_MONTH);

					datePicker = DatePickerFragment.newInstance(currYear, currMonth, currDay, et.getId());
				} else {
					DateHandler dateService = new DateHandler();
					Calendar c = dateService.parseDate(textBoxValue);

					currYear = c.get(Calendar.YEAR);
					currMonth = c.get(Calendar.MONTH);
					currDay = c.get(Calendar.DAY_OF_MONTH);

					datePicker = DatePickerFragment.newInstance(currYear, currMonth, currDay, et.getId());
				}
				datePicker.show(getFragmentManager(), "datePicker");
			}
		});
	}

	public void OnDateSelected(String date, int dateTextBoxId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		try {
			dt = dateFormat.parse(date);
			date = dateFormat.format(dt);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		if (dateTextBoxId == startDateId) {
			startDate.setText(date);
		}
		else if (dateTextBoxId == endDateId) {
			endDate.setText(date);
		}
	}

	public boolean validFields() {

		if (ticker.getText().toString().isEmpty())
			return false;

		if (startDate.getText().toString().isEmpty())
			return false;

		if (endDate.getText().toString().isEmpty())
			return false;

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
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
}























