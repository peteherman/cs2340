package com.example.gourn.buzztracker.Controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gourn.buzztracker.Model.Person;
import com.example.gourn.buzztracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText emailField;
    private EditText passField;
    private Button loginButton;
    private Button cancelButton;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void attemptLogin() {
        String email = emailField.getText().toString().trim();
        String pass = passField.getText().toString().trim();

        //Check to make sure email was entered
        if(email.isEmpty()) {
            emailField.setError("Email is required");
            emailField.requestFocus();
            return;
        }
        //Check to make sure password was entered
        if(pass.isEmpty()) {
            passField.setError("Password is required");
            passField.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging you in");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScreen.this, AppScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
//        String entered = email.getText().toString();
//
//        if (email.getText().toString().equals(user.getEmail()) && pass.getText().toString().equals(user.getPassword())) {
//            Intent intent = new Intent(this, AppScreen.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt("USER_TYPE", user.getUserType().ordinal());
//            intent.putExtras(bundle);
//            startActivity(intent);
//
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Login Failure");
//            builder.setMessage("Username or Password was not correct.");
//            builder.show();
//        }
    }

    public void cancel(View view) {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            attemptLogin();
        }
        if (v == cancelButton) {
            cancel(v);
        }
    }
}
