package com.example.quran2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.quran2.MainActivity.bottomBar;
import static com.example.quran2.MainActivity.toolbar;

public class CostemPageAdapter extends PagerAdapter {
    private int k = 0;
    private Context mCtx;
    private ArrayList<Page> mPages;

    /**
     * A constrctor for this class:::::::
     * @param mCtx  is the context.
     * @param mPages is the ArrayList (of Page data type) of pages that you want to display.
     */
    public CostemPageAdapter(Context mCtx, ArrayList<Page> mPages){
        this.mPages = mPages;
        this.mCtx = mCtx;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (RelativeLayout)o);
    }

    /**
     * for Instantiating the item for the Comming page:::::::::::
     * @param position The position of the current item.
     * @return   Returns the view you want to display by the ViewPager.
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.page_layout, container, false);

        ImageView pageImageView = (ImageView) itemView.findViewById(R.id.page_image_view);
        pageImageView.setImageResource(this.mPages.get(position).getResId());
        container.addView(itemView);

        /*
        for the click event, when the user clicks on the page.
        You can neither implement the method OnTouchEvent of the ViewPager and put a code there to distinguash the click from the long click or the move.
         */
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                For hiding the toolbar and the bottom bar when the screen is clicked.
                 */
                if (k == 0) {
                    k = 1;
                    toolbar.setVisibility(View.VISIBLE);
                    bottomBar.setVisibility(View.VISIBLE);
                } else if (k == 1) {
                    k = 0;
                    toolbar.setVisibility(View.GONE);
                    bottomBar.setVisibility(View.GONE);
                }
            }
        });

        return itemView;
    }

    /**
     * destroying the item that is not currently visible to the user, to minimize memory usage.
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
