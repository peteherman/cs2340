package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText emailField;
    private EditText passField;
    private Button loginButton;
    private Button cancelButton;
    private FirebaseAuth mAuth;
    private SignInButton googleButton;
    private final static int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button forgotButton;
    private Button guestLoginButton;
    GoogleApiClient mGoogleApiClient;
    private int loginAttempts = 0;
    private HashMap<String, Integer> logins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logins = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        cancelButton = findViewById(R.id.cancelButton);
        googleButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        forgotButton = findViewById(R.id.forgot_button);
        guestLoginButton = findViewById(R.id.guestLoginButton);
        cancelButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        forgotButton.setOnClickListener(this);
        guestLoginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginScreen.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(LoginScreen.this, AppScreen.class);
                    Bundle bundle = new Bundle();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    bundle.putInt("USER_TYPE", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        }
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(LoginScreen.this , "Google sign in failed", Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void attemptLogin(final String email, String pass) {

        if (logins != null && logins.get(email) != null && logins.get(email) >= 3) {
            emailField.setError("This account is temporarily locked.");
            emailField.requestFocus();
            return;
        }

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

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Successful",
                            Toast.LENGTH_SHORT).show();


                    //Get user who just logged in
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String currentUserID = Objects.requireNonNull(currentUser).getUid();

                    //Get database entry for user who logged in
                    DatabaseReference loginDatabase = FirebaseDatabase.getInstance()
                            .getReference().child("Users").child(currentUserID);


                    loginDatabase.addValueEventListener(new ValueEventListener() {
                        int typeNumber;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String userType = Objects.requireNonNull(dataSnapshot.child("userType").getValue()).toString();
                            Log.d("User type from db", userType);
                            typeNumber = UserType.getOrdinalFromValue(userType);
                            if (typeNumber < 0) {
                                Log.d("User Type Error",
                                        "User Type stored incorrectly in database.");
                            }
                            Intent intent = new Intent(LoginScreen.this, AppScreen.class);
                            Bundle bundle = new Bundle();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            bundle.putInt("USER_TYPE", typeNumber);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                } else {
                    loginAttempts++;
                    logins.put(email, loginAttempts);
                    if (loginAttempts == 3) {
                        Toast.makeText(LoginScreen.this, "Account temporarily locked.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginScreen.this, "Incorrect Password, " + (3 - loginAttempts) + " attempts left."
                                , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void cancel() {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
            finish();
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            attemptLogin(emailField.getText().toString(), passField.getText().toString());
        }
        if (v == cancelButton) {
            cancel();
        }
        if (v == forgotButton) {
            Intent intent = new Intent(this, ForgotPassword.class);
            startActivity(intent);
            finish();
        }
        if (v == guestLoginButton) {
            attemptLogin("guest@gmail.com", "abcdefg1");
        }
    }
}
