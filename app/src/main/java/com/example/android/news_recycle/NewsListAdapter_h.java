package com.example.android.news_recycle;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView image;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_id_h);
            description = (TextView) itemView.findViewById(R.id.description_id_h);
            image = (ImageView) itemView.findViewById(R.id.testing_h);
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
    }
}

