package com.example.moviecollection.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviecollection.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthRepository {

    FirebaseAuth auth;
    boolean authResult;
    private Context context;
    private static FirebaseUser currentUser;
    private static final String DB_NAME = "Users";
    private static final String DB_URL = "https://moviecollection-d43bf-default-rtdb.firebaseio.com/";
    FirebaseDatabase database;
    public static DatabaseReference dbRef;

    public AuthRepository(Context context){
        auth = FirebaseAuth.getInstance();
        this.context = context;
        database = FirebaseDatabase.getInstance(DB_URL);
        dbRef = database.getReference(DB_NAME);
    }

    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public void setCurrentUser(FirebaseUser user){
        currentUser = user;
    }

    public boolean signUp(String email, String password, String username, String fName, String lName, int age, String gender){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "createUserWithEmail:success");
                    setCurrentUser(task.getResult().getUser());

                    createUser(new User(currentUser.getUid(), fName, lName, username, currentUser.getEmail(), age, gender));
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                authResult = task.isSuccessful();
            }
        });
        return authResult;
    }

    public void createUser(User newUser){
        dbRef.push().setValue(newUser);
    }

    public boolean login(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "loginUserWithEmail:success");
                    setCurrentUser(task.getResult().getUser());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "loginUserWithEmail:failure", task.getException());
                    Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                authResult = task.isSuccessful();
            }
        });
        return authResult;
    }

    public void signOut(){
        auth.signOut();
    }
}
