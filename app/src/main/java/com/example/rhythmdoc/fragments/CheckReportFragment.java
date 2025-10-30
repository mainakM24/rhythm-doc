package com.example.rhythmdoc.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rhythmdoc.R;
import com.example.rhythmdoc.adapters.CheckReportAdapter;
import com.example.rhythmdoc.api.ApiService;
import com.example.rhythmdoc.api.RetrofitClient;
import com.example.rhythmdoc.databinding.FragmentCheckReportBinding;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Patient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckReportFragment extends Fragment {
    private FragmentCheckReportBinding binding;
    private List<Patient> oldPatientList;
    private List<Patient> activePatientList;
    private List<Patient> allPatientList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("login", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "error");
        String password = sharedPreferences.getString("password", "error");

        ApiService apiService = RetrofitClient.getApiService();

        Call<ApiResponse<Patient>> oldPatientApiResponseCall = apiService.getOldPatient(userId);
        Call<ApiResponse<Patient>> activePatientApiResponseCall = apiService.getActivePatient(userId);

        oldPatientApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Patient> apiResponse = response.body();
                    assert apiResponse != null;
                    oldPatientList = apiResponse.getItems();
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: patient", throwable);
            }
        });

        activePatientApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Patient> apiResponse = response.body();
                    assert apiResponse != null;
                    activePatientList = apiResponse.getItems();
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

        if (oldPatientList != null) allPatientList.addAll(oldPatientList);
        if (activePatientList != null) allPatientList.addAll(activePatientList);

        CheckReportAdapter adapter = new CheckReportAdapter(allPatientList);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}