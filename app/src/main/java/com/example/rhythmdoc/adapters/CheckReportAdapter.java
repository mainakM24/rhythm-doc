package com.example.rhythmdoc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rhythmdoc.R;
import com.example.rhythmdoc.models.Patient;

import java.util.List;

public class CheckReportAdapter extends RecyclerView.Adapter<CheckReportAdapter.ViewHolder>{

    private final List<Patient> patientList;


    public CheckReportAdapter(List<Patient> data) {
        this.patientList = data;
    }

    @NonNull
    @Override
    public CheckReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckReportAdapter.ViewHolder holder, int position) {
        Patient patient = patientList.get(position);

        holder.tvAction.setText(R.string.action);
        holder.tvName.setText(patient.getP_name());
        holder.tvPatientId.setText(patient.getPatient_id());
        holder.tvGender.setText(patient.getP_sex());
        holder.tvAge.setText(patient.getP_dob());
        holder.tvAdmissionDate.setText(patient.getPd_admission_date());
        holder.tvHospitalName.setText(patient.getPd_hospital_name());
        holder.tvBoardingId.setText(patient.getBoarding_id());
        holder.tvStatus.setText(patient.getP_status());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAction, tvPatientId, tvName, tvGender, tvAge, tvAdmissionDate, tvHospitalName, tvBoardingId, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tv_action);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPatientId = itemView.findViewById(R.id.tv_pid);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvHospitalName = itemView.findViewById(R.id.tv_hospital_name);
            tvAdmissionDate = itemView.findViewById(R.id.tv_admission_date);
            tvBoardingId = itemView.findViewById(R.id.tv_boarding_id);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}