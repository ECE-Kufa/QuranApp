package com.example.quran2;

import android.app.Activity;

public class Page {

    private Activity context;
    private String pageName;
    private String pageText;
    private String hizb;
    private int pageNumber;
    private String juzu;

    Page(String pageName, Activity context) {
        this.pageName = pageName;
        this.context = context;
    }

    Page(String pageName, Activity context, String pageText) {
        this.pageText = pageText;
        this.pageName = pageName;
        this.context = context;
    }

    int getResId() {
        return this.context.getResources().getIdentifier(pageName,"drawable", this.context.getPackageName());
    }

    public String getPageText(){
        return this.pageText;
    }

    public String getPageNumber() {
        return pageName;
    }

    public String getHizb() {
        return hizb;
    }

    public String getJuzu() {
        return juzu;
    }

}