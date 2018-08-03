package com.example.swaroopsrinivasan.safety_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swaroopsrinivasan.safety_app.Dialog.ContactsSelector;
import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;
import com.example.swaroopsrinivasan.safety_app.utils.SafetyAppSharedPref;
import com.example.swaroopsrinivasan.safety_app.utils.SecureUserCredentials;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.et_login_name) EditText et_login_name;
    @BindView(R.id.btn_add_contacts) Button btn_add_contacts;

    ArrayAdapter contactsSelectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        btn_login.setOnClickListener(this);
        btn_add_contacts.setOnClickListener(this);
        contactsSelectorAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_login:
                String loginName = userNameValidation();
                //boolean isLoginAvailable = SecureUserCredentials.secureUserLogin(loginName);
                if(loginName != null) {
                    SafetyAppSharedPref.getInstance().setUserName(loginName);
                    Intent intent = new Intent(this, SafetyActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Pls Sign Up with a appropriate name", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_add_contacts:
                new ContactsSelector(this,contactsSelectorAdapter);
                break;
        }
    }

    private String userNameValidation() {
        String name = et_login_name.getText().toString();
        if(name != null && name.length()<=0) {
            return null;
        }
        return name;
    }
}
