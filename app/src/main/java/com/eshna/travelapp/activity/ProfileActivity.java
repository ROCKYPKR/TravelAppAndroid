package com.eshna.travelapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.MinimalResponse;
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

    private static final String TAG = ProfileActivity.class.getSimpleName();

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
        if (actionBar != null) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //for now we only have one menuItem ie. back button.
                UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                User user = userLocalStore.getLoggedInUser();


                if (!nameET.getText().toString().trim().equals(user.getName()) && !nameET.getText().toString().isEmpty()) {
                    //name is updated and is not empty
                    String name = nameET.getText().toString().trim();


                    hitUpdateUserApi(name, user.getApiToken());
                } else {
                    //just finish this activity without hitting the api or updating values in the shared prefs
                    ProfileActivity.this.finish();
                }
                return true;

            case R.id.action_log_out:
                displayLogOutConfirmation();
                return true;
            case R.id.action_change_password:
                displayPasswordChangeDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayPasswordChangeDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_change_password, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        final TextInputLayout newPassTIL1 = promptView.findViewById(R.id.til_new_pass1);
        final TextInputLayout newPassTIL2 = promptView.findViewById(R.id.til_new_pass2);
        final TextInputEditText newPassET1 = promptView.findViewById(R.id.change_pass_1);
        final TextInputEditText newPassET2 = promptView.findViewById(R.id.change_pass_2);


        TextView tvDone = promptView.findViewById(R.id.tv_done_change_pass);
        TextView tvCancel = promptView.findViewById(R.id.tv_cancel_change_pass);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(newPassET1.getText())) {
                    newPassTIL1.setError("Please provide a new Password");
                    return;
                }

                if (TextUtils.isEmpty(newPassET2.getText())) {
                    newPassTIL2.setError("Please confirm the password");
                    return;
                }

                if (!TextUtils.equals(newPassET1.getText(), newPassET2.getText())) {
                    newPassTIL2.setError("Passwords do not match");
                    return;
                }

                //all validation passed
                alertD.dismiss();

                hitChangePasswordApi(newPassET2.getText().toString().trim());


            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);

        alertD.show();
    }

    private void hitChangePasswordApi(String newPassword) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());

        HashMap<String, String> newPass = new HashMap<>();
        newPass.put("new_password", newPassword);

        Call<MinimalResponse> call = apiInterface.updatePassword(userLocalStore.getLoggedInUser().getApiToken(), newPass);

        call.enqueue(new Callback<MinimalResponse>() {
            @Override
            public void onResponse(@NonNull Call<MinimalResponse> call, @NonNull Response<MinimalResponse> response) {
                Log.d(TAG, "onResponse: " + response.message());
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        logUserOut();

                    } else {
                        Log.e(TAG, "onResponse: Response body is null");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<MinimalResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void displayLogOutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.log_out_confirmation)
                .setTitle(R.string.action_log_out)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logUserOut();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void logUserOut() {
        UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);

        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        ProfileActivity.this.finish();
    }

    private void hitUpdateUserApi(String name, String apiToken) {

        showProgressBar();

        HashMap<String, String> updatedUserDetails = new HashMap<>();
        updatedUserDetails.put("name", name);

        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Log.d(TAG, updatedUserDetails.toString());

        Call<UserResponse> call = apiInterface.getUpdatedUser(apiToken, updatedUserDetails);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, response.toString());

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
                        Log.e(TAG, " Response body is null");
                    }
                } else {
                    Log.e(TAG, " Response is not successful");
                }

                hideProgressBar();
                ProfileActivity.this.finish();
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " onFailure " + t.toString());
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
