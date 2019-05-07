package com.eshna.travelapp.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.eshna.travelapp.R;
import com.eshna.travelapp.adapter.SmartFragmentStatePagerAdapter;
import com.eshna.travelapp.fragment.AboutFragment;
import com.eshna.travelapp.fragment.ReviewFragment;
import com.eshna.travelapp.model.Hotel;
import com.eshna.travelapp.utility.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String LOG_TAG = HotelDetailActivity.class.getSimpleName();

    Hotel hotel;

    @BindView(R.id.toolbar_hotel_detail)
    Toolbar toolbar;
    @BindView(R.id.tab_layout_hotel_detail)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_hotel_detail)
    CustomViewPager viewPager;

    @BindString(R.string.about)
    String about;
    @BindString(R.string.reviews)
    String reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
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
            setTitle(hotel.getName());
            if (hotel.getPhoto() != null && !hotel.getPhoto().equals("")) {
//                Picasso.with(this).load(hotel.getPhoto()).into(hotelIV);
            }
        } else {
            Log.d(LOG_TAG, " Could not get hotel from intent");
        }


        /*
        Setup View Pager
         */
        viewPager.setPagingEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        //Create fragments here.
        AboutFragment aboutFragment = new AboutFragment();
        ReviewFragment reviewFragment = new ReviewFragment();
        pagerAdapter.addFragments(aboutFragment); //0
        pagerAdapter.addFragments(reviewFragment); //1
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
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

    /*
    ViewPager implementation callbacks, below: 3
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    // adapter for the viewpager
    private class CustomPagerAdapter extends SmartFragmentStatePagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private String tabTitlesAboutUs[] = new String[]{about, reviews};

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Our custom method that populates this Adapter with Fragments
        public void addFragments(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitlesAboutUs[position];
        }
    }
}
