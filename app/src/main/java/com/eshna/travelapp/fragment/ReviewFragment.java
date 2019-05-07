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
import com.eshna.travelapp.adapter.ReviewAdapter;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.ReviewResponse;
import com.eshna.travelapp.model.Review;
import com.eshna.travelapp.utility.ItemOffsetDecoration;
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
 * Works for both Packages and Hotels
 */
public class ReviewFragment extends Fragment {

    private static final String TAG = ReviewFragment.class.getSimpleName();

    @BindView(R.id.rv_reviews)
    RecyclerView reviewRV;
    @BindView(R.id.pb_reviews_loading)
    ProgressBar progressBar;
    @BindView(R.id.tv_no_reviews_found)
    TextView noReviewsTV;

    private ReviewAdapter adapter;
    private List<Review> reviewList;
    private int currentHotelId;
    private int currentPackageId;

    private boolean isForHotel, isForPackage;


    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            if (getArguments().containsKey("hotel_id")){
                currentHotelId = getArguments().getInt("hotel_id");
                isForHotel = true;
            }

            if (getArguments().containsKey("package_id")){
                currentPackageId = getArguments().getInt("package_id");
                isForPackage = true;
            }

        } else {
            Log.e(TAG, "onCreate: getArguments returned null");
        }

        reviewList = new ArrayList<>();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, rootView);

        showProgressbar();
        initRecyclerView();

        if (isForHotel){
            getReviewsForCurrentHotel();
        }

        if (isForPackage){
            getReviewsForCurrentPackage();
        }

        return rootView;
    }

    private void getReviewsForCurrentPackage() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<ReviewResponse> call = apiInterface.getReviewsForAPackage(currentPackageId, userLocalStore.getLoggedInUser().getApiToken());

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body() != null) {
                        if (response.body().getReviews().length > 0) {
                            reviewList.addAll(Arrays.asList(response.body().getReviews()));
                            adapter.notifyDataSetChanged();
                        } else {
                            noReviewsTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReviewsForCurrentHotel() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<ReviewResponse> call = apiInterface.getReviewsForAHotel(currentHotelId, userLocalStore.getLoggedInUser().getApiToken());

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call,@NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body() != null) {
                        if (response.body().getReviews().length > 0) {
                            reviewList.addAll(Arrays.asList(response.body().getReviews()));
                            adapter.notifyDataSetChanged();
                        } else {
                            noReviewsTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reviewRV.setLayoutManager(linearLayoutManager);

        reviewRV.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.margin_padding_size_small);
        reviewRV.addItemDecoration(itemDecoration);

        adapter = new ReviewAdapter(reviewList, getActivity());
        reviewRV.setAdapter(adapter);
    }

    private void showProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

}
