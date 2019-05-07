package com.eshna.travelapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.activity.HotelDetailActivity;
import com.eshna.travelapp.adapter.HotelAdapter;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.HotelsResponse;
import com.eshna.travelapp.model.Hotel;
import com.eshna.travelapp.utility.ItemOffsetDecoration;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelListFragment extends Fragment {

    private static final String LOG_TAG = HotelListFragment.class.getSimpleName();

    private HotelAdapter adapter;
    private List<Hotel> hotelList = new ArrayList<>();

    @BindView(R.id.rv_hotels)
    RecyclerView hotelsRV;
    @BindView(R.id.pb_hotels_loading)
    ContentLoadingProgressBar hotelsLoadingPB;
    @BindView(R.id.tv_no_hotels_found)
    TextView noHotelsFoundTV;

    public HotelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_hotel_list, container, false);
        ButterKnife.bind(this, rootView);

        showProgressBar();

        initRecyclerView();

        getHotelsFromApi();

        return  rootView;
    }

    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotelsRV.setLayoutManager(linearLayoutManager);

        hotelsRV.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.margin_padding_size_small);
        hotelsRV.addItemDecoration(itemDecoration);

        RecyclerViewClickListener recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(LOG_TAG, "onClick: "+position);
                startDetailActivity(hotelList.get(position), view);
            }
        };
        adapter = new HotelAdapter(hotelList, getActivity(), recyclerViewClickListener);
        hotelsRV.setAdapter(adapter);
    }

    private void startDetailActivity(Hotel hotel, View view) {
        Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    private void getHotelsFromApi() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<HotelsResponse> call = apiInterface.getHotels(userLocalStore.getLoggedInUser().getApiToken());

        call.enqueue(new Callback<HotelsResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelsResponse> call, @NonNull Response<HotelsResponse> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                Log.d(LOG_TAG, response.toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getHotels().length>0){
                            hotelList.addAll(Arrays.asList(response.body().getHotels()));
                            adapter.notifyDataSetChanged();
                        } else {
                            noHotelsFoundTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(LOG_TAG, "Response is unsuccessful");
                }

                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<HotelsResponse> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, " in onFailure() ");
                Log.d(LOG_TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar() {
        hotelsLoadingPB.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        hotelsLoadingPB.setVisibility(View.GONE);
    }

}
