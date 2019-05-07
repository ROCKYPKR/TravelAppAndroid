package com.eshna.travelapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.eshna.travelapp.activity.PackageDetailActivity;
import com.eshna.travelapp.adapter.PackageAdapter;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.PackagesResponse;
import com.eshna.travelapp.model.Package;
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
public class PackageListFragment extends Fragment {

    private static final String LOG_TAG = PackageListFragment.class.getSimpleName();

    @BindView(R.id.rv_package_list)
    RecyclerView packagesRV;
    @BindView(R.id.pb_packages_loading)
    ContentLoadingProgressBar packagesLoadingPB;
    @BindView(R.id.tv_no_packages_found)
    TextView noPackagesTV;

    private List<Package> packageList;
    private PackageAdapter packageAdapter;



    public PackageListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_packaga_list, container, false);
        ButterKnife.bind(this, rootView);

        showProgressBar();

        initRecyclerView();

        getPackagesFromApi();

        return rootView;
    }

    private void getPackagesFromApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<PackagesResponse> call = apiInterface.getPackages(userLocalStore.getLoggedInUser().getApiToken());

        call.enqueue(new Callback<PackagesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PackagesResponse> call, @NonNull Response<PackagesResponse> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                Log.d(LOG_TAG, response.toString());

                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getPackages().length>0){
                            packageList.addAll(Arrays.asList(response.body().getPackages()));
                            packageAdapter.notifyDataSetChanged();
                        } else {
                            noPackagesTV.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.e(LOG_TAG, "onResponse: Response is not successful");
                }

                hideProgressBar();
            }

            @Override
            public void onFailure(@NonNull Call<PackagesResponse> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, " in onFailure() ");
                Log.d(LOG_TAG, t.getLocalizedMessage());
                t.printStackTrace();
                hideProgressBar();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        packageList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        packagesRV.setLayoutManager(linearLayoutManager);

        packagesRV.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.margin_padding_size_small);
        packagesRV.addItemDecoration(itemDecoration);

        RecyclerViewClickListener recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(NoticeListActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                startDetailActivity(packageList.get(position), view);
            }
        };

        packageAdapter = new PackageAdapter(packageList, getActivity(), recyclerViewClickListener);
        packagesRV.setAdapter(packageAdapter);
    }

    private void startDetailActivity(Package aPackage, View view) {
        Intent intent = new Intent(getActivity(), PackageDetailActivity.class);
        intent.putExtra("package", aPackage);
        startActivity(intent);
    }


    private void showProgressBar(){
        packagesLoadingPB.setVisibility(View.VISIBLE);
    }


    private void hideProgressBar(){
        packagesLoadingPB.setVisibility(View.GONE);
    }
}
