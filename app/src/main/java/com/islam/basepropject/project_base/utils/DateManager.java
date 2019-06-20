package com.islam.basepropject.project_base.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    public  static String getTimeDiff(long startTime){
        return (String) DateUtils.getRelativeTimeSpanString(startTime);
    }

    public static String convertTimestampToString(long timestamp, String format) {
        return  DateFormat.format(format, timestamp).toString();
    }

    public static Date convertStringToDateObj(String dateString, String format) {
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat(format, Locale.getDefault());
            return sd1.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isTimeInBetween(long openTime, long closeTime) throws ParseException {
        String initialTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(openTime * 1000));
        String finalTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(closeTime * 1000));
        boolean valid = false;
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg)) {

            Date inTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    .parse(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())));

            //End Time
            Date finTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
            }

            Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) ||
                    actualTime.compareTo(calendar1.getTime()) == 0) &&
                    actualTime.before(calendar2.getTime())) {
                valid = true;

            }
        }
        return valid;
    }

}
