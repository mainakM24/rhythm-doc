package com.example.rhythmdoc.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Doctor {
    public int id;
    public String doctor_id;
    public String d_password;
    public String d_name;
    public String d_status;
    public String d_inserted_date;
    public String d_remarks;
    public String d_about_me;
    public String d_specialization;
    public String d_address;
    public String d_medical_org_address;
    public String d_email;
    public String d_contact_number;
    public String d_medical_org_department;
    public String d_medical_org_name;
    public String d_registration_no;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getD_password() {
        return d_password;
    }

    public void setD_password(String d_password) {
        this.d_password = d_password;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_status() {
        return d_status;
    }

    public void setD_status(String d_status) {
        this.d_status = d_status;
    }

    public String getD_inserted_date() {
        return d_inserted_date;
    }

    public void setD_inserted_date(String d_inserted_date) {
        this.d_inserted_date = d_inserted_date;
    }

    public String getD_remarks() {
        return d_remarks;
    }

    public void setD_remarks(String d_remarks) {
        this.d_remarks = d_remarks;
    }

    public String getD_about_me() {
        return d_about_me;
    }

    public void setD_about_me(String d_about_me) {
        this.d_about_me = d_about_me;
    }

    public String getD_specialization() {
        return d_specialization;
    }

    public void setD_specialization(String d_specialization) {
        this.d_specialization = d_specialization;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getD_medical_org_address() {
        return d_medical_org_address;
    }

    public void setD_medical_org_address(String d_medical_org_address) {
        this.d_medical_org_address = d_medical_org_address;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_contact_number() {
        return d_contact_number;
    }

    public void setD_contact_number(String d_contact_number) {
        this.d_contact_number = d_contact_number;
    }

    public String getD_medical_org_department() {
        return d_medical_org_department;
    }

    public void setD_medical_org_department(String d_medical_org_department) {
        this.d_medical_org_department = d_medical_org_department;
    }

    public String getD_medical_org_name() {
        return d_medical_org_name;
    }

    public void setD_medical_org_name(String d_medical_org_name) {
        this.d_medical_org_name = d_medical_org_name;
    }

    public String getD_registration_no() {
        return d_registration_no;
    }

    public void setD_registration_no(String d_registration_no) {
        this.d_registration_no = d_registration_no;
    }
}
