package com.eshna.travelapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eshna.travelapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private boolean loggedInCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_sign_up_prompt)
    void navigateToSignUp() {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void attemptLogin() {
        //TODO: Hit api and on successful login call navigateToDashboard
        //for now
        loggedInCondition = true; //temporary
        if (loggedInCondition){
            navigateToDashboard();
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
