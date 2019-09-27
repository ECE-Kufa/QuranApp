package com.example.quran2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quran2.R;

import java.util.ArrayList;

import static com.example.quran2.Data.getFullTextsList;
import static com.example.quran2.Data.listsPosition;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        ListView listView = (ListView) findViewById(R.id.search_activity_layout);

        Toolbar textActivityToolbar = (Toolbar) findViewById(R.id.search_activity_toolbar);
        setSupportActionBar(textActivityToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        final ArrayList<SearchResult> searchResults = extras.getParcelableArrayList("search results list");

//        ArrayList<SearchResult> searchParagraphes = new ArrayList<>();
//        if (searchResults != null) {
//            for (int i :
//                    searchResults) {
//                searchParagraphes.add(getFullTextsList()[i].substring(0, 102) + "...");
////                searchParagraphes.add(pages.get(i).getPageText().substring(0, 102) + "...");
//            }

//        final ArrayList<SearchResult> searchResults = new ArrayList<>();
//        for (int i = 0; i < resultsAyat.size(); i++) {
//            searchResults.add(new SearchResult(resutlsAyaNumbers.get(i), resultsAyat.get(i), resultsPagePositions.get(i)));
//        }

            SearchArrayAdapter arrayAdapter = new SearchArrayAdapter (this, searchResults);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listsPosition = searchResults.get(position).getPositionOfPage();
                    finish();
                }
            });
        }
    }
