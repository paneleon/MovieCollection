package com.example.moviecollection.views;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecollection.R;
import com.example.moviecollection.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText emailTextView, passwordTextView;
    private ProgressBar progressBar;
    Button loginButton;
    TextView registerRedirect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (authViewModel.getCurrentUser() == null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        emailTextView = findViewById(R.id.login_email);
        passwordTextView = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.loading_progress_bar);

        registerRedirect = findViewById(R.id.register_redirect);
        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();

                boolean success = authViewModel.login(email, password);
                if (success){
                    Toast.makeText(getApplicationContext(),"Login successful!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}