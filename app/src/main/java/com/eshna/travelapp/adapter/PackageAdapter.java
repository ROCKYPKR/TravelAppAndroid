package com.eshna.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.model.Package;
import com.eshna.travelapp.utility.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageAdapter  extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private Context mContext;
    private List<Package> packages;
    private RecyclerViewClickListener mRecyclerItemClickListener;

    //adapter constructor
    public PackageAdapter(List<Package> packageList, Context context, RecyclerViewClickListener listener) {
        this.packages = packageList;
        this.mContext = context;
        this.mRecyclerItemClickListener = listener;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_travel_package, viewGroup, false);
        return new PackageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder packageViewHolder, int position) {
        final Package aPackage = packages.get(position);

        //setting values here
        if (aPackage.getPhoto() != null && !aPackage.getPhoto().equals("")) {
            Picasso.with(mContext).load(aPackage.getPhoto()).into(packageViewHolder.packageThumbIV);
        }
        packageViewHolder.packageNameTV.setText(aPackage.getName());

    }

    @Override
    public int getItemCount() {
        return (packages == null ? 0 : packages.size());
    }

    class PackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv__package)
        ImageView packageThumbIV;
        @BindView(R.id.package_name_tv)
        TextView packageNameTV;

        private PackageViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //on click for an item
            mRecyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}

