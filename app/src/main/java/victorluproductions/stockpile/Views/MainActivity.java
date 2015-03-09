package victorluproductions.stockpile.Views;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import victorluproductions.stockpile.Helpers.DateHandler;
import victorluproductions.stockpile.Fragments.DatePickerFragment;
import victorluproductions.stockpile.R;

public class MainActivity extends FragmentActivity
						  implements DatePickerFragment.OnDateSetListener {
	@InjectView(R.id.start_date)
	protected EditText startDate;

	@InjectView(R.id.end_date)
	protected EditText endDate;

	@InjectView(R.id.ticker_symbol)
	protected EditText ticker;

	@InjectView(R.id.search)
	protected Button searchButton;

	private int startDateId;
	private int endDateId;

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
				if (!validateFields())
					return;

				Intent intent = new Intent(MainActivity.this, StockSearchResultActivity.class);

				intent.putExtra("startDate", startDate.getText());
				intent.putExtra("endDate", endDate.getText());
				intent.putExtra("ticker", ticker.getText());

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























