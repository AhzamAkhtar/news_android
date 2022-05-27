package com.example.android.news_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
    ArrayList<News> newsArray = new ArrayList<>();
    RecyclerView recyclerView;
    NewsListAdapter newsListAdapter;
    String url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=nation";
    public void changeurls(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
         url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=sports";
         newsArray.clear();
        fetchApi();
        newsListAdapter.notifyDataSetChanged();


    }
    public void changeurle(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=entertainment";
        newsArray.clear();
        fetchApi();
        newsListAdapter.notifyDataSetChanged();
    }
    public void changeurlw(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=world";
        newsArray.clear();
        fetchApi();
        newsListAdapter.notifyDataSetChanged();
    }
    public void changeurlsc(View view){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        url = "https://gnews.io/api/v4/top-headlines?token=a5aaa59810d58657fc9d4f7d6c890ca6&lang=en&country=in&topic=science";
        newsArray.clear();
        fetchApi();
        newsListAdapter.notifyDataSetChanged();
    }
     //List<News> newsArray;

     SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //fetchApi();
        newsListAdapter = new NewsListAdapter(MainActivity.this,newsArray);
        recyclerView.setAdapter(newsListAdapter);
        fetchApi();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeup);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchApi();
                Toast.makeText(MainActivity.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    public void fetchApi(){
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
                Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
            }
        });
        //queue.add(jsonObjectRequest);
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }


}
