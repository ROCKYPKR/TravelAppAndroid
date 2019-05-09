package com.eshna.travelapp.activity;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.fragment.AboutPackageFragment;
import com.eshna.travelapp.fragment.PackageReviewFragment;
import com.eshna.travelapp.model.Package;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = HotelDetailActivity.class.getSimpleName();

    private Package aPackage;

    @BindView(R.id.ctl_package_detail)
    CollapsingToolbarLayout ctl;
    @BindView(R.id.toolbar_package_detail)
    Toolbar toolbar;
    @BindView(R.id.package_detail_iv)
    ImageView packageIV;
    @BindView(R.id.tab_layout_package_detail)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_package_detail)
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
        setContentView(R.layout.activity_package_detail);

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
        if (getIntent().hasExtra("package")) {
            //set to current object
            aPackage = (Package) getIntent().getSerializableExtra("package");

            toolbar.setTitleTextColor(Color.WHITE);
            setTitle(aPackage.getName());
            if (aPackage.getPhoto() != null && !aPackage.getPhoto().equals("")) {
                Picasso.with(this).load(aPackage.getPhoto()).into(packageIV);
            }
        } else {
            Log.d(LOG_TAG, " Could not get package from intent");
        }


        /*
        Setup View Pager
         */
        ctl.setTitleEnabled(false);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), aPackage);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PackageDetailActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        Package aPackage;

        public MyAdapter(FragmentManager fragmentManager, Package ppackage){
            super(fragmentManager);
            aPackage =  ppackage;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            if (i == 0){
                fragment = new AboutPackageFragment();

                Bundle data = new Bundle();//create bundle instance
                data.putSerializable("package", aPackage);//put object to pass with a key value
                fragment.setArguments(data);
            }
            if (i == 1){
                fragment = new PackageReviewFragment();

                Bundle data = new Bundle();//create bundle instance
                data.putInt("package_id", aPackage.getId());
                fragment.setArguments(data);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
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
            return name;
        }
    }
}
