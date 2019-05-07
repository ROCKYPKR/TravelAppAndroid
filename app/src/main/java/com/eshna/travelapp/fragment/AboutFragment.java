package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.Hotel;
import com.eshna.travelapp.model.Package;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Works for both Packages and Hotels
 */
public class AboutFragment extends Fragment {
    private static final String TAG = AboutFragment.class.getSimpleName();

    @BindView(R.id.tv_hotel_detail_price)
    TextView priceTV;
    @BindView(R.id.tv_hotel_detail_contact)
    TextView contactTV;
    @BindView(R.id.tv_hotel_detail_desc)
    TextView descTV;

    private Hotel mHotel;
    private Package mPackage;

    private boolean isForHotel, isForPackage;



    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            if (getArguments().getSerializable("hotel")!=null){
                mHotel = (Hotel) getArguments().getSerializable("hotel");//Get pass data with its key value
                isForHotel = true;
            }
            if (getArguments().getSerializable("package")!=null){
                isForPackage = true;
                mPackage = (Package) getArguments().getSerializable("package");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);

        if (isForHotel){
            setValuesForHotel();
        }
        if (isForPackage){
            setValuesForPackage();
        }

        return rootView;
    }

    private void setValuesForPackage() {
        priceTV.setText(String.format("Price: %s", mPackage.getPrice()));
        contactTV.setText(String.format("Provided by: %s", mPackage.getProvider()));
        descTV.setText(mPackage.getDescription());
    }

    private void setValuesForHotel() {
        priceTV.setText(String.format("%s/ Night", mHotel.getPrice()));
        contactTV.setText(mHotel.getContact());
        descTV.setText(mHotel.getDescription());
    }

}
