package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ballaratapplicationnew.User_Module.Prices_Activity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.ballaratapplicationnew.R;

import androidx.recyclerview.widget.RecyclerView;

public class Package_Adapter extends RecyclerView.Adapter<FlowerViewHolder> {

    Context mContext;
    private List<PackageList_Pojo> mFlowerList;

    public Package_Adapter(Context mContext, List<PackageList_Pojo> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, final int position) {


        holder.price_name.setText(mFlowerList.get(position).getPkg_name());
        holder.item_price.setText(mFlowerList.get(position).getPkg_price());
        Picasso.get().load(mFlowerList.get(position).getPkg_image()).noFade().into(holder.profile_image);

//    Glide.with(mContext).load(mFlowerList.get(position).getPkg_image()).into(holder.profile_image);
        holder.clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class FlowerViewHolder extends RecyclerView.ViewHolder {


    TextView price_name, item_price;
    CircularImageView profile_image;
    RelativeLayout clicked;


    FlowerViewHolder(View itemView) {
        super(itemView);

        price_name = itemView.findViewById(R.id.row_item);
        item_price = itemView.findViewById(R.id.item_price);
        clicked = itemView.findViewById(R.id.clicked);
        profile_image = itemView.findViewById(R.id.profile_image);


    }
}
