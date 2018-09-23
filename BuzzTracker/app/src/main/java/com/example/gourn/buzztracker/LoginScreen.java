package com.example.gourn.buzztracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void attemptLogin(View view) {
        EditText email = findViewById(R.id.emailField);
        EditText pass = findViewById(R.id.passwordField);

        if (email.getText().toString().equals("user") && pass.getText().toString().equals("pass")) {
            //setContentView(R.layout.activity_app_screen);
           // Intent intent = new Intent(this, AppScreen.class);
           // startActivity(intent);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login Failure");
            builder.setMessage("Username or Password was not correct." + email.getText() + pass.getText());
            builder.show();
        }
    }

    public void cancel(View view) {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
    }
}
