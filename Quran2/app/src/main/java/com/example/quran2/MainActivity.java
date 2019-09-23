package com.example.quran2;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import static com.example.quran2.Data.getPageText;
import static com.example.quran2.Data.pages;

public class MainActivity extends AppCompatActivity {

    /*
     Variables Declaration:::::::::::::::::::::::::::::::::::::
     */
    MenuItem bookmarkItem;
    static Toolbar toolbar;
    static LinearLayout bottomBar, topBar;
    private int currentPosition;
    String pageNumber;
    private ViewPager viewPager;
    public TextView hizbTextView;
    public TextView topPageNumberTextView, bottomPageNumberTextView;
    public TextView juzuTextView;
    private int bookmarkPagePosition = -1;
    /*
     End Variable Declaration;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        setting the costomized toolbar:::::::::::::::::::::::::::::::::
         */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        Initializing some variables::::::::::::::::
         */
        viewPager = findViewById(R.id.view_pager);

        bottomBar = (LinearLayout) findViewById(R.id.bottom_bar_main_layout);
        topBar = (LinearLayout) findViewById(R.id.top_bar_linear_layout);
        bookmarkItem = findViewById(R.id.bookmark_item);
        hizbTextView = (TextView) findViewById(R.id.hizb_text_view);
        bottomPageNumberTextView = (TextView) findViewById(R.id.page_number_text_view);
        topPageNumberTextView = (TextView) findViewById(R.id.top_page_number_text_view);
        juzuTextView = (TextView) findViewById(R.id.juzu_text_view);

//_____________________________________________________________________________________________________________________________________
        /*
        Setting Resources for the pages:::::::
         */
        pages = new ArrayList<Page>();
        for(int i = 0; i < 604; i++){
            //The if statement is set Because there is no enough texts:
            if(i<=10) pages.add(new Page("p" + (i + 1), MainActivity.this, getPageText(i)));
            else pages.add(new Page("p" + (i + 1), MainActivity.this));
        }
        /*
        Setting the adapter for the ViewPager:::::::::::::::::
         */
        CostemPageAdapter costemPageAdapter = new CostemPageAdapter(MainActivity.this, pages);
        viewPager.setAdapter(costemPageAdapter);

        /*
                Setting up the TextViews in the bottom bar (at the initial time)::::::::::
         */
        int cPosition = viewPager.getCurrentItem();
        setTopBarContent(cPosition);
        /*
        Setting up the TextViews in the bottom bar::::::::::
        It should change when the page changes::::::::::::::::::::
         */
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setTopBarContent(position);
                super.onPageSelected(position);

                // if the current page is bookmarked and and the current icon is "not bookmarked", then set the icon to "bookmarked":
                // this weird expression is how to compare two drawables.::: && bookmarkItem.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_bookmark_border_black_24dp).getConstantState())
                if(position == bookmarkPagePosition ){
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);
                }
                else{
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                }

            }
        });

        /*
        Dont care about THIS:::::
         */
//        ImageView imageView = (ImageView) findViewById(R.id.page_image_view);
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//
//        if(imageView != null) {
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (k == 0) {
//                        k = 1;
//                        toolbar.setVisibility(View.VISIBLE);
//                    } else if (k == 1) {
//                        k = 0;
//                        toolbar.setVisibility(View.GONE);
//                    }
//                }
//            });
//        }
    }

    /*
    For the menu in the Action bar:::
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        bookmarkItem = menu.findItem(R.id.bookmark_item);
        // setting the "bookmarked" icon if the current page has bookmark(for the start up of the app):
        if(pages.get(viewPager.getCurrentItem()).isBookmarked()) bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);
        return true;
    }

    /*
    This for the Options in the MENU:::::::::::;
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.phahras_item:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.phahras), Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_text_item:
                sendTextEntent();
                Toast.makeText(MainActivity.this,  getResources().getString(R.string.show_text) + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                 break;
            case R.id.bookmark_item:
                // if the page is not bookmarked, then add the bookmark:
                if(!pages.get(viewPager.getCurrentItem()).isBookmarked()){
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);
                    pages.get(viewPager.getCurrentItem()).setBookmarked(true);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.bookmark_added) + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();

                    // the code for setting a bookmark is placed here::::::::
                    bookmarkPagePosition = viewPager.getCurrentItem();
                }
                else if (pages.get(viewPager.getCurrentItem()).isBookmarked()) {
                    // if it is bookmarked, remove the bookmark:
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                    pages.get(viewPager.getCurrentItem()).setBookmarked(false);
                    Toast.makeText(MainActivity.this,  getResources().getString(R.string.bookmark_removed) + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();

                    // the code for removing a bookmark is placed here:::::::::::
                    bookmarkPagePosition = -1;
                }
                break;
            case R.id.go_to_bookmark:
                if(bookmarkPagePosition == -1) Toast.makeText(MainActivity.this, getResources().getString(R.string.no_bookmark_available), Toast.LENGTH_SHORT).show();
                else{
                    viewPager.setCurrentItem(bookmarkPagePosition);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    I don't know what is this (^-^)::::
     */
//    public static ArrayList<Page> getPages() {
//        return pages;
//    }

    /*
    For sending an intent to the TextActivity::::::::::::::
     */
    private void sendTextEntent(){
        Intent intent = new Intent(MainActivity.this, TextActivity.class);
        intent.putExtra("pageText", pages.get(viewPager.getCurrentItem()).getPageText());
        startActivity(intent);
    }

    private void setTopBarContent(int position){
        hizbTextView.setText(pages.get(position).getHizb());
//                pageNumberTextView.setText(String.valueOf(pages.get(position).getPageNumber()));
        pageNumber = pages.get(position).getPageNumber();
        topPageNumberTextView.setText(pageNumber);
        bottomPageNumberTextView.setText(pageNumber);
        juzuTextView.setText(pages.get(position).getJuzu());
    }


}
