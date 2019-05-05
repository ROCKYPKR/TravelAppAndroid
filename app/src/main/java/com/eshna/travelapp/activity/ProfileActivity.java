package com.eshna.travelapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.UserResponse;
import com.eshna.travelapp.model.User;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = ProfileActivity.class.getSimpleName();

    @BindView(R.id.tv_name_initials)
    TextView nameInitialsTV;
    @BindView(R.id.et_name_profile)
    EditText nameET;
    @BindView(R.id.et_email_profile)
    EditText emailET;
    @BindView(R.id.et_country_profile)
    EditText countryET;
    @BindView(R.id.et_gender_profile)
    EditText genderET;

    @BindView(R.id.pb_profile_updating)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setUserDetailsToView();
    }

    private void setUserDetailsToView() {
        UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
        User user = userLocalStore.getLoggedInUser();

        nameET.setText(user.getName());
        emailET.setText(user.getEmail());
        nameInitialsTV.setText(computePersonNameInitials(user.getName()));
        countryET.setText(user.getCountry());
        genderET.setText(user.getGender());

    }

    private String computePersonNameInitials(String name) {
        String initials;
        Character character1 = name.charAt(0);
        Character character2;
        if (name.contains(" ")) {
            character2 = name.charAt(name.indexOf(" ") + 1);
        } else {
            character2 = name.charAt(1);
        }

        initials = String.valueOf(character1) + String.valueOf(character2);
        return initials.toUpperCase(Locale.ENGLISH);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //for now we only have one menuItem ie. back button.
        UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
        User user = userLocalStore.getLoggedInUser();

        HashMap<String, String> updatedUserDetails = new HashMap<>();

        if (!nameET.getText().toString().trim().equals(user.getName()) && !nameET.getText().toString().isEmpty()){
            //name is updated and is not empty
            String name = nameET.getText().toString().trim();
            updatedUserDetails.put("name", name);

            hitUpdateUserApi(updatedUserDetails, user.getApiToken());
        } else {
            //just finish this activity without hitting the api or updating values in the shared prefs
            ProfileActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void hitUpdateUserApi(HashMap updatedUserDetails, String apiToken) {

        showProgressBar();

        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Log.d(LOG_TAG, updatedUserDetails.toString());

        Call call = apiInterface.getUpdatedUser(apiToken, updatedUserDetails);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                Log.d(LOG_TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            //update to api done, store user data to shared prefs
                            UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                            userLocalStore.clearUserData();
                            userLocalStore.storeUserData(response.body().getUser());
                            userLocalStore.setUserLoggedIn(true); //set boolean user logged in
                        }
                    } else {
                        Log.e(LOG_TAG, " Response body is null");
                    }
                } else {
                    Log.e(LOG_TAG, " Response is not successful");
                }

                hideProgressBar();
                ProfileActivity.this.finish();
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, " onFailure " + t.toString());
                t.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                hideProgressBar();
                ProfileActivity.this.finish();
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
