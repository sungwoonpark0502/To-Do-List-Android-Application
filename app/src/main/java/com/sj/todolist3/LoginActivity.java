package com.sj.todolist3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.signupButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton:
                    logIn();
                    break;
                case R.id.signupButton:
                    signUp();
                    break;
            }
        }
    };

    public void logIn() {
        String email = ((EditText)findViewById(R.id.editTextEmailAddress)).getText().toString();
        email += "@osu.edu";
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            startToast("email or password is not correct");
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // After pressing the login button, check rather this user verified their email or not
                        if (user.isEmailVerified()) {
                            startToast("Login Success");
                            Data.setEmail(((EditText)findViewById(R.id.editTextEmailAddress)).getText().toString());
                            listStartAcitivty(TodoListActivity.class);
                        } else {
                            startToast("Please verify your email before log in");
                        }
                    } else {
                        if (task.getException() != null) {
                            startToast(task.getException().toString());
                        }
                    }
                }
            });
        }
    }

    /**
     * Sign up page intent
     */
    public void signUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    View.OnClickListener LoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    /**
     * Toast a message
     * @param msg
     */
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void listStartAcitivty(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}