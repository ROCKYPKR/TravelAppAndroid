package com.eshna.travelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.RegistrationResponse;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.til_name)
    TextInputLayout nameTIL;
    @BindView(R.id.et_name)
    EditText nameET;
    @BindView(R.id.til_email)
    TextInputLayout emailTIL;
    @BindView(R.id.et_email)
    EditText emailET;
    @BindView(R.id.til_password)
    TextInputLayout passwordTIL;
    @BindView(R.id.et_password)
    EditText passwordET;
    @BindView(R.id.til_confirm_password)
    TextInputLayout passwordConfirmTIL;
    @BindView(R.id.et_password_comfirm)
    EditText passwordConfirmET;
    @BindView(R.id.spinner_country)
    Spinner countrySpinner;
    @BindView(R.id.rg_gender)
    RadioGroup genderRG;
    @BindView(R.id.rb_gender_male)
    RadioButton maleRB;
    @BindView(R.id.rb_gender_female)
    RadioButton femaleRB;
    @BindView(R.id.loading_registration)
    ProgressBar loadingPB;

    @BindString(R.string.incorrect_name)
    String incorrectName;
    @BindString(R.string.incorrect_email)
    String incorrectEmail;
    @BindString(R.string.country)
    String countrySelectionPrompt;
    @BindString(R.string.gender)
    String genderSelectionPrompt;
    @BindString(R.string.enter_password)
    String noPassword;
    @BindString(R.string.password_do_not_match)
    String passwordDoNotMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        loadCountrySpinner();
    }

    private void loadCountrySpinner() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        countrySpinner = (Spinner) findViewById(R.id.spinner_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(adapter);
    }

    /*
    On Click Handlers
     */
    @OnClick(R.id.tv_login)
    void navigateToLogin() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cancel_sign_up)
    void closeThisScreen() {
        SignUpActivity.this.finish();
    }

    @OnClick(R.id.btn_sign_up)
    void initiateSignUp() {

        if (!validateName() || !validateEmail() || !validatePassword() || !validateConfirmPassword() || !validateGender()) {
            //if either validation method fail, do not proceed
        } else {
            String name = nameET.getText().toString().trim();
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String country = countrySpinner.getSelectedItem().toString().trim();
            int selectedId = genderRG.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton selectedRB = (RadioButton) findViewById(selectedId);
            String gender = selectedRB.getText().toString().trim();
            hitRegistrationApi(name, email, password, gender, country);
        }
    }
    /*
    On Click Handlers end
     */

    private void hitRegistrationApi(String name, String email, String password, String gender, String country) {
        showProgressBar();
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("name", name);
        userDetails.put("email", email);
        userDetails.put("password", password);
        userDetails.put("gender", gender);
        userDetails.put("country", country);

        Log.d(LOG_TAG, userDetails.toString());

        Call<RegistrationResponse> call = apiInterface.getUserAfterRegistration(userDetails);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                Log.d(LOG_TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            //registration done, store data to shared prefs
                            UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                            userLocalStore.clearUserData();
                            userLocalStore.storeUserData(response.body().getUser());
                            userLocalStore.setUserLoggedIn(true); //set boolean user logged in

                            //On Successful SignUp, navigate to other activity
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            SignUpActivity.this.finish();
                        }
                    } else {
                        Log.e(LOG_TAG, " Response body is null");
                    }
                } else {
                    Log.e(LOG_TAG, " Response is not successful");
                }

                hideProgressBar();
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e(LOG_TAG, " onFailure " + t.toString());
                t.printStackTrace();
                Toast.makeText(SignUpActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        });
    }

    private void hideProgressBar() {
        loadingPB.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        loadingPB.setVisibility(View.VISIBLE);
    }

    /*
     * Validation methods
     */
    private boolean validateName() {
        if (nameET.getText().toString().trim().length() > 2) {
            nameTIL.setErrorEnabled(false);
            return true;
        } else {
            nameTIL.setError(incorrectName);
            return false;
        }
    }

    private boolean validateEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(emailET.getText()).matches()) {
            emailTIL.setErrorEnabled(false);
            return true;
        } else {
            emailTIL.setError(incorrectEmail);
            return false;
        }
    }

    private boolean validatePassword() {
        if (passwordET.getText().toString().isEmpty()) {
            passwordTIL.setError(noPassword);
            return false;
        } else {
            passwordTIL.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        if (passwordConfirmET.getText().toString().trim().equals(passwordET.getText().toString().trim())) {
            passwordConfirmTIL.setErrorEnabled(false);
            return true;
        } else {
            passwordConfirmTIL.setError(passwordDoNotMatch);
            return false;
        }
    }
    //spinner does not need validation

    private boolean validateGender() {
        if (genderRG.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(this, genderSelectionPrompt, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // one of the radio buttons is checked
            return true;
        }
    }

    /*
     * Validation methods end
     */


}
