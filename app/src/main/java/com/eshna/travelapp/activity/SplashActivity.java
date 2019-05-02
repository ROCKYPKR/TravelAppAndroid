package com.eshna.travelapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eshna.travelapp.R;

import java.util.regex.Pattern;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {

    private boolean loggedIn;

    @BindView(R.id.et_email)
    TextView emailLoginTV;
    @BindView(R.id.et_password)
    TextView passwordLoginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //TODO: Validate fields, on success, hit api, on success go to next appropriate activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToAppropriateActivity();
            }
        }, 4000); //4 seconds

    }

    private void navigateToAppropriateActivity() {
        Intent intent;
        if (loggedIn){
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
        intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);

        SplashActivity.this.finish(); //do not come back here
    }

    private boolean validateEmail(){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailLoginTV.getText())
                .matches();
    }

    private boolean validatePassword(){
        return !emailLoginTV.getText().toString().isEmpty();
    }
}
