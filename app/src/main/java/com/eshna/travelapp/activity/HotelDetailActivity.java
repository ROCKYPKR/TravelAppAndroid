package com.eshna.travelapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.fragment.AboutHotelFragment;
import com.eshna.travelapp.fragment.HotelReviewFragment;
import com.eshna.travelapp.fragment.MapFragment;
import com.eshna.travelapp.model.Hotel;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = HotelDetailActivity.class.getSimpleName();

    private Hotel hotel;

    @BindView(R.id.ctl)
    CollapsingToolbarLayout ctl;
    @BindView(R.id.toolbar_hotel_detail)
    Toolbar toolbar;
    @BindView(R.id.hotel_detail_iv)
    ImageView hotelIV;
    @BindView(R.id.tab_layout_hotel_detail)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_hotel_detail)
    ViewPager viewPager;

    @BindString(R.string.about)
    String about;
    @BindString(R.string.reviews)
    String reviews;
    @BindString(R.string.map)
    String map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        /*
        Get data from preceding fragment
         */
        if (getIntent().hasExtra("hotel")) {
            hotel = (Hotel) getIntent().getSerializableExtra("hotel");
            toolbar.setTitleTextColor(Color.WHITE);
            setTitle(hotel.getName());
            if (hotel.getPhoto() != null && !hotel.getPhoto().equals("")) {
                Picasso.with(this).load(hotel.getPhoto()).into(hotelIV);
            }
        } else {
            Log.d(LOG_TAG, " Could not get hotel from intent");
        }


        /*
        Setup View Pager
         */
        ctl.setTitleEnabled(false);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), hotel);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                HotelDetailActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyAdapter extends FragmentStatePagerAdapter{

        Hotel mHotel;

        public MyAdapter(FragmentManager fragmentManager, Hotel hotel){
            super(fragmentManager);
            mHotel =  hotel;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            if (i == 0){
                fragment = new AboutHotelFragment();

                Bundle data = new Bundle();//create bundle instance
                data.putSerializable("hotel", mHotel);//put object to pass with a key value
                fragment.setArguments(data);
            }
            if (i == 1){
                fragment = new HotelReviewFragment();

                Bundle data = new Bundle();//create bundle instance
                data.putInt("hotel_id", hotel.getId());
                fragment.setArguments(data);
            }
            if (i==2){
                fragment = new MapFragment();

                Bundle data = new Bundle();
                data.putSerializable("hotel", mHotel);
                fragment.setArguments(data);

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if (position == 0){
                name = about;
            }
            if (position == 1){
                name = reviews;
            }
            if (position == 2){
                name = map;
            }
            return name;
        }
    }

}
