package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.model.Hotel;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutHotelFragment extends Fragment {
    private static final String TAG = AboutHotelFragment.class.getSimpleName();

    @BindView(R.id.tv_hotel_detail_price)
    TextView priceTV;
    @BindView(R.id.tv_hotel_detail_contact)
    TextView contactTV;
    @BindView(R.id.tv_hotel_detail_desc)
    TextView descTV;
    @BindView(R.id.fab_book_hotel)
    FloatingActionButton bookFAB;

    private Unbinder unbinder;
    private Hotel mHotel;



    public AboutHotelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            if (getArguments().getSerializable("hotel")!=null){
                mHotel = (Hotel) getArguments().getSerializable("hotel");//Get pass data with its key value
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_hotel, container, false);
        unbinder = ButterKnife.bind(this, rootView);


            setValuesForHotel();


        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.fab_book_hotel)
    public void askBookConfirm(){
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
    }

    private void setValuesForHotel() {
        priceTV.setText(String.format("%s/ Night", mHotel.getPrice()));
        contactTV.setText(mHotel.getContact());
        descTV.setText(mHotel.getDescription());
    }

    private void askConfirmation() {

//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage(R.string.confirm_book_this)
//                .setTitle(R.string.book)
//                .setCancelable(true)
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (isForHotel){
//                            hitBookHotelApi();
//                        }
//
//                        if (isForPackage){
//                            hitBookPackageApi();
//                        }
//                    }})
//                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();

        Toast.makeText(getContext(), "Hello book hotel/package", Toast.LENGTH_SHORT).show();
    }

    private void hitBookPackageApi() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> hotelInfo = new HashMap<>();
        hotelInfo.put("hotel_id", String.valueOf(mHotel.getId()));
        hotelInfo.put("status", "0");
        hotelInfo.put("boooked_for", "2019-05-09");//got to get a date here

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

    }

    private void hitBookHotelApi() {

    }

}
