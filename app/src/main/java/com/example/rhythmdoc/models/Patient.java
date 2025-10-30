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
