package com.eshna.travelapp.event;

public class HotelReviewDeletedEvent {
    private int mPosition;

    public HotelReviewDeletedEvent(int position) {
        mPosition = position;
    }

    public int getReviewPosition(){
        return mPosition;
    }
}
