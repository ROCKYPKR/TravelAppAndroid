package com.eshna.travelapp.event;

public class PackageReviewDeletedEvent {
    private int mPosition;

    public PackageReviewDeletedEvent(int position) {
        mPosition = position;
    }

    public int getReviewPosition(){
        return mPosition;
    }
}
