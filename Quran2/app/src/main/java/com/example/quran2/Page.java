package com.example.quran2;

import android.app.Activity;

public class Page {

    private Activity context;
    private String pageName;
    private String pageText;

    public Page(String pageName, Activity context) {
        this.pageName = pageName;
        this.context = context;
    }

    public Page(String pageName, Activity context, String pageText) {
        this.pageText = pageText;
        this.pageName = pageName;
        this.context = context;
    }

    public int getResId() {
        return this.context.getResources().getIdentifier(pageName,"drawable", this.context.getPackageName());
    }

    public String getPageText(){
        return this.pageText;
    }



}