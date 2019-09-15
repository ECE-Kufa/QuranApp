package com.example.quran2;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewAnimationUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> pagesResources = new ArrayList<Integer>();
        ArrayList<Page> pages = new ArrayList<Page>();
        for(int i = 1; i <= 604; i++){
            Page p = new Page("p" + i, MainActivity.this);
            pages.add(p);
            pagesResources.add(p.getResId());
        }

        CostemPageAdapter costemPageAdapter = new CostemPageAdapter(MainActivity.this, pagesResources);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(costemPageAdapter);


    }
}
