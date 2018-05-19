package com.example.swaroopsrinivasan.safety_app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.swaroopsrinivasan.safety_app.Dialog.ContactsSelector;
import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.Service.TrackerService;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SafetyActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btn_select_contacts)Button btnSelectContacts;
    @BindView(R.id.btn_share_location)Button btnShareLocation;
    ArrayAdapter<String> arrayAdapter;

    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        ButterKnife.bind(this);
        btnSelectContacts.setOnClickListener(this);
        btnShareLocation.setOnClickListener(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_contacts:
                new ContactsSelector(this, arrayAdapter);
                break;
            case R.id.btn_share_location:
                checkPermissionStatus();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            StringBuilder sb = new StringBuilder();
            Uri contactData = data.getData();
            Cursor c = getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                sb.append(name+" : ");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermissionStatus() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(String permission:permissions) {
                int permissionCheck = PermissionChecker.checkSelfPermission(this, permission);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 0);
                    return false;
                }
            }
        }
        startService(new Intent(this, TrackerService.class));
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults){
        if(requestCode == 0 && grantResults.length >= 1) {
            for(int i=0;i<grantResults.length;i++) {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //Show dialog asking for permission...
                    requestPermissions(permissions, 0);
                }
            }
            startService(new Intent(this, TrackerService.class));
        }
    }
}
