package com.example.rhythmdoc.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rhythmdoc.api.ApiService;
import com.example.rhythmdoc.api.RetrofitClient;
import com.example.rhythmdoc.databinding.FragmentPatientDetailsBinding;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Doctor;
import com.example.rhythmdoc.models.Patient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetailsFragment extends Fragment {

    private FragmentPatientDetailsBinding binding;
    private String patientId;
    private String boardingId;
    private String hospitalName;
    private String admissionDate;
    private Patient patientDetails;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientDetailsBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            patientId = bundle.getString("PATIENT_ID");
            boardingId = bundle.getString("BOARDING_ID");
            hospitalName = bundle.getString("HOSPITAL_NAME");
            admissionDate = bundle.getString("ADMISSION_DATE");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Patient>> patientDetailsApiResponseCall = apiService.getPatientDetails(patientId);

        patientDetailsApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Patient> apiResponse = response.body();
                    assert apiResponse != null;
                    patientDetails = apiResponse.getItems().get(0);
                    loadPatientInformationTable(patientDetails);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: patientDetails details", throwable);
            }
        });

        loadAdmissionDetailsTable();
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
        binding.tlPatientInformation.addView(row);
    }

    private void loadPatientInformationTable(@NonNull Patient patient){

        addTableRow("Patient ID", patient.getPatient_id());
        addTableRow("Name", patient.getP_name());
        addTableRow("Date of Birth", patient.getP_dob());
        addTableRow("Age", patient.getP_dob());
        addTableRow("Gender", patient.getP_sex());
        addTableRow("House No", patient.getP_house_no());
        addTableRow("Street Name", patient.getP_street_name());
        addTableRow("City", patient.getP_city());
        addTableRow("Pin Code", patient.getP_pin_cdoe());
        addTableRow("State", patient.getP_state());
        addTableRow("Country", patient.getP_country());
        addTableRow("Mobile", patient.getP_mobile());
        addTableRow("Email", patient.getP_email());
        addTableRow("Enlisted On", patient.getP_start_date());

        binding.progressBar.setVisibility(View.GONE);
    }

    private void loadAdmissionDetailsTable() {
        binding.tvAdmissionDate.setText(admissionDate);
        binding.tvBoardingId.setText(boardingId);
        binding.tvHospitalName.setText(hospitalName);
    }
}