package com.nnm.team91.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.HashTagData;

import java.util.ArrayList;

/**
 * Created by wintersalmon on 2016. 12. 13..
 * HashTagAdapter
 */

public class HashTagAdapter extends BaseAdapter {
    private ArrayList<HashTagData> hashTagItemList = new ArrayList<HashTagData>();

    public HashTagAdapter() {
    }
    @Override
    public int getCount() {
        return hashTagItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return hashTagItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_hash_tag, parent, false);
        }

        TextView hashTagId = (TextView) convertView.findViewById(R.id.hash_tag_text_view_id);
        TextView hashTagCommonId = (TextView) convertView.findViewById(R.id.hash_tag_text_view_common_id);
        TextView hashTagString = (TextView) convertView.findViewById(R.id.hash_tag_text_view_tag);

        HashTagData hashTag = hashTagItemList.get(position);

        hashTagId.setText(String.valueOf(hashTag.getId()));
        hashTagCommonId.setText(String.valueOf(hashTag.getCommonId()));
        hashTagString.setText(hashTag.getHashTag());

        return convertView;
    }

    public void addItem(HashTagData hashTag) {
        hashTagItemList.add(hashTag);
    }

    public void clear() {
        hashTagItemList.clear();
    }
}
