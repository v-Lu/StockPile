package victorluproductions.thestockmonitor.Views;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import victorluproductions.thestockmonitor.Services.JSONTickerSearchActivity;
import victorluproductions.thestockmonitor.Fragments.DatePickerFragment;
import victorluproductions.thestockmonitor.R;

public class MainActivity extends FragmentActivity
						  implements DatePickerFragment.OnDateSetListener {
	private int startDateId;
	private int endDateId;

	private EditText startDate;
	private EditText endDate;
	private EditText ticker;
	private Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//new HttpGetTask().execute();

		startDate = (EditText) findViewById(R.id.start_date);
		endDate = (EditText) findViewById(R.id.end_date);

		startDateId = startDate.getId();
		endDateId = endDate.getId();

		setDatePickerOnClickListener(startDate);
		setDatePickerOnClickListener(endDate);

		searchButton = (Button) findViewById((R.id.search));
		setSearchButtonOnClickListener(searchButton);
	}

	public void setSearchButtonOnClickListener(Button searchButton) {
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!validateFields())
					return;

				Intent queryData = new Intent(getApplicationContext(), JSONTickerSearchActivity.class);
				startActivity(queryData);


			//Intent stockSearch = new Intent(getApplicationContext(), StockSearchResultActivity.class);
			//startActivity(stockSearch);
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

				//determine which date text box this is called from
				findViewById(R.id.start_date);

				String textBoxValue = et.getText().toString();

				if (textBoxValue.isEmpty()) {
					Calendar c = Calendar.getInstance();
					currYear = c.get(Calendar.YEAR);
					currMonth = c.get(Calendar.MONTH);
					currDay = c.get(Calendar.DAY_OF_MONTH);

					datePicker = DatePickerFragment.newInstance(currYear, currMonth, currDay, et.getId());
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date;
						date = dateFormat.parse(textBoxValue);

						Calendar c = Calendar.getInstance();
						c.setTime(date);

						currYear = c.get(Calendar.YEAR);
						currMonth = c.get(Calendar.MONTH);
						currDay = c.get(Calendar.DAY_OF_MONTH);

						datePicker = DatePickerFragment.newInstance(currYear, currMonth, currDay, et.getId());
					}
					catch (ParseException e) {
						e.printStackTrace();
					}
				}
				datePicker.show(getFragmentManager(), "datePicker");


			}
		});
	}

	public void OnDateSelected(String date, int dateTextBoxId) {
		if (dateTextBoxId == startDateId) {
			startDate = (EditText) findViewById(R.id.start_date);
			startDate.setText(date);
		}
		else if (dateTextBoxId == endDateId) {
			endDate = (EditText) findViewById(R.id.end_date);
			endDate.setText(date);
		}
	}

	public boolean validateFields() {
		EditText startDate = (EditText) findViewById(R.id.start_date);
		EditText endDate = (EditText) findViewById(R.id.end_date);
		EditText ticker = (EditText) findViewById(R.id.ticker_symbol);

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























