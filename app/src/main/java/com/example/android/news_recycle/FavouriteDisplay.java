package com.example.android.news_recycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.news_recycle.data.MyDbHandler;
import com.example.android.news_recycle.modal.Favourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDisplay extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faf_layout);
        MyDbHandler mylistmain = new MyDbHandler(getApplicationContext());

        Favourite mylist  = new Favourite();
        mylist.setTitle("ygyhjvj");
        mylist.setDescription("de1");
        mylist.setImage("imag1");
        mylistmain.addFavourite(mylist);

        Favourite mylist2  = new Favourite();
        mylist.setTitle("ygyhjvj");
        mylist.setDescription("de1");
        mylist.setImage("imag1");
        mylistmain.addFavourite(mylist2);

        ArrayList<String> favoutites = new ArrayList<>();
        listView = findViewById(R.id.list);


        List<Favourite> allfav = mylistmain.getAllFavourite();
        for(Favourite favourite:allfav){
            Log.d("dbharry", "\nId: " + favourite.getId() + "\n" +
                    "Name: " + favourite.getTitle() + "\n"+
                    "Phone Number: " + favourite.getDescription() + "\n" + "Image_url :" + favourite.getImage());

            //favoutites.add(favourite.getTitle() + " (" + favourite.getDescription() + ")");
            favoutites.add(favourite.getTitle());
        }
        ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<>(FavouriteDisplay.this, android.R.layout.simple_list_item_1,favoutites);
        listView.setAdapter(arrayAdapter);
        Log.d("dbharray","total"+mylistmain.getCount());

    }
}
