package victorluproductions.thestockmonitor.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import victorluproductions.thestockmonitor.Helpers.DateHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerFragment.OnDateSetListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment
								implements DatePickerDialog.OnDateSetListener {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String arg_year = "year";
	private static final String arg_month = "month";
	private static final String arg_day = "day";
	private static final String arg_dateTextBoxId = "dateTextBoxId";
	OnDateSetListener mCallback;

	private int year;
	private int month;
	private int day;
	private int dateTextBoxId;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param year Parameter 1.
	 * @param month Parameter 2.
	 * @param day Parameter 2.
	 * @return A new instance of fragment FromDate.
	 */
	// TODO: Rename and change types and number of parameters
	public static DatePickerFragment newInstance(int year, int month, int day, int dateTextBoxId) {
		DatePickerFragment fragment = new DatePickerFragment();
		Bundle args = new Bundle();
		args.putInt(arg_year, year);
		args.putInt(arg_month, month);
		args.putInt(arg_day, day);
		args.putInt(arg_dateTextBoxId, dateTextBoxId);
		fragment.setArguments(args);
		return fragment;
	}

	public DatePickerFragment() {
	}

/*	public void showDatePickerDialog() {
		FragmentTransaction fragTran = getFragmentManager().beginTransaction();
		Fragment frag = getFragmentManager().findFragmentByTag("dateDialog");

		if (frag != null) {
			fragTran.remove(frag);
		}
		fragTran.addToBackStack(null);

		this.show(fragTran, "dateDialog");
	}*/

	@Override
	public Dialog onCreateDialog(Bundle InstanceState) {
		InstanceState = getArguments();

		year = InstanceState.getInt(arg_year);
		month = InstanceState.getInt(arg_month);
		day = InstanceState.getInt(arg_day);
		dateTextBoxId = InstanceState.getInt(arg_dateTextBoxId);

		return new DatePickerDialog(getActivity(), this, year, month , day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		DateHandler dateService = new DateHandler();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String date = dateService.getDate(calendar);

		mCallback.OnDateSelected(date, dateTextBoxId);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnDateSetListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDateSetListener");
		}
	}

	// Container Activity must implement this interface
	public interface OnDateSetListener {
		public void OnDateSelected(String date, int dateTextBoxId);
	}

}
