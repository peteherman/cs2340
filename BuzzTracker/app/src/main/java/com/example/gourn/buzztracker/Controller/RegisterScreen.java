package com.example.gourn.buzztracker.Controller;



import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.gourn.buzztracker.R;
import com.example.gourn.buzztracker.Model.User;
import com.example.gourn.buzztracker.Model.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;



public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private enum Alerts {
    NAME, EMAIL, PASSLENGTH, PASSNUM, MATCH
  };



  private EditText nameField;
  private EditText emailField;
  private EditText passField;
  private EditText confirmPassField;
  private Spinner userTypeSpinner;
  private ProgressDialog progress;
  private Button submitButton;
  private Button cancelButton;

  private FirebaseAuth firebaseAuth;

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
    progress = new ProgressDialog(this);
    nameField = findViewById(R.id.nameField);
    emailField = findViewById(R.id.emailField);
    passField = findViewById(R.id.passField);
    confirmPassField = findViewById(R.id.confirmPassField);
    userTypeSpinner = findViewById(R.id.accountTypeSpinner);
    firebaseAuth = FirebaseAuth.getInstance();
    submitButton = findViewById(R.id.submitButton);
    cancelButton = findViewById(R.id.cancelButton);
    submitButton.setOnClickListener(this);
    cancelButton.setOnClickListener(this);

    //Set up user type spinner

    ArrayAdapter<String> csAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
    csAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    userTypeSpinner.setAdapter(csAdapter);


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

  @Override
  public void onClick(View view) {
      if (view == submitButton) {
          onSubmit();
      }
      if (view == cancelButton) {
          cancelRegistration(view);
      }
  }



  public void onSubmit() {
      boolean isSubmit = true;
      nameText = nameField.getText().toString().trim();
      emailText = emailField.getText().toString().trim();
      passText = passField.getText().toString().trim();
      confirmPassText = confirmPassField.getText().toString().trim();
      userType = (UserType)userTypeSpinner.getSelectedItem();
//
//
//
//      //Check for new alerts
//      alertsList.clear();
//
//
      //Check to make sure name field was entered
      if(nameText.isEmpty()) {
          nameField.setError("Name is required");
          nameField.requestFocus();
          return;
      }

      //Check to make sure email was entered
      if(emailText.isEmpty()) {
          emailField.setError("Email is required");
          emailField.requestFocus();
          return;
      }
      //Check to make sure password was entered
      if(passText.isEmpty()) {
          passField.setError("Password is required");
          passField.requestFocus();
          return;
      }
      boolean isValidPass = true;

      //Check to make sure password is long enough
      if (passField != null) {
          passText = passField.getText().toString();
          if (passText.length() < MIN_PASS_LENGTH) {
              isValidPass = false;
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
              isValidPass = false;
          }
      }
      if (!isValidPass) {
          passField.setError("Password must be at least 7 letters and at least one number");
          passField.requestFocus();
          return;
      }

      if(confirmPassText.isEmpty()) {
          confirmPassField.setError("Confirm password is required");
          confirmPassField.requestFocus();
          return;
      }
      //Check to make sure passwords match
      if (!(passText.equals(confirmPassText))) {
          confirmPassField.setError("Passwords don't match");
          confirmPassField.requestFocus();
          return;
      }
      User user = new User(nameText, emailText, passText, userType);
      progress.setMessage("Registering. Please wait...");
      progress.show();
      firebaseAuth.createUserWithEmailAndPassword(emailText, passText)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          Toast.makeText(RegisterScreen.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(RegisterScreen.this, AppScreen.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(intent);
                      } else {
                          if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                              Toast.makeText(getApplicationContext(), "Already registered email", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
              });

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