package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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

public class Queries_Adapter extends RecyclerView.Adapter<BlogsViewHolder> {

    Context mContext;
    private List<Queries_List_Pojo> mFlowerList;

    Queries_Adapter(Context mContext, List<Queries_List_Pojo> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public BlogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_queries_adapter, parent, false);
        return new BlogsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final BlogsViewHolder holder, final int position) {

        holder.name.setText(mFlowerList.get(position).getName());
        holder.pkg_name.setText(mFlowerList.get(position).getPkg_name());

        holder.blogs_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(mContext , DetailsActivity.class) ;
                 intent.putExtra("query_id" , mFlowerList.get(position).getId());
                Log.d("Mysss" , ""+mFlowerList.get(position).getId());
                 mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class BlogsViewHolder extends RecyclerView.ViewHolder {


    TextView name,pkg_name;
    RelativeLayout blogs_clicked;

    BlogsViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        pkg_name = itemView.findViewById(R.id.pkg_name);
        blogs_clicked = itemView.findViewById(R.id.blogs_clicked);

    }
}



