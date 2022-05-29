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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter_h  extends RecyclerView.Adapter<NewsListAdapter_h.MyViewHolder> {
    //ArrayList<News> newslist   = new ArrayList<>();
    ArrayList<News_h> newslist;
    //ArrayList<News> newslist = new ArrayList<>()
    //ArrayList<News> newslist;
    Context context;
    public NewsListAdapter_h(Context context , ArrayList<News_h> newslist){
        this.context= context;
        this.newslist = newslist;


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_horizontal,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        News_h newss = newslist.get(position);
        holder.title.setText(newss.getTitle_h());
        holder.description.setText(newss.getDescription_h());
        Picasso.get().load(newss.getUrlTOImage_h()).into(holder.image);
        holder.share.setOnClickListener((view -> {
            //Toast.makeText(context,newss.getTitle_h(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Read This Article \n" + newss.getTitle_h() + "\n" + newss.getUrl_h());
            intent.setType("text/plain");
            Intent shareintent = Intent.createChooser(intent,null);
            context.startActivity(shareintent);

        }));
        holder.delete.setOnClickListener((view -> {
            newslist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,newslist.size());

        }));




    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView image;
        ImageView share;
        ImageView delete;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_id_h);
            description = (TextView) itemView.findViewById(R.id.description_id_h);
            image = (ImageView) itemView.findViewById(R.id.testing_h);
            share = (ImageView) itemView.findViewById(R.id.share);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
        public void onClick(View view){
            int position = this.getAbsoluteAdapterPosition();
            News_h newss = newslist.get(position);
            //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            String url = newss.getUrl_h();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));


        }
        /**public void shareq(View view){
            int position = this.getAbsoluteAdapterPosition();
            News_h newss = newslist.get(position);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Read This Article \n" + newss.getTitle_h() + "\n" + newss.getUrl_h());
            intent.setType("text/plain");

            Intent shareintent = Intent.createChooser(intent,null);
            context.startActivity(shareintent);

        }**/


    }
}

