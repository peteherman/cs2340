package com.example.gourn.buzztracker.Controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.gourn.buzztracker.Model.Person;
import com.example.gourn.buzztracker.R;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void attemptLogin(View view) {
        EditText email = findViewById(R.id.emailField);
        EditText pass = findViewById(R.id.passwordField);
        DB_Handler db = new DB_Handler(this.getApplicationContext(), null, null, 1);
        String entered = email.getText().toString();
        Person user = db.loadUser(entered);

        if (email.getText().toString().equals(user.getEmail()) && pass.getText().toString().equals(user.getPassword())) {
            Intent intent = new Intent(this, AppScreen.class);
            Bundle bundle = new Bundle();
            bundle.putInt("USER_TYPE", user.getUserType().ordinal());
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login Failure");
            builder.setMessage("Username or Password was not correct.");
            builder.show();
        }
    }

    public void cancel(View view) {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
    }
}
