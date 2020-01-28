package com.example.ballaratapplicationnew.Admin_Module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ballaratapplicationnew.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;


public class All_Users_Adapter extends RecyclerView.Adapter<Users_ViewHolder> {

    Context mContext;
    private List<All_User_Pojo> mFlowerList;

    All_Users_Adapter(Context mContext, List<All_User_Pojo> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public Users_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_admin_adapter, parent, false);
        return new Users_ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final Users_ViewHolder holder, final int position) {

        holder.user_name.setText(mFlowerList.get(position).getName());
        holder.email.setText(mFlowerList.get(position).getEmail());

//        Glide.with(mContext).load(mFlowerList.get(position).getBlog_image()).into(holder.profile_image);


        holder.blogs_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext , All_User_Detail.class) ;
                intent.putExtra("user_id" , mFlowerList.get(position).getId());
                Log.d("Mysss22" , ""+mFlowerList.get(position).getId());
                mContext.startActivity(intent);


//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mFlowerList.get(position).getBlog_url()));
//                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class Users_ViewHolder extends RecyclerView.ViewHolder {


    TextView user_name,email;
    CircularImageView profile_image;
    RelativeLayout blogs_clicked;

    Users_ViewHolder(View itemView) {
        super(itemView);

        user_name = itemView.findViewById(R.id.name);
        email = itemView.findViewById(R.id.pkg_name);
        blogs_clicked = itemView.findViewById(R.id.blogs_clicked);

    }
}





//public class All_Users_Adapter
//
//
//
//
//
// extends RecyclerView.Adapter<All_Users_Adapter.ViewHolder> {
//
//    private List<String> mData;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//    Context contexts;
//
//    // data is passed into the constructor
//    public All_Users_Adapter(Context context, List<String> data) {
//        contexts = context;
//        this.mInflater = LayoutInflater.from(contexts);
//        this.mData = data;
//    }
//
//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.all_user_admin_adapter, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        String animal = mData.get(position);
//
//        holder.myTextView.setText(animal);
//
//        holder.clicked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                contexts.startActivity(new Intent(contexts, BlogsActivity.class));
//            }
//        });
//
//    }
//
//    // total number of rows
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView myTextView,m2;
//        ImageView profile_image2;
//        RelativeLayout clicked;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            myTextView = itemView.findViewById(R.id.row_item);
//            m2 = itemView.findViewById(R.id.m2);
//
//            clicked = itemView.findViewById(R.id.clicked);
//            profile_image2 = itemView.findViewById(R.id.profile_image2);
//
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
////    // convenience method for getting data at click position
////    String getItem(int id) {
////        return mData.get(id);
////    }
////
////    // allows clicks events to be caught
////    void setClickListener(ItemClickListener itemClickListener) {
////        this.mClickListener = itemClickListener;
////    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//}