package bloodbank.android.example.com.bloodbank.helper;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;


public class HelperMethod {


    // Method Replace used to transaction between activity to fragment and set tittle of activity
    public static void replace(Fragment fragment, android.support.v4.app.FragmentManager fragmentManager, int id, TextView bar_tittle, String tittle) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (bar_tittle != null) {
            bar_tittle.setText(tittle);

        }
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    //Calender
    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        DecimalFormat mFormat = new DecimalFormat("00");
                        if (!data1.getMonth().equals(mFormat.format(Double.valueOf(selectedMonth)))) {
                            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                            data1.setDate_txt(data);
                            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                            data1.setYear(String.valueOf(selectedYear));
                            text_view_data.setText(data);
                        }
                    }
                }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()), Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    //This method for make Call
    public static void makePhoneCall(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        context.startActivity(callIntent);

    }


}
