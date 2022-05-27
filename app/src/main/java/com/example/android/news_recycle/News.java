package com.example.android.news_recycle;

public class News {

    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mUrlTOImage;

    public News(String title, String description, String url, String urltoimage) {
        mTitle = title;
        mDescription = description;
        mUrl = url;
        mUrlTOImage = urltoimage;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getUrl(){
        return mUrl;
    }
    public String getUrlTOImage(){
        return mUrlTOImage;
    }
}
