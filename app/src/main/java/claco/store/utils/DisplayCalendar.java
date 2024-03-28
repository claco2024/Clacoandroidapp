package claco.store.utils;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DisplayCalendar {
    Calendar myCalendar= Calendar.getInstance();
    public  DatePickerDialog.OnDateSetListener displayDateDialog(final TextView editText)
    {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };
        return date;
    }

    private void updateLabel(TextView editTextId) {
        String myFormat = "YYYY/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextId.setText(sdf.format(myCalendar.getTime()));
    }
}
