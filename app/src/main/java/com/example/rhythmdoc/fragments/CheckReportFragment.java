package com.example.rhythmdoc.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhythmdoc.R;
import com.example.rhythmdoc.api.ApiService;
import com.example.rhythmdoc.api.RetrofitClient;
import com.example.rhythmdoc.databinding.FragmentCheckReportBinding;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckReportFragment extends Fragment {
    private FragmentCheckReportBinding binding;
    private List<Patient> oldPatientList;
    private List<Patient> activePatientList;
    private  List<Patient> allPatientList = new ArrayList<>();

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
                    for (Patient patient : oldPatientList) patient.setP_status("Old");
                    allPatientList.addAll(apiResponse.getItems());
                    updateTable(allPatientList);
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
                    for (Patient patient : activePatientList) patient.setP_status("Active");
                    allPatientList.addAll(activePatientList);
                    updateTable(allPatientList);
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchButton.setOnClickListener(view1 -> {
            String query = Objects.requireNonNull(binding.etSearchBox.getText()).toString().trim().toLowerCase();
            List<Patient> filteredList = new ArrayList<>();

            for (Patient patient : allPatientList) {
                if (
                    isMatching(patient.getPatient_id(), query) ||
                    isMatching(patient.getP_name(), query) ||
                    isMatching(patient.getBoarding_id(), query) ||
                    isMatching(patient.getPd_hospital_name(), query) ||
                    isMatching(patient.getP_dob(), query) ||
                    isMatching(patient.getP_status(), query) ||
                    isMatching(patient.getP_sex(), query) ||
                    isMatching(patient.getPd_admission_date(), query)
                ) filteredList.add(patient);
            }
            updateTable(filteredList);
        });

        updateTable(allPatientList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateTable(List<Patient> filteredList) {
        binding.tlAllPatient.removeViews(1, binding.tlAllPatient.getChildCount() - 1);
        addTableRow(filteredList, binding.tlAllPatient);
    }

    private boolean isMatching(String value, String query) {
        if (value == null || value.isEmpty()) return false;
        return value.toLowerCase().contains(query);
    }

    private void addTableRow(List<Patient> patientList, TableLayout tableLayout) {
        if (patientList == null || patientList.isEmpty()) return;
        int padding_6 = Math.round(6 * getResources().getDisplayMetrics().density);
        for (Patient patient : patientList) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setBackgroundColor(getResources().getColor(R.color.table_background, requireContext().getTheme()));
            tableRow.setPadding(0, padding_6, 0, padding_6);

            String[] data = {
                    "View",
                    patient.getPatient_id(),
                    patient.getP_name(),
                    patient.getP_sex(),
                    patient.getP_dob(),
                    patient.getPd_admission_date(),
                    patient.getPd_hospital_name(),
                    patient.getBoarding_id(),
                    patient.getP_status()
            };
            TextView[] textViews = new TextView[data.length];

            for (int i = 0; i < textViews.length; i++) {
                textViews[i] = new TextView(requireContext());
                styleTextView(textViews[i], padding_6);
                textViews[i].setText(data[i] != null ? data[i] : "N/A");
                tableRow.addView(textViews[i]);
            }

            textViews[0].setTextColor(requireContext().getColor(R.color.link));
            tableLayout.addView(tableRow);
        }
    }

    private void styleTextView(TextView textView, int paddingInPx) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = android.view.Gravity.CENTER;
        textView.setLayoutParams(layoutParams);

        TableRow.LayoutParams params = (TableRow.LayoutParams) textView.getLayoutParams();
        params.setMargins(1, 1, 1, 1); // 1dp margin on all sides
        textView.setLayoutParams(params);

        textView.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
    }
}