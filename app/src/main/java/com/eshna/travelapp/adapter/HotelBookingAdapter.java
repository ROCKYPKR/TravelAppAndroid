package com.eshna.travelapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
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
import com.eshna.travelapp.event.HotelBookingDeletedEvent;
import com.eshna.travelapp.event.PackageReviewDeletedEvent;
import com.eshna.travelapp.model.HotelBooking;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.eshna.travelapp.utility.UserLocalStore;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelBookingAdapter extends RecyclerView.Adapter<HotelBookingAdapter.HotelBookingVH> {

    private static final String TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<HotelBooking> hotelBookings;
    private RecyclerViewClickListener mRecyclerItemClickListener;
    private BottomSheetBehavior bottomSheetBehavior;

    //adapter constructor
    public HotelBookingAdapter(List<HotelBooking> hotelBookingList, Context context, RecyclerViewClickListener listener) {
        this.hotelBookings = hotelBookingList;
        this.mContext = context;
        this.mRecyclerItemClickListener = listener;
    }

    @NonNull
    @Override
    public HotelBookingVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_booking, viewGroup, false);
        return new HotelBookingVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelBookingVH hotelBookingVH, final int position) {
        final HotelBooking hotelBooking = hotelBookings.get(position);

        //setting values here
        if (hotelBooking.getHotel().getPhoto() != null && !hotelBooking.getHotel().getPhoto().equals("")) {
            Picasso.with(mContext).load(hotelBooking.getHotel().getPhoto()).into(hotelBookingVH.bookingThumbIV);
        }
        hotelBookingVH.bookingNameTV.setText(hotelBooking.getHotel().getName());
        hotelBookingVH.bookingDateTV.setText(String.format("Booked for: %s", hotelBooking.getBookedDate()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime bookedDateTime = dateTimeFormatter.parseDateTime(hotelBooking.getBookedDate());
        DateTime currentDateTime = new DateTime();

        if ( bookedDateTime.plusDays(2).isBefore(currentDateTime)) {
            hotelBookingVH.cancelBookingIV.setVisibility(View.GONE);
        }

        hotelBookingVH.cancelBookingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.confirm_cancel_booking_message)
                        .setTitle(R.string.confirm_cancel_booking)
                        .setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hitDeleteBookingApi(hotelBooking.getBookingId(), position);
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
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MinimalResponse> call = apiInterface.deleteHotelBooking(bookingId, new UserLocalStore(mContext).getLoggedInUser().getApiToken());

        call.enqueue(new Callback<MinimalResponse>() {
            @Override
            public void onResponse(Call<MinimalResponse> call, Response<MinimalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    if (response.body()!= null){
                        Log.d(TAG, "onResponse: "+response.body().getMessage());
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //pass an event that the review is deleted
                        EventBus.getDefault().post(new HotelBookingDeletedEvent(position));
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MinimalResponse> call, Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (hotelBookings == null ? 0 : hotelBookings.size());
    }

    public class HotelBookingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_booking_thumb)
        ImageView bookingThumbIV;
        @BindView(R.id.tv_booking_name)
        TextView bookingNameTV;
        @BindView(R.id.tv_booking_date)
        TextView bookingDateTV;
        @BindView(R.id.iv_cancel_booking)
        ImageView cancelBookingIV;


        private HotelBookingVH(@NonNull View view) {
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

