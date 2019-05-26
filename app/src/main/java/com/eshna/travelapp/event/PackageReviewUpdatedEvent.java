package com.eshna.travelapp.event;

import com.eshna.travelapp.model.PackageReview;

public class PackageReviewUpdatedEvent {
    private int mPosition;
    private final PackageReview mPackageReview;

    public PackageReviewUpdatedEvent(PackageReview packageReview, int position) {
        mPackageReview = packageReview;
        mPosition = position;
    }

    public PackageReview getmPackageReview() {
        return mPackageReview;
    }

    public int getReviewPosition(){
        return mPosition;
    }
}
