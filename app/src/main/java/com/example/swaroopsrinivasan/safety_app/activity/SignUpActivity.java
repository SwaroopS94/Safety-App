package com.example.swaroopsrinivasan.safety_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;
import com.example.swaroopsrinivasan.safety_app.utils.SafetyAppSharedPref;
import com.example.swaroopsrinivasan.safety_app.utils.SecureUserCredentials;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_sign_up) Button btn_sign_up;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.et_sign_up_name)
    EditText et_sign_up_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        btn_sign_up.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_sign_up:
                Toast.makeText(this, "Your SignUp name is : "+et_sign_up_name.getText(),Toast.LENGTH_SHORT).show();
                String signUpName = userNameValidation();
                if(signUpName != null) {
                    SafetyAppSharedPref.getInstance().setUserName(signUpName);
                    SecureUserCredentials.storeUserCred(et_sign_up_name.getText().toString());
                    startActivity(new Intent(this, SafetyActivity.class));
                }
                else {
                    Toast.makeText(this, "Pls make sure the user name matches the criteria", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login:
                String loginName = userNameValidation();
                boolean isLoginAvailable = SecureUserCredentials.secureUserLogin(loginName);
                if(isLoginAvailable) {
                    Intent intent = new Intent(this, SafetyActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Pls Sign Up with a appropriate name", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String userNameValidation() {
        String name = et_sign_up_name.getText().toString();
        if(name != null && name.length()<=0) {
            return null;
        }
        return name;
    }
}
