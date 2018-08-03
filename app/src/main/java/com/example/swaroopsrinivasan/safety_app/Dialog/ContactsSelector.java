package com.example.swaroopsrinivasan.safety_app.Dialog;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.swaroopsrinivasan.safety_app.Model.Contact;
import com.example.swaroopsrinivasan.safety_app.utils.SessionHandler;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by swaroop.srinivasan on 4/1/18.
 */

public class ContactsSelector extends AlertDialog implements DialogInterface.OnMultiChoiceClickListener{
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ArrayAdapter<String> adapter;
    private Context mContext;
    private SessionHandler mSessionhandler;

    private final static int NUMBER_OF_SAFETY_CONTACTS = 4;
    private int numberOfContactsAdded=0;
    private List<Contact> mSafetyContacts;
    protected ContactsSelector(Context context) {
        super(context);
    }

    public ContactsSelector(Context context, ArrayAdapter<String> adapter) {
        super(context);
        mContext = context;
        this.adapter = adapter;
        builder = new AlertDialog.Builder(context);
        mSessionhandler = SessionHandler.getInstance();
        mSafetyContacts = new ArrayList<>();
        getContactList();
    }
    public void showDialog() {
        builder.setMultiChoiceItems(contactsName, null, this);
        alertDialog = builder.create();
        alertDialog.setTitle("Select "+NUMBER_OF_SAFETY_CONTACTS+" Contacts");
        alertDialog.show();
    }




    String[] contactsName = null;
    List<Contact> contacts;

    private void getContactList() {
        ContentResolver cr = mContext.getContentResolver();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER ,ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection, null, null, null);
        contactsName = new String[cursor.getCount()];
        contacts = new ArrayList<>();
        int i=0;
        if(cursor.moveToFirst())
        {
            do
            {
                Contact contact = new Contact();

                contact.contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                try {
                    contact.contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(contact.contactNumber.length() > 10) {
                        adapter.add(contact.contactName);
                        contactsName[i++] = contact.contactName;
                        contacts.add(contact);
                    }
                }
                catch (Exception e) {
                    continue;
                }
            } while (cursor.moveToNext());
        }
        showDialog();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
        if(numberOfContactsAdded < NUMBER_OF_SAFETY_CONTACTS) {
            numberOfContactsAdded++;
            mSafetyContacts.add(contacts.get(i));
            alertDialog.setTitle("Select "+(NUMBER_OF_SAFETY_CONTACTS-numberOfContactsAdded)+" Contacts");
        }
        else {
            mSessionhandler.setmSafetyContacts(mSafetyContacts);
            dialogInterface.dismiss();
        }
    }
}

class FetchContactsList extends AsyncTask<Void, Void, Void> {


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
