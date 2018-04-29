package com.example.swaroopsrinivasan.safety_app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swaroopsrinivasan.safety_app.R;

/**
 * Created by swaroop.srinivasan on 4/20/18.
 */

public class LiveTrackingFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_live_tracking,container);

        return parentView;
    }
}
