package com.example.rhythmdoc.models;

import com.example.rhythmdoc.utils.CommonUtil;

public class Patient {
    private String patient_id;
    private String p_name;
    private String p_sex;
    private String p_dob;
    private String pd_admission_date;
    private String pd_hospital_name;
    private String boarding_id;
    private String p_status;

    private String p_address;
    private String p_mobile;
    private String p_remarks;
    private String p_start_date;
    private String p_notes;
    private String p_street_name;
    private String p_house_no;
    private String p_city;
    private String p_pin_cdoe; // TODO: Spelling mistake
    private String p_state;
    private String p_country;
    private String p_email;
    private String age;

    public String getPatient_id() {
        return patient_id;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_sex() {
        return p_sex;
    }

    public String getP_dob() {
        return p_dob;
    }

    public String getPd_admission_date() {
        return CommonUtil.formatDate(pd_admission_date);
    }

    public String getPd_hospital_name() {
        return pd_hospital_name;
    }

    public String getBoarding_id() {
        return boarding_id;
    }

    public String getP_status() {
        return p_status;
    }

    public String getP_address() {
        return p_address;
    }

    public String getP_mobile() {
        return p_mobile;
    }

    public String getP_remarks() {
        return p_remarks;
    }

    public String getP_start_date() {
        return p_start_date;
    }

    public String getP_notes() {
        return p_notes;
    }

    public String getP_street_name() {
        return p_street_name;
    }

    public String getP_house_no() {
        return p_house_no;
    }

    public String getP_city() {
        return p_city;
    }

    public String getP_pin_cdoe() {
        return p_pin_cdoe;
    }

    public String getP_state() {
        return p_state;
    }

    public String getP_country() {
        return p_country;
    }

    public String getP_email() {
        return p_email;
    }

    public String getAge() {
        return age;
    }

    public void setP_status(String status) {
        this.p_status = status;
    }

    public boolean contains(String query) {
        return patient_id.toLowerCase().contains(query) ||
                p_name.toLowerCase().contains(query) ||
                p_dob.toLowerCase().contains(query) ||
                p_sex.toLowerCase().contains(query) ||
                pd_hospital_name.toLowerCase().contains(query) ||
                boarding_id.toLowerCase().contains(query) ||
                pd_admission_date.toLowerCase().contains(query) ||
                p_status.toLowerCase().contains(query);
    }

}
