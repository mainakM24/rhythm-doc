package com.example.rhythmdoc.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.rhythmdoc.databinding.FragmentMyDetailsBinding;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Doctor;
import com.example.rhythmdoc.models.Patient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyDetailsFragment extends Fragment {

    private FragmentMyDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("login", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "error");
        String password = sharedPreferences.getString("password", "error");

        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Doctor>> doctorApiResponseCall = apiService.getUserByCredential(userId, password);
        doctorApiResponseCall.enqueue(new Callback<ApiResponse<Doctor>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Doctor>> call, @NonNull Response<ApiResponse<Doctor>> response) {

                if (response.isSuccessful()){
                    ApiResponse<Doctor> apiResponse = response.body();
                    assert apiResponse != null;
                    Doctor doctor = apiResponse.getItems().get(0);

                    loadMyInformationTable(doctor);

                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Doctor>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: patient", throwable);
            }
        });

        Call<ApiResponse<Patient>> activePatientApiResponseCall = apiService.getActivePatient(userId);
        activePatientApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
               if (response.isSuccessful()) {
                   ApiResponse<Patient> apiResponse = response.body();
                   assert apiResponse != null;
                   List<Patient> patients = apiResponse.getItems();
                   loadActiveOrOldPatientTable(patients, binding.tlActivePatient);

               } else {
                   System.err.println("Error: " + response.code() + " - " + response);
               }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

        Call<ApiResponse<Patient>> oldPatientApiResponseCall = apiService.getOldPatient(userId);
        oldPatientApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Patient> apiResponse = response.body();
                    assert apiResponse != null;
                    List<Patient> patients = apiResponse.getItems();
                    loadActiveOrOldPatientTable(patients, binding.tlOldPatient);

                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addTableRow(String label, String value) {

        TableRow row = new TableRow(getContext());

        // Create TextView for the label (first column)
        TextView labelTextView = new TextView(requireContext());
        labelTextView.setTextColor(Color.WHITE);
        labelTextView.setText(label);
        labelTextView.setPadding(16, 16, 16, 16); // Add padding
        labelTextView.setTextSize(16); // Set text size
        row.addView(labelTextView);

        // Create TextView for the value (second column)
        TextView valueTextView = new TextView(requireContext());
        valueTextView.setText(value != null ? value : "N/A"); // Handle null values
        valueTextView.setPadding(16, 16, 16, 16); // Add padding
        valueTextView.setTextSize(16); // Set text size
        row.addView(valueTextView);

        // Add the row to the TableLayout
        binding.tlMyInformation.addView(row);
    }

    private void loadMyInformationTable(@NonNull Doctor doctor){

        addTableRow("Doctor ID", doctor.getDoctor_id());
        addTableRow("Name", doctor.getD_name());
        addTableRow("Address", doctor.getD_address());
        addTableRow("Mobile", doctor.getD_contact_number());
        addTableRow("Email", doctor.getD_email());
        addTableRow("About Me", doctor.getD_about_me());
        addTableRow("Specialization", doctor.getD_specialization());
        addTableRow("Registration", doctor.getD_registration_no());
        addTableRow("Medical Org Name", doctor.getD_medical_org_name());
        addTableRow("Medical Org Address", doctor.getD_medical_org_address());
        addTableRow("Medical Org Department", doctor.getD_medical_org_department());
        addTableRow("Remarks", doctor.getD_remarks());
        addTableRow("Enlisted On", doctor.getD_inserted_date());

        binding.progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void loadActiveOrOldPatientTable(List<Patient> patients, TableLayout tableLayout) {
        int padding_6 = Math.round(6 * getResources().getDisplayMetrics().density);
        if (patients.isEmpty()) {
            TextView textView = new TextView(getContext());
            styleTextView(textView, padding_6);
            textView.setText("No patient is assigned");

            TableRow tableRow = (TableRow) tableLayout.getChildAt(0);
            tableRow.removeAllViews();
            tableRow.addView(textView);

            return;
        }

        for (Patient patient : patients) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setBackgroundColor(getResources().getColor(R.color.table_background, requireContext().getTheme()));
            tableRow.setPadding(0, padding_6, 0, padding_6);

            String[] data = {
                    patient.getPatient_id(),
                    patient.getP_name(),
                    patient.getP_sex(),
                    patient.getP_dob(),
                    patient.getPd_admission_date(),
                    patient.getPd_hospital_name(),
                    patient.getBoarding_id()
            };
            TextView[] textViews = new TextView[data.length];

            for (int i = 0; i < textViews.length; i++) {
                textViews[i] = new TextView(requireContext());
                styleTextView(textViews[i], padding_6);
                textViews[i].setText(data[i] != null ? data[i] : "N/A");
                tableRow.addView(textViews[i]);
            }

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