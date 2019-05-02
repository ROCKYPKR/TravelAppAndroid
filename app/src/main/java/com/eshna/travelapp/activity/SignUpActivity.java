package com.eshna.travelapp.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.eshna.travelapp.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.til_name)
    TextInputLayout nameTIL;
    @BindView(R.id.et_name)
    EditText nameET;
    @BindView(R.id.et_email)
    EditText emailET;
    @BindView(R.id.et_password)
    EditText passwordET;
    @BindView(R.id.spinner_country)
    Spinner countrySpinner;
    @BindView(R.id.rg_gender)
    RadioGroup genderRG;
    @BindView(R.id.rb_gender_male)
    RadioButton maleRB;
    @BindView(R.id.rb_gender_female)
    RadioButton femaleRB;

    @BindString(R.string.incorrect_name)
    String incorrectName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login)
    void navigateToLogin(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cancel_sign_up)
    void closeThisScreen(){
        SignUpActivity.this.finish();
    }

    @OnClick(R.id.btn_sign_up)
    void initiateSignUp(){
        //TODO: Validation of all fields

        //TODO: HIT API

        //TODO: Navigate
        //On Successful SignUp
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        SignUpActivity.this.finish();
    }

    /**
     * Validation methods
     */
    private boolean validateName(){
        if (nameET.getText().toString().trim().length() > 2){
            nameTIL.setErrorEnabled(false);
            return true;
        } else {
            nameTIL.setError(incorrectName);
            return false;
        }
    }


}
