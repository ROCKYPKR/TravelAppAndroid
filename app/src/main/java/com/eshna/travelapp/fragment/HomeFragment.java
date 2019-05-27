package com.eshna.travelapp.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.PlacesResponse;
import com.eshna.travelapp.model.Place;
import com.eshna.travelapp.utility.UserLocalStore;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

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
public class HomeFragment extends Fragment{

    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    @BindView(R.id.iv_place_photo)
    ImageView placeIV;
    @BindView(R.id.tv_place_name)
    TextView placeTV;
    @BindView(R.id.tv_place_description)
    TextView placeDescTV;

    private BottomSheetBehavior bottomSheetBehavior;
    private Unbinder unbinder;
    private MapView mapView;
    private GoogleMap mMap;

    private List<Place> placeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeList = new ArrayList<>();

        getListOfPlacesFromApi();
    }

    private void getListOfPlacesFromApi() {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        UserLocalStore userLocalStore = new UserLocalStore(getContext());

        Call<PlacesResponse> call = apiInterface.getPlaces(userLocalStore.getLoggedInUser().getApiToken());

        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesResponse> call,@NonNull Response<PlacesResponse> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, response.toString());

                if (response.isSuccessful()){
                    if (response.body()!=null){
                        placeList.addAll(Arrays.asList(response.body().getPlaces()));

                        initializeMapWithPlaces();

                    } else {
                        Log.e(TAG, "onResponse: Response body is null" );
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeMapWithPlaces() {
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //display selected place's information on a bottom sheet
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

                        Place selectedPlace = (Place) marker.getTag();

                        Picasso.with(getContext()).load(selectedPlace.getPhoto()).into(placeIV);
                        placeTV.setText(selectedPlace.getName());
                        placeDescTV.setText(selectedPlace.getDescription());
                        return false;
                    }
                });

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(27.7089559,85.2910274))
                        .zoom(5)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 6000, null);

                //add marker for all the places
                for (Place place: placeList) {

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(place.getLatitude(),place.getLongitude()))
                            .title(place.getName()));
                    marker.setTag(place);
//
//                    mMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(place.getLatitude(),place.getLongitude()))
//                            .title(place.getName()));
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);//Important to add
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
