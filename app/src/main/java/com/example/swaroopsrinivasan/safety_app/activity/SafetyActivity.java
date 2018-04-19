package com.example.swaroopsrinivasan.safety_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.swaroopsrinivasan.safety_app.Dialog.ContactsSelector;
import com.example.swaroopsrinivasan.safety_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SafetyActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btn_select_contacts)Button btnSelectContacts;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        ButterKnife.bind(this);
        btnSelectContacts.setOnClickListener(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_contacts:
                new ContactsSelector(this, arrayAdapter);
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
}
