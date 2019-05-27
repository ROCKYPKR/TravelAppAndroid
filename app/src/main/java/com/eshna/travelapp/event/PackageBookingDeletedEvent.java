package com.eshna.travelapp.event;

public class PackageBookingDeletedEvent {
    private int mPosition;

    public PackageBookingDeletedEvent(int position) {
        mPosition = position;
    }

    public int getPosition(){
        return mPosition;
    }
}
