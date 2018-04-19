package com.example.swaroopsrinivasan.safety_app.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.swaroopsrinivasan.safety_app.Model.Contact;

import java.util.ArrayList;

/**
 * Created by swaroop.srinivasan on 4/15/18.
 */

public class ContactsAdapter extends BaseAdapter{
    private ArrayList mContacts;
    private Context mContext;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        mContacts = contacts;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int i) {
        return mContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {

        }
        return null;
    }
}
