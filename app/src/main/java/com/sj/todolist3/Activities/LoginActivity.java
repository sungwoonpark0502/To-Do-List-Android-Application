package com.sj.todolist3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sj.todolist3.Data;
import com.sj.todolist3.R;
import com.sj.todolist3.User;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;

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
        final String[] email = {((EditText) findViewById(R.id.editTextEmailAddress)).getText().toString()};
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        if (email[0].isEmpty() || password.isEmpty()) {
            startToast("email or password is not correct");
        } else {
            mAuth.signInWithEmailAndPassword(email[0], password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // After pressing the login button, check rather this user verified their email or not
                        if (user.isEmailVerified()) {
                            startToast("Login Success");
                            User loginUser = new User();
                            loginUser.setEmail(((EditText)findViewById(R.id.editTextEmailAddress)).getText().toString());
                            ((EditText) findViewById(R.id.editTextEmailAddress)).getText().clear();
                            ((EditText) findViewById(R.id.editTextPassword)).getText().clear();
//                            if (!Data.isCurrUser(loginUser.getEmail())) {
//                                Data.addNewUser(loginUser.getEmail());
//                            }
                            listStartAcitivty(TodoListActivity.class, loginUser);
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

    private void listStartAcitivty(Class c, User user) {
        Intent intent = new Intent(this, c);
        intent.putExtra("User Email", user.getEmail());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // Here you want to show the user a dialog box
        Log.d("BACK BUTTON", " is pressed");
        new AlertDialog.Builder(this)
                .setTitle("Exiting the App")
                .setMessage("Are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // The user is not sure, so you can exit or just stay
                dialog.dismiss();
            }
        }).show();
    }
}