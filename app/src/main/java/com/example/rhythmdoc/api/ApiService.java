package com.example.rhythmdoc.api;

import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Doctor;
import com.example.rhythmdoc.models.Patient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("user/v1/validate/doctor/{uid}/{upw}")
    Call<ApiResponse<Doctor>> getUserByCredential(
            @Path("uid") String userId,
            @Path("upw") String userPassword
    );

    @GET("doctor/v1/activepatient/{docid}")
    Call<ApiResponse<Patient>> getActivePatient(
            @Path("docid") String doctorId
    );

    @GET("doctor/v1/oldpatient/{docid}")
    Call<ApiResponse<Patient>> getOldPatient(
            @Path("docid") String doctorId
    );

    @GET("user/v1/changepassword/{uid}/{opass}/{npass}")
    Call<Void> changePassword(
            @Path("uid") String userId,
            @Path("opass") String oldPassword,
            @Path("npass") String newPassword
    );
}
