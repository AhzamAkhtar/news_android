package com.example.android.news_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ImageView nointernet = (ImageView) findViewById(R.id.noInternet);
    private Boolean wifiConnected;
    private Boolean mobileConnected;
    ArrayList<News> newsArray = new ArrayList<>();
    ArrayList<News_h> newsArray_h = new ArrayList<>();
    RecyclerView recyclerView,recyclerView1;
    NewsListAdapter newsListAdapter;
    //OfferAdapter offerAdapter;
    NewsListAdapter_h newsListAdapter_h;
    String url = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=nation";
    String url_h = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=breaking-news";
    public void changeurls(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
         String  url_sports = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=sports";
         newsArray.clear();
        fetchApi(url_sports);
        newsListAdapter.notifyDataSetChanged();


    }
    public void changeurle(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
         String url_ent = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=entertainment";
        newsArray.clear();
        fetchApi(url_ent);
        newsListAdapter.notifyDataSetChanged();
    }
    public void changeurlw(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        String url_world = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=world";
        newsArray.clear();
        fetchApi(url_world);
        newsListAdapter.notifyDataSetChanged();
    }
    public void changeurlsc(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        String url_science = "https://gnews.io/api/v4/top-headlines?token=b2cc9ca74c1f3ad3403c04fd9a92b8c3&lang=en&country=in&topic=science";
        newsArray.clear();
        fetchApi(url_science);
        newsListAdapter.notifyDataSetChanged();
    }
     //List<News> newsArray;

     SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for horizontal

        recyclerView1 = (RecyclerView) findViewById(R.id.firstrecyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView1.setHasFixedSize(true);
        /*List<Integer> iamgelist = new ArrayList<>();
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        iamgelist.add(R.drawable.night);
        offerAdapter = new OfferAdapter(iamgelist);
        recyclerView1.setAdapter(offerAdapter);*/

        newsListAdapter_h = new NewsListAdapter_h(MainActivity.this,newsArray_h);
        recyclerView1.setAdapter(newsListAdapter_h);


        fetchApi_h(url_h);
        //newsListAdapter_h.notifyDataSetChanged();

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeup);


        //newsListAdapter.notifyDataSetChanged();





        // for vertical
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetchApi(url);
        newsListAdapter = new NewsListAdapter(MainActivity.this,newsArray);
        recyclerView.setAdapter(newsListAdapter);
        //fetchApi(url);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeup);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsArray.clear();
                newsArray_h.clear();
                fetchApi(url);
                fetchApi_h(url_h);
                //Toast.makeText(MainActivity.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    public void fetchApi(String url){
        checkConnection();
        ProgressBar prog = (ProgressBar) findViewById(R.id.prog);
        prog.setVisibility(View.VISIBLE);
        //prog.setVisibility(View.VISIBLE);
        //RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=nation";
        //String url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=097575c06ecd4bc4b550fae79a2bd131";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject base = new JSONObject(response);
                    JSONArray jsonArray = response.getJSONArray("articles");
                    //ArrayList<News> newsArray = new ArrayList<>();
                    for(int i=0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String url= jsonObject.getString("url");
                        String image = jsonObject.getString("image");
                        //JSONArray newjsonarray = jsonObject.getJSONArray("source");
                        //String name = (String) jsonObject.getJSONObject("souce").get("name");
                        //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                        //for(int j=0; j<newjsonarray.length(); j++){
                            //JSONObject newjsonObject = jsonArray.getJSONObject(i);
                            //String name = newjsonObject.getString("name");
                            //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                        //}
                        News news = new News(title,description,url,image);
                        //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        newsArray.add(news);
                        //newsArray_h.add(news);
                        prog.setVisibility(View.GONE);

                    }
                    newsListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed! To Load Data , Try Later ", Toast.LENGTH_SHORT).show();
                prog.setVisibility(View.GONE);
            }
        });
        //queue.add(jsonObjectRequest);
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    public void fetchApi_h(String url){
        checkConnection();
        ProgressBar prog2 = (ProgressBar) findViewById(R.id.prog2);
        prog2.setVisibility(View.VISIBLE);
        //ProgressBar prog = (ProgressBar) findViewById(R.id.prog);
        //prog.setVisibility(View.VISIBLE);
        //prog.setVisibility(View.VISIBLE);
        //RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=nation";
        //String url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=097575c06ecd4bc4b550fae79a2bd131";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject base = new JSONObject(response);
                    JSONArray jsonArray = response.getJSONArray("articles");
                    //ArrayList<News> newsArray = new ArrayList<>();
                    for(int i=0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String url= jsonObject.getString("url");
                        String image = jsonObject.getString("image");
                        //JSONArray newjsonarray = jsonObject.getJSONArray("source");
                        //String name = (String) jsonObject.getJSONObject("souce").get("name");
                        //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                        //for(int j=0; j<newjsonarray.length(); j++){
                        //JSONObject newjsonObject = jsonArray.getJSONObject(i);
                        //String name = newjsonObject.getString("name");
                        //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                        //}
                        News_h news = new News_h(title,description,url,image);
                        //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        //newsArray.add(news);
                        newsArray_h.add(news);
                        prog2.setVisibility(View.GONE);

                    }
                    //
                    newsListAdapter_h.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed! To Load Data , Try Later  ", Toast.LENGTH_SHORT).show();
                prog2.setVisibility(View.GONE);


            }
        });
        //queue.add(jsonObjectRequest);
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            wifiConnected = networkInfo.getType()==ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType()==ConnectivityManager.TYPE_MOBILE;

            if(wifiConnected || mobileConnected){
                //Toast.makeText(this, "on", Toast.LENGTH_SHORT).show();
                ImageView imageView = (ImageView) findViewById(R.id.noInternet);
                imageView.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                newsArray.clear();
                newsArray_h.clear();
                //nointernet.setVisibility(View.GONE);
                ImageView imageView = (ImageView) findViewById(R.id.noInternet);
                imageView.setVisibility(View.VISIBLE);
            }
        }

    }




}
