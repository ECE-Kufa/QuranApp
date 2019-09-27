package com.example.quran2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import static com.example.quran2.Data.getFullTextsList;
import static com.example.quran2.Data.getPageText;
import static com.example.quran2.Data.pages;
import static com.example.quran2.Data.listsPosition;

public class MainActivity extends AppCompatActivity {

    /*
     Variables Declaration:::::::::::::::::::::::::::::::::::::
     */
    CostemPageAdapter costemPageAdapter;
//    int cPosition = 0;
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
        Log.v("MainActivity", "on create!!!!!!!!!");
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
        costemPageAdapter = new CostemPageAdapter(MainActivity.this, pages);
        viewPager.setAdapter(costemPageAdapter);

        /*
                Setting up the TextViews in the bottom bar (at the initial time)::::::::::
         */
//        listsPosition = viewPager.getCurrentItem();
        if(listsPosition != 486)setTopBarContent(listsPosition);
        else setTopBarContent(0);
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
                listsPosition = position;
                if(bookmarkItem!= null) {
                    if (position == bookmarkPagePosition) {
                        bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);
                    } else {
                        bookmarkItem.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                    }
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
    @Override
    protected void onResume() {
        super.onResume();
        Log.v("MainActivity", "on resume!!!!!!!!!");
    }
    @Override
    public void finish() {
        super.finish();
        Log.v("MainActivity", "finish!!!!!!!!!!!!!!!!!!!!!");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("MainActivity", "on destroy!!!!!!!!!");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v("MainActivity", "on stop!!!!!!!!!");
    }

