package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moviecollection.R;
import com.example.moviecollection.viewmodel.AuthViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, fNameEditText, lNameEditText, usernameEditText, ageEditText;
    Spinner genderSpinner;
    private ProgressBar progressBar;
    Button registerButton;
    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        fNameEditText = findViewById(R.id.reg_first_name);
        lNameEditText = findViewById(R.id.reg_last_name);
        usernameEditText = findViewById(R.id.reg_username);
        ageEditText = findViewById(R.id.reg_age);
        genderSpinner = findViewById(R.id.reg_gender);

        emailEditText = findViewById(R.id.reg_email);
        passwordEditText = findViewById(R.id.reg_password);
        progressBar = findViewById(R.id.register_progress_bar);


        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email, password, fName, lName, username, gender;
                int age;
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                fName = fNameEditText.getText().toString();
                lName = lNameEditText.getText().toString();
                username = usernameEditText.getText().toString();
                age = Integer.parseInt(ageEditText.getText().toString());
                gender = genderSpinner.getSelectedItem().toString();

                boolean success = authViewModel.signUp(email, password, username, fName, lName, age, gender);
                if (success){
                    Toast.makeText(getApplicationContext(),"Registration successful!!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}