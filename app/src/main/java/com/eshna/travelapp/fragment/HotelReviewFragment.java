package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.adapter.HotelReviewAdapter;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.HotelReviewResponse;
import com.eshna.travelapp.apiResponse.HotelReviewsResponse;
import com.eshna.travelapp.model.HotelReview;
import com.eshna.travelapp.utility.ItemOffsetDecoration;
import com.eshna.travelapp.utility.UserLocalStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelReviewFragment extends Fragment{

    private static final String TAG = HotelReviewFragment.class.getSimpleName();

    @BindView(R.id.rv_hotel_reviews)
    RecyclerView reviewRV;
    @BindView(R.id.pb_hotel_reviews_loading)
    ProgressBar progressBar;
    @BindView(R.id.tv_no_hotel_reviews_found)
    TextView noReviewsTV;
    @BindView(R.id.fab_write_hotel_review)
    FloatingActionButton floatingActionButton;

    private HotelReviewAdapter adapter;
    private List<HotelReview> reviewList;
    private int currentHotelId;
    private String apiToken;

    Unbinder unbinder;

    public HotelReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            if (getArguments().containsKey("hotel_id")){
                currentHotelId = getArguments().getInt("hotel_id");
            }
        } else {
            Log.e(TAG, "onCreate: getArguments returned null");
        }

        reviewList = new ArrayList<>();

        UserLocalStore userLocalStore = new UserLocalStore(getContext());
        apiToken = userLocalStore.getLoggedInUser().getApiToken();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hotel_review, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        showProgressbar();
        initRecyclerView();

        getReviewsForCurrentHotel();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.fab_write_hotel_review)
    public void presentReviewDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_write_review, null);

        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        final EditText userInput =  promptView.findViewById(R.id.et_write_review);
        final RatingBar ratingBar = promptView.findViewById(R.id.rb_write_review);

        TextView tvDone =  promptView.findViewById(R.id.tv_done_write_review);
        TextView tvCancel = promptView.findViewById(R.id.tv_cancel_write_review);

        tvDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: HIT post review api
                if (userInput.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please provide review", Toast.LENGTH_SHORT).show();
                } else {
                    String reviewText = userInput.getText().toString().trim();
                    float ratingValue = ratingBar.getRating();
//                    Log.d(TAG, "onClick: "+reviewText+" "+ratingValue);

                hitPostReviewAPI(ratingValue, reviewText); //this is done asynchronously
                alertD.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);

        alertD.show();

    }

    private void hitPostReviewAPI(float ratingValue, String reviewText) {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("hotel_id", String.valueOf(currentHotelId));
        requestBody.put("rating", String.valueOf(ratingValue));
        requestBody.put("review", reviewText);

        Call<HotelReviewResponse> call = apiInterface.postHotelReview(apiToken, requestBody);

        call.enqueue(new Callback<HotelReviewResponse>() {
            @Override
            public void onResponse(Call<HotelReviewResponse> call, Response<HotelReviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body() != null) {
                        if (!response.body().getError()){
                            reviewList.add( response.body().getReview());
                            adapter.notifyDataSetChanged();
                            noReviewsTV.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Review Posted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "onResponse: "+response.body().getCode());
                        }
                    } else {
                        Log.e(TAG, "onResponse: Response body is null");
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is unsuccessful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelReviewResponse> call,@NonNull Throwable t) {
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

        Call<HotelReviewsResponse> call = apiInterface.getReviewsForAHotel(currentHotelId, apiToken);

        call.enqueue(new Callback<HotelReviewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelReviewsResponse> call, @NonNull Response<HotelReviewsResponse> response) {
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
            public void onFailure(@NonNull Call<HotelReviewsResponse> call, @NonNull Throwable t) {
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

        adapter = new HotelReviewAdapter(reviewList, getActivity());
        reviewRV.setAdapter(adapter);
    }

    private void showProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}