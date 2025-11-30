package com.example.rhythmdoc.models;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Session {
    private String patient_id;
    private String session_id;
    private String start_date;
    private String end_date;
    private int total_analyzed_duration;
    private int total_analyzed_beat;
    private int min_bpm;
    private int med_bpm;
    private int max_bpm;
    private int nb_count;
    private int apb_count;

    public String getPatient_id() {
        return patient_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getStart_date() {
        return formatDate(start_date);
    }

    public String getEnd_date() {
        return formatDate(end_date);
    }

    public int getTotal_analyzed_duration() {
        return total_analyzed_duration;
    }

    public int getTotal_analyzed_beat() {
        return total_analyzed_beat;
    }

    public int getMin_bpm() {
        return min_bpm;
    }

    public int getMed_bpm() {
        return med_bpm;
    }

    public int getMax_bpm() {
        return max_bpm;
    }

    public int getNb_count() {
        return nb_count;
    }

    public int getApb_count() {
        return apb_count;
    }

    public static String formatDate(String isoDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = inputFormat.parse(isoDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mma", Locale.getDefault());
            outputFormat.setTimeZone(TimeZone.getDefault()); // Use the default timezone
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "Invalid date";
        }
    }
}
