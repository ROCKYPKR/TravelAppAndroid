package com.eshna.travelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.utility.CheckNetworkUtil;
import com.eshna.travelapp.utility.UserLocalStore;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    TextView emailLoginTV;
    @BindView(R.id.et_password)
    TextView passwordLoginTV;
    private boolean loggedIn;
    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userLocalStore = new UserLocalStore(getApplicationContext());

        if (CheckNetworkUtil.isInternetAvailable(getApplicationContext())) {
            //the internet is available
            //intentional delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToAppropriateActivity();
                }
            }, 2000); //3 seconds

        } else {

            Snackbar.make(findViewById(R.id.splash_root), "Please connect to internet and start the app.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SplashActivity.this.finish();
                        }
                    }).show();

        }

    }

    private void navigateToAppropriateActivity() {
        Intent intent;
        if (userLocalStore.getLoggedInUser() != null) {
            //we have a logged in user with api token
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        SplashActivity.this.finish(); //do not come back here
    }
}
