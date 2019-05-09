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

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.Package;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutPackageFragment extends Fragment {

    private static final String TAG = AboutPackageFragment.class.getSimpleName();

    @BindView(R.id.tv_package_price)
    TextView priceTV;
    @BindView(R.id.tv_package_provider)
    TextView providerTV;
    @BindView(R.id.tv_package_description)
    TextView descTV;
    @BindView(R.id.fab_book_package)
    FloatingActionButton bookFAB;

    private Unbinder unbinder;
    private Package mPackage;


    public AboutPackageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getSerializable("package") != null) {
                mPackage = (Package) getArguments().getSerializable("package");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_package, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        setValuesForPackage();

        return rootView;
    }

    private void setValuesForPackage() {
        priceTV.setText(String.format("Price: %s", mPackage.getPrice()));
        providerTV.setText(String.format("Provided by: %s", mPackage.getProvider()));
        descTV.setText(mPackage.getDescription());
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
