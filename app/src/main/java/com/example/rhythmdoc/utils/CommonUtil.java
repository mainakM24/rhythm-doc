package com.example.rhythmdoc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtil {
    public static String formatDate(String isoDate) {
        if (isoDate == null) {
            return "N/A";
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = inputFormat.parse(isoDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            outputFormat.setTimeZone(TimeZone.getDefault()); // Use the default timezone
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "Invalid date";
        }
    }
}
