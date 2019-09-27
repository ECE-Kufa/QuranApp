package com.example.quran2;

import android.app.Activity;

 class Page {

    private Activity context;
    private String pageName;
    private String pageText;
    private String hizb;
    private String juzu;
    private boolean isBookmarked = false;
    private String surah = "سورة البقرة";


     Page(String pageName, Activity context) {
        this.pageName = pageName;
        this.context = context;
    }

    Page(String pageName, Activity context, String pageText) {
        this.pageText = pageText;
        this.pageName = pageName;
        this.context = context;
    }

     public void setBookmarked(boolean bookmarked) {
         isBookmarked = bookmarked;
     }

     public boolean isBookmarked() {
         return isBookmarked;
     }

     int getResId() {
        return this.context.getResources().getIdentifier(pageName,"drawable", this.context.getPackageName());
    }

    String getPageText(){
        return this.pageText;
    }

    String getPageNumber() {
        return Integer.valueOf(pageName.substring(1)).toString();
    }

    String getHizb() {
        return hizb;
    }

    String getJuzu() {
        return juzu;
    }

     public String getSurah() {
         return surah;
     }
 }