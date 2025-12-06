package com.example.rhythmdoc.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
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

import com.example.rhythmdoc.R;
import com.example.rhythmdoc.adapters.AdviceAdapter;
import com.example.rhythmdoc.api.ApiService;
import com.example.rhythmdoc.api.RetrofitClient;
import com.example.rhythmdoc.databinding.FragmentPatientDetailsBinding;
import com.example.rhythmdoc.models.Advice;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Patient;
import com.example.rhythmdoc.models.Session;

import java.util.ArrayList;
import java.util.List;

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
    private List<Session> sessionList = new ArrayList<>();
    private List<Advice> adviceList = new ArrayList<>();
    private AdviceAdapter adviceAdapter;
    private int limitMultiplier = 1;
    private final int LIMIT = 4;

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
        Call<ApiResponse<Session>> sessionApiResponseCall = apiService.getSessionDetails(patientId);
        Call<ApiResponse<Advice>> adviceApiResponseCall = apiService.getLoggedAdvice(patientId, LIMIT * limitMultiplier);

        //Patient Details API Call
        patientDetailsApiResponseCall.enqueue(new Callback<ApiResponse<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Patient>> call, @NonNull Response<ApiResponse<Patient>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Patient> apiResponse = response.body();
                    assert apiResponse != null;
                    patientDetails = apiResponse.getItems().get(0);
                    loadPatientInformationTable(patientDetails);
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ApiResponse<Patient>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: patientDetails details", throwable);
            }
        });

        //Session Details API Call
        sessionApiResponseCall.enqueue(new Callback<ApiResponse<Session>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Session>> call, @NonNull Response<ApiResponse<Session>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sessionList = response.body().getItems();
                    loadSessionTable(sessionList);
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Session>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: session details", throwable);
            }
        });

        //Advice API Call
        adviceApiResponseCall.enqueue(new Callback<ApiResponse<Advice>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Advice>> call, @NonNull Response<ApiResponse<Advice>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adviceList = response.body().getItems();
                    adviceAdapter = new AdviceAdapter(adviceList);
                    binding.rvAdvice.setAdapter(adviceAdapter);
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Advice>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: advice", throwable);
            }
        });

        //Loading the Admission Details table with data gotten through bundle
        loadAdmissionDetailsTable();

        //For loading more advice
        binding.tvLoadMore.setOnClickListener(view1 -> loadMoreAdviceRecyclerView(apiService, patientId, adviceAdapter));
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
        if (binding == null) return;

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
        if (binding == null) return;
        binding.tvAdmissionDate.setText(admissionDate);
        binding.tvBoardingId.setText(boardingId);
        binding.tvHospitalName.setText(hospitalName);
    }

    //TODO: implement no session case

    private void loadSessionTable(List<Session> sessionList) {
        if (binding == null) return;
        int padding_6 = Math.round(6 * getResources().getDisplayMetrics().density);
        for (Session session : sessionList) {
            TableRow tableRow = new TableRow(requireContext());
            tableRow.setBackgroundColor(getResources().getColor(R.color.table_background, requireContext().getTheme()));
            tableRow.setPadding(0, padding_6, 0, padding_6);

            TextView[] textViews = new TextView[7];
            String[] data = {
                    "Report",
                    patientId,
                    session.getSession_id(),
                    session.getStart_date(),
                    session.getEnd_date(),
                    String.valueOf(session.getTotal_analyzed_beat()),
                    String.valueOf(session.getMed_bpm())
            };

            for (int i = 0; i < textViews.length; i++) {
                textViews[i] = new TextView(requireContext());
                styleTextView(textViews[i]);
                textViews[i].setText(data[i] != null ? data[i] : "N/A");
                tableRow.addView(textViews[i]);
            }

            textViews[0].setTextColor(getResources().getColor(R.color.link, requireContext().getTheme()));
            textViews[0].setTypeface(null, Typeface.BOLD);

            binding.tlSessionList.addView(tableRow);
        }
    }

    private void styleTextView(TextView textView) {
        // Set layout gravity
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = android.view.Gravity.CENTER;
        textView.setLayoutParams(layoutParams);

        // Set margin
        TableRow.LayoutParams params = (TableRow.LayoutParams) textView.getLayoutParams();
        params.setMargins(1, 1, 1, 1); // 1dp margin on all sides
        textView.setLayoutParams(params);

        // Set padding
        int padding_6 = Math.round(6 * getResources().getDisplayMetrics().density);
        textView.setPadding(padding_6, padding_6, padding_6, padding_6);
    }

    private void loadMoreAdviceRecyclerView( ApiService apiService, String userId, AdviceAdapter adviceAdapter ) {
        Call<ApiResponse<Advice>> adviceApiResponseCall = apiService.getLoggedAdvice(userId, LIMIT * ++limitMultiplier);
        adviceApiResponseCall.enqueue(new Callback<ApiResponse<Advice>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Advice>> call, @NonNull Response<ApiResponse<Advice>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    int oldSize = adviceList.size();
                    adviceList.clear();
                    adviceList.addAll(response.body().getItems());
                    adviceAdapter.notifyItemRangeChanged(oldSize, adviceList.size());
                } else {
                    System.err.println("Error: " + response.code() + " - " + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Advice>> call, @NonNull Throwable throwable) {
                Log.e("api", "onFailure: advice", throwable);
            }
        });
    }
}