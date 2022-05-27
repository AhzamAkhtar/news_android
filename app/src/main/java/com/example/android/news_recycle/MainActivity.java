package com.example.android.news_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<News> newsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        NewsListAdapter newsListAdapter = new NewsListAdapter(MainActivity.this,newsArray);
        recyclerView.setAdapter(newsListAdapter);

        fetchApi();
    }


    public void fetchApi(){
        //RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://gnews.io/api/v4/top-headlines?token=f1d8a882ca73d2f674a31e1339b923eb&lang=en&country=in&topic=nation";
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
                        News news = new News(title,description,url,image);
                        //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        newsArray.add(news);

                    }

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
