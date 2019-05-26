package com.eshna.travelapp.event;

import com.eshna.travelapp.model.HotelReview;

public class HotelReviewUpdatedEvent {
    private int mPosition;
    private final HotelReview mHotelReview;

    public HotelReviewUpdatedEvent(HotelReview hotelReview, int position) {
        mHotelReview = hotelReview;
        mPosition = position;
    }

    public HotelReview getHotelReview() {
        return mHotelReview;
    }

    public int getReviewPosition(){
        return mPosition;
    }
}
