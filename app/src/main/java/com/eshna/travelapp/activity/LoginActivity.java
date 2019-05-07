package com.eshna.travelapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.UserResponse;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.til_email_login)
    TextInputLayout emailTIL;
    @BindView(R.id.et_email)
    EditText emailET;
    @BindView(R.id.til_password_login)
    TextInputLayout passwordTIL;
    @BindView(R.id.et_password)
    EditText passwordET;
    @BindView(R.id.pb_login_loading)
    ProgressBar loginPB;

    @BindString(R.string.incorrect_email)
    String incorrectEmail;
    @BindString(R.string.enter_password)
    String noPassword;



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

        if (validateEmail() && validatePassword()){
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            attemptLoginToApi(email, password);

        } else {
            //do nothing
            Toast.makeText(this, "Cannot log you in", Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptLoginToApi(String email, String password) {
        showProgressBar();

        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, String> userCredentials = new HashMap<>();
        userCredentials.put("email", email);
        userCredentials.put("password", password);

        Log.d(LOG_TAG, userCredentials.toString());

        Call<UserResponse> call = apiInterface.getUserLogin(userCredentials);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                Log.d(LOG_TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            //login done, store user data to shared prefs
                            UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                            userLocalStore.clearUserData();
                            userLocalStore.storeUserData(response.body().getUser());
                            userLocalStore.setUserLoggedIn(true); //set boolean user logged in

                            //On Successful login, navigate to other activity
                            navigateToDashboard();
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, " onFailure " + t.toString());
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        });
    }

    private void hideProgressBar() {
        loginPB.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        loginPB.setVisibility(View.VISIBLE);
    }

    /*
    Validation methods
     */
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
    /*
    Validation methods end
     */

    private void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
