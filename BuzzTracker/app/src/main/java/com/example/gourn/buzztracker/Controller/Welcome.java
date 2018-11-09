package com.example.gourn.buzztracker.Controller;import android.content.Intent;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.View;import android.widget.Button;import com.example.gourn.buzztracker.Controller.LoginScreen;import com.example.gourn.buzztracker.Controller.RegisterScreen;import com.example.gourn.buzztracker.R;public class Welcome extends AppCompatActivity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.welcome);        Button login = findViewById(R.id.sign_in);        login.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(view.getContext(), LoginScreen.class);                Bundle bundle = new Bundle();                if ((getIntent() != null) && (getIntent().getExtras() != null)) {                    //if (getIntent().getExtras().getInt("USER_TYPE")) {                        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));                    //}                }                intent.putExtras(bundle);                startActivityForResult(intent, 0);            }        });        Button register = (Button) findViewById(R.id.register);        register.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent intent = new Intent(view.getContext(), RegisterScreen.class);                Bundle bundle = new Bundle();                if ((getIntent() != null) && (getIntent().getExtras() != null)) {                    //if (getIntent().getExtras().getInt("USER_TYPE")) {                    bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));                    //}                }                intent.putExtras(bundle);                startActivityForResult(intent, 0);            }        });    }}