package com.example.android.news_recycle.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.news_recycle.modal.Favourite;
import com.example.android.news_recycle.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context){
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_TITLE
                + " TEXT," + Params.KEY_DESCRIPTION + " TEXT," + Params.KEY_IMAGE + " TEXT" + ")";
        Log.d("dbharry","Queary being run is: "+ create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addFavourite(Favourite favourite){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE,favourite.getTitle());
        values.put(Params.KEY_DESCRIPTION,favourite.getDescription());
        values.put(Params.KEY_IMAGE,favourite.getImage());

        db.insert(Params.TABLE_NAME,null,values);
        Log.d("dbharry","Successfully insertes");
        db.close();



    }

    public List<Favourite> getAllFavourite(){
        List<Favourite> favouriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                Favourite favourite = new Favourite();
                favourite.setId(Integer.parseInt(cursor.getString(0)));
                favourite.setTitle(cursor.getString(1));
                favourite.setDescription(cursor.getString(2));
                favourite.setImage(cursor.getString(3));

                favouriteList.add(favourite);
            }while (cursor.moveToNext());
        }
        return favouriteList;
    }
    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

}
