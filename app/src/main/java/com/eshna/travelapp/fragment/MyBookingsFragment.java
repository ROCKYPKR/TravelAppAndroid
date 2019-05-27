package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.adapter.HotelBookingAdapter;
import com.eshna.travelapp.adapter.PackageBookingAdapter;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.HotelBookingsResponse;
import com.eshna.travelapp.apiResponse.PackageBookingsResponse;
import com.eshna.travelapp.event.HotelBookingDeletedEvent;
import com.eshna.travelapp.model.HotelBooking;
import com.eshna.travelapp.model.PackageBooking;
import com.eshna.travelapp.utility.ItemOffsetDecoration;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.eshna.travelapp.utility.UserLocalStore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends Fragment {

    private static final String TAG = MyBookingsFragment.class.getSimpleName();


    @BindView(R.id.pb_bookings_loading)
    ProgressBar progressBar;
    @BindView(R.id.tv_title_hotel_booking)
    TextView hotelBookingTitleTV;
    @BindView(R.id.rv_hotel_bookings)
    RecyclerView hotelBookingRV;
    @BindView(R.id.tv_title_package_booking)
    TextView packageBookingTitleTV;
    @BindView(R.id.rv_package_bookings)
    RecyclerView packageBookingRV;
    @BindView(R.id.tv_no_hotel_bookings)
    TextView noHotelBookingsTV;
    @BindView(R.id.tv_no_package_bookings)
    TextView noPackageBookingsTV;

    private Unbinder unbinder;
    private String apiToken;

    private HotelBookingAdapter hotelBookingAdapter;
    private List<HotelBooking> hotelBookingList;
    private PackageBookingAdapter packageBookingAdapter;
    private List<PackageBooking> packageBookingList;


    public MyBookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());
        apiToken = userLocalStore.getLoggedInUser().getApiToken();
        Log.d(TAG, "onCreate: apiToken is: "+ apiToken);

        hotelBookingList = new ArrayList<>();
        packageBookingList = new ArrayList<>();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_bookings, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initRecyclerViews();

        showProgressbar();

        getHotelBookingsFromApi();
        getPackageBookingsFromApi();

        return  rootView;
    }

    private void initRecyclerViews() {
        /*
        For Hotel Booking RV
         */
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotelBookingRV.setLayoutManager(linearLayoutManager);

        hotelBookingRV.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.margin_padding_size_small);
        hotelBookingRV.addItemDecoration(itemDecoration);

        RecyclerViewClickListener recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick: "+position);
                //TODO: Some future feature
            }
        };
        hotelBookingAdapter = new HotelBookingAdapter(hotelBookingList, getActivity(), recyclerViewClickListener);
        hotelBookingRV.setAdapter(hotelBookingAdapter);


         /*
        For Package Booking RV
         */
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        packageBookingRV.setLayoutManager(linearLayoutManager2);

        packageBookingRV.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration2 = new ItemOffsetDecoration(getContext(), R.dimen.margin_padding_size_small);
        hotelBookingRV.addItemDecoration(itemDecoration2);

        RecyclerViewClickListener recyclerViewClickListener2 = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick: "+position);
                //TODO: SOme future feature
            }
        };
        packageBookingAdapter = new PackageBookingAdapter(packageBookingList, getActivity(), recyclerViewClickListener2);
        packageBookingRV.setAdapter(packageBookingAdapter);

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void getPackageBookingsFromApi() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<PackageBookingsResponse> call = apiInterface.getPackageBookings(apiToken);

        call.enqueue(new Callback<PackageBookingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PackageBookingsResponse> call,@NonNull Response<PackageBookingsResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getPackageBookings().length>0){
                            packageBookingList.addAll(Arrays.asList(response.body().getPackageBookings()));
                            packageBookingAdapter.notifyDataSetChanged();
                        } else {
                            noPackageBookingsTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(TAG, "Get hotel booking Response is unsuccessful");
                }

                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<PackageBookingsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() getting package bookings");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getHotelBookingsFromApi() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<HotelBookingsResponse> call = apiInterface.getHotelBookings(apiToken);

        call.enqueue(new Callback<HotelBookingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelBookingsResponse> call, @NonNull Response<HotelBookingsResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getHotelBookings().length>0){
                            hotelBookingList.addAll(Arrays.asList(response.body().getHotelBookings()));
                            hotelBookingAdapter.notifyDataSetChanged();
                        } else {
                            noHotelBookingsTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(TAG, "Get hotel booking Response is unsuccessful");
                }

                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<HotelBookingsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() getting hotel bookings");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHotelBookingDeletedEvent(HotelBookingDeletedEvent hotelBookingDeletedEvent){
        //fired when an event is posted

        hotelBookingList.remove(hotelBookingDeletedEvent.getHotelBookignPosition());
        hotelBookingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
