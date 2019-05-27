package com.eshna.travelapp.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.PackageBooking;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public void onBindViewHolder(@NonNull PackageBookingAdapter.PackageBookingVH packageBookingVH, int position) {
        final PackageBooking packageBooking = packageBookings.get(position);

        //setting values here
        if (packageBooking.getaPackage().getPhoto() != null && !packageBooking.getaPackage().getPhoto().equals("")) {
            Picasso.with(mContext).load(packageBooking.getaPackage().getPhoto()).into(packageBookingVH.bookingThumbIV);
        }
        packageBookingVH.bookingNameTV.setText(packageBooking.getaPackage().getName());
        packageBookingVH.bookingDateTV.setText(String.format("Booked for: %s", packageBooking.getBookedDate()));

        packageBookingVH.cancelBookingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Alert dialog for cancel package booking confirmation
                Toast.makeText(mContext, "Test", Toast.LENGTH_SHORT).show();
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


