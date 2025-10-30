package com.example.rhythmdoc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rhythmdoc.api.ApiService;
import com.example.rhythmdoc.api.RetrofitClient;
import com.example.rhythmdoc.databinding.ActivityLoginBinding;
import com.example.rhythmdoc.models.ApiResponse;
import com.example.rhythmdoc.models.Doctor;
import com.example.rhythmdoc.utils.NetworkUtil;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(this, BaseActivity.class);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean remembered = sharedPreferences.getBoolean("remembered", false);
        if (remembered){
            startActivity(intent);
            finish();
        }

        //checking internet connection
        if (!NetworkUtil.isNetworkAvailable(this)) {
            Toast.makeText(this, "Turn On Internet", Toast.LENGTH_LONG).show();
        }

        //clicking sign in button
        binding.btSignIn.setOnClickListener(view -> {

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                binding.pbLoginProgressBar.setVisibility(View.VISIBLE);
            }, 1000);
            binding.tilUsername.setError(null);
            binding.tilPassword.setError(null);

            //retrieving form data
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString().trim();


            //Calling API using retrofit
            ApiService apiService = RetrofitClient.getApiService();
            Call<ApiResponse<Doctor>> apiResponseCall = apiService.getUserByCredential(username, password);


            apiResponseCall.enqueue(new Callback<ApiResponse<Doctor>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<Doctor>> call, @NonNull Response<ApiResponse<Doctor>> response) {
                    if (response.isSuccessful()){
                        ApiResponse<Doctor> apiResponse = response.body();
                        assert apiResponse != null;
                        if (apiResponse.getCount() == 1){
                            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            if (binding.cbRememberMe.isChecked()){
                                editor.putBoolean("remembered", true);
                            }
                            editor.putString("userId", username);
                            editor.putString("password", password);
                            editor.apply();
                            startActivity(intent);
                            finish();
                        } else {
                            binding.pbLoginProgressBar.setVisibility(View.GONE);

                            binding.tilUsername.setErrorEnabled(true);
                            binding.tilPassword.setErrorEnabled(true);

                            binding.tilUsername.setError("Check Username");
                            binding.tilPassword.setError("Check Password");
                        }
                    } else {
                        System.err.println("Error: " + response.code() + " - " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<Doctor>> call, @NonNull Throwable throwable) {
                    Log.e("api", "onFailure: ", throwable);
                }
            });
            binding.pbLoginProgressBar.setVisibility(View.GONE);
        });
    }
}