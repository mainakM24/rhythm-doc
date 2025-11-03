package com.example.rhythmdoc.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rhythmdoc.databinding.FragmentPatientDetailsBinding;

public class PatientDetailsFragment extends Fragment {

    FragmentPatientDetailsBinding binding;
    String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientDetailsBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("USER_ID");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textView.setText(userId);
    }
}