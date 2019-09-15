package com.example.quran2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CostemPageAdapter extends PagerAdapter {
    private Context ctx;
    private ArrayList<Integer> resources;

    public CostemPageAdapter(Context ctx, ArrayList<Integer> resources){
        this.resources = resources;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return resources.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.page_layout, container, false);

        ImageView pageImageView = (ImageView) itemView.findViewById(R.id.page_image_view);
        pageImageView.setImageResource(this.resources.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

}
