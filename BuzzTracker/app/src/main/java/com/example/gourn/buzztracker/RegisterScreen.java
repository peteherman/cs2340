package com.example.gourn.buzztracker;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterScreen extends AppCompatActivity {

    private enum Alerts {
        NAME, EMAIL, PASSLENGTH, PASSNUM, MATCH
    };

    private EditText nameField;
    private EditText emailField;
    private EditText passField;
    private EditText confirmPassField;
    private Spinner userTypeSpinner;

    public static final int MIN_PASS_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passwordField);
        confirmPassField = findViewById(R.id.confirmPassField);
        userTypeSpinner = findViewById(R.id.accountTypeSpinner);

        //Set up user type spinner
        ArrayAdapter<String> csAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        csAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(csAdapter);

    }


    public void onSubmit(View view) {
        String nameText = nameField.toString();
        String emailText = emailField.toString();
        String passText = passField.toString();
        String confirmPassText = confirmPassField.toString();
        UserType userType = (UserType)userTypeSpinner.getSelectedItem();

        //Check to make sure name field was entered
        if(nameText.length() == 0) {
            createAlert(Alerts.NAME);
        }

        //Check to make sure email was entered
        else if(emailText.length() == 0) {
            createAlert(Alerts.EMAIL);
        }

        //Check to make sure password is long enough
        else if (passText.length() < MIN_PASS_LENGTH) {
            createAlert(Alerts.PASSLENGTH);
        }

        //Check to make sure password has a number in it
        boolean containsNum = false;
        int digit = 0;
        while (!containsNum) {
            if (passText.contains("" + digit)) {
                containsNum = true;
            }
            digit++;
        }
        if (!containsNum) {
            createAlert(Alerts.PASSNUM);
        }
        //Check to make sure passwords match
        else if (!passText.equals(confirmPassField)) {
            createAlert(Alerts.MATCH);
        }
        User newUser = new User(nameText, emailText, passText);
        DB_Handler.addUser(newUser);
    }

    private void createAlert(Alerts type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Error");

        if (type.equals(Alerts.NAME)) {
            builder.setMessage("Name Field is Empty");
        } else if (type.equals(Alerts.EMAIL)) {
            builder.setMessage("Email Field is Empty");
        } else if (type.equals(Alerts.PASSLENGTH)) {
            builder.setMessage("Password is not long enough");
        } else if (type.equals(Alerts.PASSNUM)) {
            builder.setMessage("Password does not contain a number");
        } else if (type.equals(Alerts.MATCH)) {
            builder.setMessage("Passwords do not match");
        }
        builder.show();
    }
}
