package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshna.travelapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends Fragment {


    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        return  rootView;
    }

}
