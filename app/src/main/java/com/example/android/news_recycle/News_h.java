package com.example.android.news_recycle;

public class News_h {
    private String mTitle_h;
    private String mDescription_h;
    private String mUrl_h;
    private String mUrlTOImage_h;

    public News_h(String title_h, String description_h, String url_h, String urltoimage_h) {
        mTitle_h = title_h;
        mDescription_h = description_h;
        mUrl_h = url_h;
        mUrlTOImage_h = urltoimage_h;
    }
    public String getTitle_h(){
        return mTitle_h;
    }
    public String getDescription_h(){
        return mDescription_h;
    }
    public String getUrl_h(){
        return mUrl_h;
    }
    public String getUrlTOImage_h(){
        return mUrlTOImage_h;
    }
}
