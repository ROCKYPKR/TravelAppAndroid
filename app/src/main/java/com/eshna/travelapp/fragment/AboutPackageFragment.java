package com.eshna.travelapp.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.BookPackageResponse;
import com.eshna.travelapp.model.Package;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutPackageFragment extends Fragment{

    private static final String TAG = AboutPackageFragment.class.getSimpleName();

    @BindView(R.id.tv_package_price)
    TextView priceTV;
    @BindView(R.id.tv_package_provider)
    TextView providerTV;
    @BindView(R.id.tv_package_description)
    TextView descTV;


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

    @OnClick(R.id.fab_book_package)
    public void askBookConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Book "+mPackage.getName()+"? Please select a date after selecting Yes.")
                .setTitle(R.string.confirm_booking)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                String dateSelected = String.valueOf(year)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth);
                                hitBookPackageApi(dateSelected);
                            }
                        }, 2012, 10, 10); //for initializing DPD only
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000); //one day from today

                        datePickerDialog.show();
                    }})
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hitBookPackageApi(String bookDate) {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> packageInfo = new HashMap<>();
        packageInfo.put("tour_package_id", String.valueOf(mPackage.getId()));
        packageInfo.put("status", "0");
        packageInfo.put("booked_for", bookDate);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<BookPackageResponse> call = apiInterface.bookPackage(userLocalStore.getLoggedInUser().getApiToken(), packageInfo);

        call.enqueue(new Callback<BookPackageResponse>() {
            @Override
            public void onResponse(Call<BookPackageResponse> call, Response<BookPackageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body()!=null){
                        if (!response.body().isError()){
                            Toast.makeText(getContext(), getResources().getString(R.string.booked), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.booking_error), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: "+response.body().getCode());
                        }
                    }
                } else {
                    Log.e(TAG, "Response is unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<BookPackageResponse> call, Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
