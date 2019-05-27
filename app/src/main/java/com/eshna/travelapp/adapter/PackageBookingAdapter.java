package com.eshna.travelapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import com.eshna.travelapp.apiResponse.MinimalResponse;
import com.eshna.travelapp.event.PackageBookingDeletedEvent;
import com.eshna.travelapp.model.PackageBooking;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.eshna.travelapp.utility.UserLocalStore;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageBookingAdapter extends RecyclerView.Adapter<PackageBookingAdapter.PackageBookingVH> {

    private static final String TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<PackageBooking> packageBookings;
    private RecyclerViewClickListener mRecyclerItemClickListener;

    //adapter constructor
    public PackageBookingAdapter(List<PackageBooking> packageBookingList, Context context, RecyclerViewClickListener listener) {
        this.packageBookings = packageBookingList;
        this.mContext = context;
        this.mRecyclerItemClickListener = listener;
    }

    @NonNull
    @Override
    public PackageBookingAdapter.PackageBookingVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_booking, viewGroup, false);
        return new PackageBookingVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageBookingAdapter.PackageBookingVH packageBookingVH, final int position) {
        final PackageBooking packageBooking = packageBookings.get(position);

        //setting values here
        if (packageBooking.getaPackage().getPhoto() != null && !packageBooking.getaPackage().getPhoto().equals("")) {
            Picasso.with(mContext).load(packageBooking.getaPackage().getPhoto()).into(packageBookingVH.bookingThumbIV);
        }
        packageBookingVH.bookingNameTV.setText(packageBooking.getaPackage().getName());
        packageBookingVH.bookingDateTV.setText(String.format("Booked for: %s", packageBooking.getBookedDate()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime bookedDateTime = dateTimeFormatter.parseDateTime(packageBooking.getBookedDate());
        DateTime currentDateTime = new DateTime();

        if ( bookedDateTime.plusDays(2).isBefore(currentDateTime)) {
            packageBookingVH.cancelBookingIV.setVisibility(View.GONE);
        }

        packageBookingVH.cancelBookingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.confirm_cancel_booking_message)
                        .setTitle(R.string.confirm_cancel_booking)
                        .setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hitDeleteBookingApi(packageBooking.getBookingId(), position);
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
        });

    }

    private void hitDeleteBookingApi(int bookingId, final int position) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MinimalResponse> call = apiInterface.deletePackageBooking(bookingId, new UserLocalStore(mContext).getLoggedInUser().getApiToken());

        call.enqueue(new Callback<MinimalResponse>() {
            @Override
            public void onResponse(@NonNull Call<MinimalResponse> call, @NonNull Response<MinimalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    if (response.body()!= null){
                        Log.d(TAG, "onResponse: "+response.body().getMessage());
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //pass an event that the review is deleted
                        EventBus.getDefault().post(new PackageBookingDeletedEvent(position));
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MinimalResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (packageBookings == null ? 0 : packageBookings.size());
    }

    public class PackageBookingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_booking_thumb)
        ImageView bookingThumbIV;
        @BindView(R.id.tv_booking_name)
        TextView bookingNameTV;
        @BindView(R.id.tv_booking_date)
        TextView bookingDateTV;
        @BindView(R.id.iv_cancel_booking)
        ImageView cancelBookingIV;

        private PackageBookingVH(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //on click for an item
            mRecyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}


