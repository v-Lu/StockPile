package victorluproductions.thestockmonitor.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by victorlu on 15-02-23.
 */
public class DateHandler {

	public DateHandler() {

	}

	public Calendar parseDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt = dateFormat.parse(date);

			Calendar c = Calendar.getInstance();
			c.setTime(dt);


			return c;

		} catch (ParseException e) {
			return null;
		}
	}

	public String getDate(Calendar c) {
		String year;
		String month;
		String day;

		year = String.valueOf(c.get(Calendar.YEAR));
		// Calendar.MONTH starts at 0... Add 1 to make it a readable date
		month = String.valueOf(c.get(Calendar.MONTH) + 1);
		day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		return year + "-" + month + "-" + day;
	}

}
