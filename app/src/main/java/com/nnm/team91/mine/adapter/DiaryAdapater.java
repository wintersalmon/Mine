package com.nnm.team91.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.DiaryData;

import java.util.ArrayList;

/**
 * Created by wintersalmon on 2016. 11. 25..
 * DiaryAdapter
 */

public class DiaryAdapater extends BaseAdapter {
    private ArrayList<DiaryData> listViewItemList = new ArrayList<DiaryData>() ;

    public DiaryAdapater() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_diary, parent, false);
        }

        TextView timeTextView = (TextView) convertView.findViewById(R.id.diaryTextViewTime);
        TextView keyTagTextView = (TextView) convertView.findViewById(R.id.diaryTextViewKeyTag);
        TextView hashTagTextView = (TextView) convertView.findViewById(R.id.diaryTextViewHasTag);

        DiaryData diary = listViewItemList.get(position);

        timeTextView.setText(diary.getTime());
        keyTagTextView.setText(diary.getKeyTag());
        hashTagTextView.setText(diary.getHasTagListString());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(DiaryData diary) {
        listViewItemList.add(diary);
    }

    public void clear() {
        listViewItemList.clear();
    }
}