package com.example.gourn.buzztracker.Controller;



import android.content.DialogInterface;

import android.content.Intent;

import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;


import com.example.gourn.buzztracker.R;
import com.example.gourn.buzztracker.Model.User;
import com.example.gourn.buzztracker.Model.UserType;

import java.util.ArrayList;



public class RegisterScreen extends AppCompatActivity {

    private enum Alerts {
    NAME, EMAIL, PASSLENGTH, PASSNUM, MATCH
  };



  private EditText nameField;
  private EditText emailField;
  private EditText passField;
  private EditText confirmPassField;
  private Spinner userTypeSpinner;

  private String nameText = "";
  private  String emailText = "";
  private String passText = "";
  private String confirmPassText = "";
  private UserType userType = null;

  public static final int MIN_PASS_LENGTH = 8;



  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_screen);
    nameField = findViewById(R.id.nameField);
    emailField = findViewById(R.id.emailField);
    passField = findViewById(R.id.passField);
    confirmPassField = findViewById(R.id.confirmPassField);
    userTypeSpinner = findViewById(R.id.accountTypeSpinner);
    final Button submitButton = findViewById(R.id.submitButton);
    final Button cancelButton = findViewById(R.id.cancelButton);
    final ArrayList<Alerts> alertsList = new ArrayList<>();
    final AlertDialog.Builder build = new AlertDialog.Builder(this);





    //Set up user type spinner

    ArrayAdapter<String> csAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
    csAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    userTypeSpinner.setAdapter(csAdapter);





    //Set up click listener for submit button

    submitButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        boolean isSubmit = true;
        nameText = "";
        emailText = "";
        passText = "";
        confirmPassText = "";

        //userType = (UserType)userTypeSpinner.getSelectedItem();



        //Check for new alerts
        alertsList.clear();


        //Check to make sure name field was entered
        if(nameField != null) {
          nameText = nameField.getText().toString();
          if (nameText.length() == 0) {
            isSubmit = false;
            alertsList.add(Alerts.NAME);
          }
        } else {
          isSubmit = false;
          alertsList.add(Alerts.NAME);
        }



        //Check to make sure email was entered
        if(emailField != null) {
          emailText = emailField.getText().toString();
          if (emailText.length() == 0) {
            isSubmit = false;
            alertsList.add(Alerts.EMAIL);
          }
        } else {
          isSubmit = false;
          alertsList.add(Alerts.EMAIL);
        }



        //Check to make sure password is long enough
        if (passField != null) {
          passText = passField.getText().toString();
          if (passText.length() < MIN_PASS_LENGTH) {
            isSubmit = false;
            alertsList.add(Alerts.PASSLENGTH);
          }
          //Check to make sure password has a number in it
          boolean containsNum = false;
          int digit = 0;
          while (digit < 10) {
            if (passText.contains("" + digit)) {
              containsNum = true;
            }
            digit++;
          }
          if (!containsNum) {
            isSubmit = false;
            alertsList.add(Alerts.PASSNUM);
          }
        } else {
          isSubmit = false;
          alertsList.add(Alerts.PASSLENGTH);
        }

        if (confirmPassField != null) {
          confirmPassText = confirmPassField.getText().toString();
        }



        //Check to make sure passwords match
        if (!(passText.equals(confirmPassText))) {
          isSubmit = false;
          alertsList.add(Alerts.MATCH);
        }



        if (isSubmit) {
          onSubmit(v, null, nameText, emailText, passText, userType);
          //submitRegistration(v);
        } else {
          createAlert(alertsList);
        }
      }

    });



    //Set up click listener for cancel button
    cancelButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        // Code here executes on main thread after user presses button
        cancelRegistration(v);
      }

    });

    //Set up spinner selectionlistener
      userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> a, View v,
          int pos, long arg3) {
                userType = (UserType)a.getItemAtPosition(pos);
          }

          @Override
          public void onNothingSelected(AdapterView<?> a) {
              userType = UserType.USER;
          }
      });

  }



  public void onSubmit(View view, Object factory, String nameText, String emailText,

                       String passText, UserType userType) {

    User user = new User(nameText, emailText, passText, userType);
    DB_Handler dbHandler = new DB_Handler(this.getApplicationContext(), null, null, 1);

    dbHandler.addUser(user);
    Intent intent = new Intent(this, AppScreen.class);
    Bundle bundle = new Bundle();
    bundle.putInt("USER_TYPE", user.getUserType().ordinal());
    intent.putExtras(bundle);

    startActivity(intent);

    finish();

  }





  private void createAlert(ArrayList<Alerts> alertsList) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    builder.setTitle("Registration Error");


    if (alertsList.contains(Alerts.NAME)) {
      adapter.add("Name Field is Empty");
//            builder.setMessage("Name Field is Empty");
    }
    if (alertsList.contains(Alerts.EMAIL)) {
      adapter.add("Email Field is Empty");
//            builder.setMessage("Email Field is Empty");
    }

    if (alertsList.contains(Alerts.PASSLENGTH)) {
      adapter.add("Password is not long enough");
//            builder.setMessage("Password is not long enough");
    }

    if (alertsList.contains(Alerts.PASSNUM)) {
      adapter.add("Password does not contain a number");
//            builder.setMessage("Password does not contain a number");
    }

    if (alertsList.contains(Alerts.MATCH)) {
      adapter.add("Passwords do not match");
//            builder.setMessage("Passwords do not match");

    }

    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int item) {



      }

    });

    AlertDialog alert = builder.create();

    alert.show();

  }



  private void cancelRegistration(View v) {

    Intent intent = new Intent(this, Welcome.class);

    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    startActivity(intent);

    finish();

  }



//  private void submitRegistration(View v) {
//
//    Intent intent = new Intent(this, AppScreen.class);
//    Bundle bundle = new Bundle();
//    bundle.putInt("USER_TYPE", user.getUserType().ordinal());
//    intent.putExtras(bundle);
//
//    startActivity(intent);
//
//    finish();
//
//  }

}