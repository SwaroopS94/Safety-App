package com.example.swaroopsrinivasan.safety_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.Storage.SessionHandler;
import com.example.swaroopsrinivasan.safety_app.utils.SafetyAppSharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.splash_progress_bar)
    ProgressBar mProgressBar;

    SessionHandler mSessionHandler;
    SafetyAppSharedPref mSharedPefs;
    String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setAppConfigs();
    }

    public void setAppConfigs() {
        mSessionHandler = SessionHandler.getInstance();
        mSharedPefs = SafetyAppSharedPref.getInstance();
        mUserName = mSharedPefs.getUserName();
        if(mUserName != null) {
            mSessionHandler.setUserName(mUserName);
            startSafetyActivity();
        }
        else {
            startSignUpActivity();
        }
    }

    public void startSafetyActivity() {
        Intent intent = new Intent(this,SafetyActivity.class);
        startActivity(intent);
    }

    public void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
