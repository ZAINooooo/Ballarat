package com.example.ballaratapplicationnew.User_Module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.example.ballaratapplicationnew.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsViewHolder> {

    Context mContext;
    private List<BlogsList_Pojo> mFlowerList;

    BlogsAdapter(Context mContext, List<BlogsList_Pojo> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public BlogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        return new BlogsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final BlogsViewHolder holder, final int position) {

        holder.price_name.setText(mFlowerList.get(position).getBlog_name());
//        Picasso.get().load(mFlowerList.get(position).getBlog_image()).noFade().into(holder.profile_image);
        Glide.with(mContext).load(mFlowerList.get(position).getBlog_image()).into(holder.profile_image);


        holder.blogs_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mFlowerList.get(position).getBlog_url()));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class BlogsViewHolder extends RecyclerView.ViewHolder {


    TextView price_name;
    CircularImageView profile_image;
    RelativeLayout blogs_clicked;

    BlogsViewHolder(View itemView) {
        super(itemView);

        price_name = itemView.findViewById(R.id.row_item);
        profile_image = itemView.findViewById(R.id.profile_image);
        blogs_clicked = itemView.findViewById(R.id.blogs_clicked);

    }
}



