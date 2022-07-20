package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.moviecollection.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private ProgressBar progressBar;
    Button registerButton;
//    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailTextView = findViewById(R.id.reg_email);
        passwordTextView = findViewById(R.id.reg_password);
        progressBar = findViewById(R.id.register_progress_bar);


        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();

            }
        });
    }
}