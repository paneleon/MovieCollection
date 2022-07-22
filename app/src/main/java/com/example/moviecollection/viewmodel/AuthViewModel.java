package com.example.moviecollection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviecollection.repositories.AuthRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    public AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public FirebaseUser getCurrentUser(){
        return authRepository.getCurrentUser();
    }

    public boolean signUp(String email, String password, String username, String fName, String lName, int age, String gender){
        return authRepository.signUp(email, password, username, fName, lName, age, gender);
    }


    public boolean login(String email, String password){
        return authRepository.login(email, password);
    }

    public void signOut(){
        authRepository.signOut();
    }
}
