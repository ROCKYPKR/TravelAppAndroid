package com.eshna.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;

import com.eshna.travelapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReviewDialogFragment extends DialogFragment {

    @BindView(R.id.rb_review)
    RatingBar ratingBar;
    @BindView(R.id.et_review)
    EditText reviewET;

    Unbinder unbinder;

    public WriteReviewDialogFragment() {
        // Required empty public constructor
    }

    public static WriteReviewDialogFragment newInstance(String title) {

        WriteReviewDialogFragment frag = new WriteReviewDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_write_review_dialog, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        String title = getArguments().getString("title", "Write review");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field
        reviewET.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
