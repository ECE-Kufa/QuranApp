package com.example.quran2;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
//import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {
//    private ArrayList<Page> pages;
    String pageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);

        Toolbar textActivityToolbar = (Toolbar) findViewById(R.id.toolbar_text_activity);
        setSupportActionBar(textActivityToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
//        pages = MainActivity.getPages();
        TextView textView = (TextView) findViewById(R.id.page_text_view);

        /*
        Getting Extras:::::::::::::
         */
        Bundle extras = getIntent().getExtras();
        pageText = extras.getString("pageText",getString(R.string.there_is_no_text_available));

        textView.setText(pageText);

    }
}
