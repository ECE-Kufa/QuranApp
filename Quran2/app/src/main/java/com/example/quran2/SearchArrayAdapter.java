package com.example.quran2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.quran2.Data.START_POSITION_OF_LISTS;
import static com.example.quran2.Data.pages;

public class SearchArrayAdapter extends ArrayAdapter<SearchResult> {

    public SearchArrayAdapter(Context context, ArrayList<SearchResult> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.search_list_view_item, parent, false);
        }

        SearchResult currentResult = getItem(position);
        TextView surahTextView = (TextView) listItemView.findViewById(R.id.search_item_surah_text_view);
        surahTextView.setText(pages.get(currentResult.getPositionOfPage()).getSurah());

        TextView pageNumberTextView = (TextView) listItemView.findViewById(R.id.search_item_page_number_text_view);
        pageNumberTextView.setText("رقم الصفحة: " + (currentResult.getPositionOfPage() + START_POSITION_OF_LISTS));

        TextView paragraphTextView = (TextView) listItemView.findViewById(R.id.search_item_paragraph);
        paragraphTextView.setText(currentResult.getAya());


        return listItemView;
    }
}
