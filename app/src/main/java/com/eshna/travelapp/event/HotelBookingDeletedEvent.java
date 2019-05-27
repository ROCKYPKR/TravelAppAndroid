package com.eshna.travelapp.event;

public class HotelBookingDeletedEvent {
    private int mPosition;

    public HotelBookingDeletedEvent(int position) {
        mPosition = position;
    }

    public int getHotelBookignPosition(){
        return mPosition;
    }
}
