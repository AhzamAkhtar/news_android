package com.example.android.news_recycle;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHOlder>{

    private List<Integer> imageList;

    public OfferAdapter(List<Integer> imageList){
        this.imageList= imageList;
    }
    @NonNull
    @Override
    public OfferViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_layout , parent, false);
        return new OfferViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHOlder holder, int position) {
        holder.mImageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class OfferViewHOlder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public OfferViewHOlder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.offer_iv);
        }
    }
}