package com.nnm.team91.mine;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * Created by tristan on 2016-11-10.
 */

public class SearchFragment extends ListFragment {

    ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Adapter 생성 및 Adapter 지정.
        adapter = new ListViewAdapter();
        setListAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.search_img), ""); // TODO: image와 edittext의 위치 정렬, 검색할 때 새로운 단어를 띄어야 하기 때문에 Fragment를 씌워야할 것 같은 느낌적인 느낌
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.sharp_img), "이전 검색어", ContextCompat.getDrawable(getActivity(), R.drawable.cross_img));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        Drawable sharp = item.getSharpImg();
        String edt = item.getEdtStr();
        Drawable cross = item.getCrossImg();

        super.onListItemClick(l, v, position, id);
    }
}
