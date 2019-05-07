package com.eshna.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.model.Hotel;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private static final String LOG_TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<Hotel> hotels;
    private RecyclerViewClickListener mRecyclerItemClickListener;

    //adapter constructor
    public HotelAdapter(List<Hotel> hotelList, Context context, RecyclerViewClickListener listener) {
        this.hotels = hotelList;
        this.mContext = context;
        this.mRecyclerItemClickListener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hotel, viewGroup, false);
        return new HotelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder hotelViewHolder, int position) {
        final Hotel hotel = hotels.get(position);

        //setting values here
        if (hotel.getPhoto() != null && !hotel.getPhoto().equals("")) {
            Picasso.with(mContext).load(ApiClient.BASE_URL +"/"+ hotel.getPhoto()).into(hotelViewHolder.hotelThumbIV);
        }
        hotelViewHolder.hotelNameTV.setText(hotel.getName());
        hotelViewHolder.hotelRatingBar.setNumStars(hotel.getOverallRating());

    }

    @Override
    public int getItemCount() {
        return (hotels == null ? 0 : hotels.size());
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_hotel_thumbnail)
        ImageView hotelThumbIV;
        @BindView(R.id.tv_hotel_name)
        TextView hotelNameTV;
        @BindView(R.id.rb_hotel)
        RatingBar hotelRatingBar;

        private HotelViewHolder(@NonNull View view) {
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
