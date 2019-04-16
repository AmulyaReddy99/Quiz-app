package com.tech.quizapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemAdapter extends BaseAdapter {

    String[] categories;
    LayoutInflater mInflater;
    public ItemAdapter(Context c, String[] categories){
        this.categories = categories;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int i) {
        return categories[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.tab_easy, null);
//        CheckBox category= v.findViewById(R.id.checkBox);
//
//        category.setText(categories[i]);
        return v;
    }

}