    /*
    For the menu in the Action bar:::
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v("MainActivity", "on create options menu!!!!!!!!!");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        bookmarkItem = menu.findItem(R.id.bookmark_item);
        // setting the "bookmarked" icon if the current page has bookmark(for the start up of the app):
        if(pages.get(listsPosition).isBookmarked()) bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                sendSearchIntent(ArabicSearch(getFullTextsList(), s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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
                sendTextEntent(listsPosition);
                Toast.makeText(MainActivity.this,  getResources().getString(R.string.show_text) + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.bookmark_item:
                // if the page is not bookmarked, then add the bookmark:
                if(!pages.get(listsPosition).isBookmarked()){
                    // set bookmark::::::
                    setBookmark(listsPosition);
                    //change icon::::::::::::::::
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_black_24dp);
                    //show toast:::::::::::
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.bookmark_added) + listsPosition, Toast.LENGTH_SHORT).show();
                }
                else if (pages.get(viewPager.getCurrentItem()).isBookmarked()) {
                    // if it is bookmarked, remove the bookmark:
                    bookmarkItem.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                    pages.get(viewPager.getCurrentItem()).setBookmarked(false);
                    Toast.makeText(MainActivity.this,  getResources().getString(R.string.bookmark_removed) + listsPosition, Toast.LENGTH_SHORT).show();

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
     *For sending an intent to the TextActivity::::::::::::::
     */
    private void sendTextEntent(int position){
        Intent intent = new Intent(MainActivity.this, TextActivity.class);
        intent.putExtra("pageText", pages.get(position).getPageText());
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

    /* normalize Method
     * @return String
     */
    private String normalizer(String input) {

//        Remove honorific sign
//        input = input.replaceAll("\u0610", "");//ARABIC SIGN SALLALLAHOU ALAYHE WA SALLAM
//        input = input.replaceAll("\u0611", "");//ARABIC SIGN ALAYHE ASSALLAM
//        input = input.replaceAll("\u0612", "");//ARABIC SIGN RAHMATULLAH ALAYHE
//        input = input.replaceAll("\u0613", "");//ARABIC SIGN RADI ALLAHOU ANHU
//        input = input.replaceAll("\u0614", "");//ARABIC SIGN TAKHALLUS

        //Remove koranic anotation
        input = input.replaceAll("\u0615", "");//ARABIC SMALL HIGH TAH
        input = input.replaceAll("\u0616", "");//ARABIC SMALL HIGH LIGATURE ALEF WITH LAM WITH YEH
        input = input.replaceAll("\u0617", "");//ARABIC SMALL HIGH ZAIN
        input = input.replaceAll("\u0618", "");//ARABIC SMALL FATHA
        input = input.replaceAll("\u0619", "");//ARABIC SMALL DAMMA
        input = input.replaceAll("\u061A", "");//ARABIC SMALL KASRA
        input = input.replaceAll("\u06D6", "");//ARABIC SMALL HIGH LIGATURE SAD WITH LAM WITH ALEF MAKSURA
        input = input.replaceAll("\u06D7", "");//ARABIC SMALL HIGH LIGATURE QAF WITH LAM WITH ALEF MAKSURA
        input = input.replaceAll("\u06D8", "");//ARABIC SMALL HIGH MEEM INITIAL FORM
        input = input.replaceAll("\u06D9", "");//ARABIC SMALL HIGH LAM ALEF
        input = input.replaceAll("\u06DA", "");//ARABIC SMALL HIGH JEEM
        input = input.replaceAll("\u06DB", "");//ARABIC SMALL HIGH THREE DOTS
        input = input.replaceAll("\u06DC", "");//ARABIC SMALL HIGH SEEN
        input = input.replaceAll("\u06DD", "");//ARABIC END OF AYAH
        input = input.replaceAll("\u06DE", "");//ARABIC START OF RUB EL HIZB
        input = input.replaceAll("\u06DF", "");//ARABIC SMALL HIGH ROUNDED ZERO
        input = input.replaceAll("\u06E0", "");//ARABIC SMALL HIGH UPRIGHT RECTANGULAR ZERO
        input = input.replaceAll("\u06E1", "");//ARABIC SMALL HIGH DOTLESS HEAD OF KHAH
        input = input.replaceAll("\u06E2", "");//ARABIC SMALL HIGH MEEM ISOLATED FORM
        input = input.replaceAll("\u06E3", "");//ARABIC SMALL LOW SEEN
        input = input.replaceAll("\u06E4", "");//ARABIC SMALL HIGH MADDA
        input = input.replaceAll("\u06E5", "");//ARABIC SMALL WAW
        input = input.replaceAll("\u06E6", "");//ARABIC SMALL YEH
        input = input.replaceAll("\u06E7", "");//ARABIC SMALL HIGH YEH
        input = input.replaceAll("\u06E8", "");//ARABIC SMALL HIGH NOON
        input = input.replaceAll("\u06E9", "");//ARABIC PLACE OF SAJDAH
        input = input.replaceAll("\u06EA", "");//ARABIC EMPTY CENTRE LOW STOP
        input = input.replaceAll("\u06EB", "");//ARABIC EMPTY CENTRE HIGH STOP
        input = input.replaceAll("\u06EC", "");//ARABIC ROUNDED HIGH STOP WITH FILLED CENTRE
        input = input.replaceAll("\u06ED", "");//ARABIC SMALL LOW MEEM

        //Remove tatweel
        input = input.replaceAll("\u0640", "");

        //Remove tashkeel
        input=input.replaceAll("\u064B", "");//ARABIC FATHATAN
        input=input.replaceAll("\u064C", "");//ARABIC DAMMATAN
        input=input.replaceAll("\u064D", "");//ARABIC KASRATAN
        input=input.replaceAll("\u064E", "");//ARABIC FATHA
        input=input.replaceAll("\u064F", "");//ARABIC DAMMA
        input=input.replaceAll("\u0650", "");//ARABIC KASRA
        input=input.replaceAll("\u0651", "");//ARABIC SHADDA
        input=input.replaceAll("\u0652", "");//ARABIC SUKUN
        input=input.replaceAll("\u0653", "");//ARABIC MADDAH ABOVE
        input=input.replaceAll("\u0654", "");//ARABIC HAMZA ABOVE
        input=input.replaceAll("\u0655", "");//ARABIC HAMZA BELOW
        input=input.replaceAll("\u0656", "");//ARABIC SUBSCRIPT ALEF
        input=input.replaceAll("\u0657", "");//ARABIC INVERTED DAMMA
        input=input.replaceAll("\u0658", "");//ARABIC MARK NOON GHUNNA
        input=input.replaceAll("\u0659", "");//ARABIC ZWARAKAY
        input=input.replaceAll("\u065A", "");//ARABIC VOWEL SIGN SMALL V ABOVE
        input=input.replaceAll("\u065B", "");//ARABIC VOWEL SIGN INVERTED SMALL V ABOVE
        input=input.replaceAll("\u065C", "");//ARABIC VOWEL SIGN DOT BELOW
        input=input.replaceAll("\u065D", "");//ARABIC REVERSED DAMMA
        input=input.replaceAll("\u065E", "");//ARABIC FATHA WITH TWO DOTS
        input=input.replaceAll("\u065F", "");//ARABIC WAVY HAMZA BELOW
        input=input.replaceAll("\u0670", "");//ARABIC LETTER SUPERSCRIPT ALEF

        //Replace Waw Hamza Above by Waw
//        input=input.replaceAll("\u0624", "\u0648");

        //Replace Ta Marbuta by Ha
//        input=input.replaceAll("\u0629", "\u0647");

        //Replace Ya
        // and Ya Hamza Above by Alif Maksura
//        input=input.replaceAll("\u064A", "\u0649");
//        input=input.replaceAll("\u0626", "\u0649");

        // Replace Alifs with Hamza Above/Below
        // and with Madda Above by Alif
//        input = input.replaceAll("\u0622", "\u0627");
//        input=input.replaceAll("\u0623", "\u0627");
//        input=input.replaceAll("\u0625", "\u0627");

        return input;
    }

    private void setBookmark(int bookmarkPosition) {
        // the code for setting a bookmark is placed here::::::::
        // change the boolean variable to be true and change the position of bookmarkPagePosition:::::::::;
        bookmarkPagePosition = bookmarkPosition;
        if (bookmarkPosition != -1) {
            pages.get(bookmarkPosition).setBookmarked(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MainActivity", "on pause!!!!!!!!!");

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("position", viewPager.getCurrentItem());
        editor.putInt("bookmarkPagePosition", bookmarkPagePosition);
        editor.apply();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MainActivity", "on start!!!!!!!!!");

        SharedPreferences sh = getPreferences(MODE_PRIVATE);

        // Because when you click on an item in the search activity ListView,
        // it will change the value of @listsPosition to the position of the search result you clicked.
        // and also the number 486 is the number of total pages, and there is no such position in the lists.
        if(listsPosition == 486) listsPosition = sh.getInt("position", 0);
        viewPager.setCurrentItem(listsPosition);
        setBookmark(sh.getInt("bookmarkPagePosition", -1));
    }

    /**
     * Searches for word in all elements of the input list.
     * @param word the word you want to search for.
     * @param input the input list you want to search in.
     * @return the indexes of elements in input list which are have the word.
     */
    private ArrayList<SearchResult> ArabicSearch(String[] input, String word){
        ArrayList<SearchResult> outputArray= new ArrayList<>();

        // for each element in input::::::
        for(int i = 0; i < input.length; i++){
            String normalizedElement = normalizer(input[i]);
            if(normalizedElement.contains(word)){
//                outputArray.add(i);
                String previosAyaNumber = "";
                int j = normalizedElement.indexOf(word), k = 0;
                // remove the second condition and search for a word in the firs Aya in a page, and see what happens!!!
                // that because there is no Aya number at the beginning of the page...!
                while(normalizedElement.charAt(j) != '\uFD3F' && j > 0) j--;
                k = j + 1;
                while(normalizedElement.charAt(k)!= '\uFD3E') k++;
                previosAyaNumber = normalizedElement.substring(j+1, k);

                outputArray.add(new SearchResult(previosAyaNumber, i));
            }
        }
        return outputArray;
    }

    private void sendSearchIntent(ArrayList<SearchResult> searchResultsList){
        Intent intent = new Intent(this, SearchActivity.class);

        intent.putExtra("search results list", searchResultsList);
        startActivity(intent);
    }



}
