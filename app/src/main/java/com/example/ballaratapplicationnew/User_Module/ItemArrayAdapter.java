package com.example.ballaratapplicationnew.User_Module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.example.ballaratapplicationnew.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ItemArrayAdapter extends RecyclerView.Adapter<FlowerViewHolder> {

 Context mContext;
private List<PriceList_Pojo> mFlowerList;

    ItemArrayAdapter(Context mContext, List<PriceList_Pojo> mFlowerList) {
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
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {


                if( mFlowerList.get(position).getPkg_price().equals("0.00"))
                {


                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"tariq.riaz@eitsec.co.uk"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Ballarat Application");
                    i.putExtra(Intent.EXTRA_TEXT   , "Enter Any Text");
                    try {
                        mContext.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }

                else
                {
                    Intent intent = new Intent(mContext , Prices_Activity.class);
                    Log.d("User_Name" , ""+mFlowerList.get(position).getPkg_name());
                    Log.d("User_Image" , ""+mFlowerList.get(position).getPkg_image());
                    intent.putExtra("User_Name" ,mFlowerList.get(position).getPkg_name());
                    intent.putExtra("User_Image" ,mFlowerList.get(position).getPkg_image());
                    intent.putExtra("Package_Id1" ,mFlowerList.get(position).getId());
                    intent.putExtra("User_price" ,mFlowerList.get(position).getPkg_price());
                    mContext.startActivity(intent);
                }

            }
        });
    }

@Override
public int getItemCount() {
        return mFlowerList.size();
        }
}

class FlowerViewHolder extends RecyclerView.ViewHolder {


    TextView price_name,item_price;
    CircularImageView profile_image;
    RelativeLayout clicked;


    FlowerViewHolder(View itemView) {
        super(itemView);

        price_name = itemView.findViewById(R.id.row_item);
        item_price = itemView.findViewById(R.id.item_price);
        clicked= itemView.findViewById(R.id.clicked);
        profile_image = itemView.findViewById(R.id.profile_image);



    }
}