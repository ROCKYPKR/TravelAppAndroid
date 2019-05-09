package com.eshna.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.HotelBooking;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelBookingAdapter extends RecyclerView.Adapter<HotelBookingAdapter.HotelBookingVH> {

    private static final String LOG_TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<HotelBooking> hotelBookings;
    private RecyclerViewClickListener mRecyclerItemClickListener;

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
    public void onBindViewHolder(@NonNull HotelBookingVH hotelBookingVH, int position) {
        final HotelBooking hotelBooking = hotelBookings.get(position);

        //setting values here
        if (hotelBooking.getHotel().getPhoto() != null && !hotelBooking.getHotel().getPhoto().equals("")) {
            Picasso.with(mContext).load(hotelBooking.getHotel().getPhoto()).into(hotelBookingVH.bookingThumbIV);
        }
        hotelBookingVH.bookingNameTV.setText(hotelBooking.getHotel().getName());
        hotelBookingVH.bookingDateTV.setText(hotelBooking.getBookedDate());

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

