package com.example.rhythmdoc.models;

import com.example.rhythmdoc.utils.CommonUtil;

public class Patient {public String patient_id;
    public String p_name;
    public String p_sex;
    public String p_dob;
    public String pd_admission_date;
    public String pd_hospital_name;
    public String boarding_id;

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
}
