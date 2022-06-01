package com.example.android.news_recycle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.news_recycle.data.MyDbHandler;
import com.example.android.news_recycle.modal.Favourite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter  extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    //ArrayList<News> newslist   = new ArrayList<>();
    ArrayList<News> newslist;
   //ArrayList<News> newslist = new ArrayList<>()
    //ArrayList<News> newslist;
    Context context;
    public NewsListAdapter(Context context , ArrayList<News> newslist){
        this.context= context;
        this.newslist = newslist;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        News newss = newslist.get(position);
        holder.title.setText(newss.getTitle());
        holder.description.setText(newss.getDescription());
        Picasso.get().load(newss.getUrlTOImage()).into(holder.image);
        holder.share_verticle.setOnClickListener((view -> {
            //Toast.makeText(context,newss.getTitle_h(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Read This Article \n" + newss.getTitle() + "\n" + newss.getUrl());
            intent.setType("text/plain");

            Intent shareintent = Intent.createChooser(intent,null);
            context.startActivity(shareintent);

        }));
        holder.delete_verticle.setOnClickListener((view -> {
            newslist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,newslist.size());
        }));
        /**holder.fav_verticle.setOnClickListener(view -> {
            MyDbHandler mylist = new MyDbHandler(context);

            Favourite myfav = new Favourite();
            myfav.setTitle(newss.getTitle());
            myfav.setDescription(newss.getDescription());
            myfav.setImage(newss.getUrlTOImage());
            mylist.addFavourite(myfav);

            //Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
        });**/

    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView image;
        ImageView share_verticle;
        ImageView delete_verticle;
        ImageView fav_verticle;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_id);
            description = (TextView) itemView.findViewById(R.id.description_id);
            image = (ImageView) itemView.findViewById(R.id.testing);
            share_verticle = (ImageView) itemView.findViewById(R.id.share_verticle);
            delete_verticle = (ImageView) itemView.findViewById(R.id.delete_verticle);
            fav_verticle = (ImageView) itemView.findViewById(R.id.fav_verticle);
        }
        public void onClick(View view){
            int position = this.getAbsoluteAdapterPosition();
            News newss = newslist.get(position);
            //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            String url = newss.getUrl();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));


        }
    }
}
