package com.nnm.team91.mine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tristan on 2016-11-10.
 */

public class ListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    private static final int ITEM_VIEW_TYPE_SEARCH = 0;
    private static final int ITEM_VIEW_TYPE_LIST = 1;
    private static final int ITEM_VIEW_TYPE_MAX = 2;
    String[] items = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    // position 위치의 아이템 타입 리턴.
    @Override
    public int getItemViewType(int position) {
        return listViewItemList.get(position).getType();
    }

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    //Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 지정한 위치에 있는 데이터와 관계된 아이템의 ID를 리턴 : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Data Set(ListViewItemList)에서 position에 위치한 데이터 참조 획득
            ListViewItem listViewItem = listViewItemList.get(position);


            switch (viewType) {
                case ITEM_VIEW_TYPE_SEARCH:
                    convertView = inflater.inflate(R.layout.search_listviewitem, parent, false);
                    // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
                    ImageView imageView = (ImageView) convertView.findViewById(R.id.list_img);
                    TextView textView = (TextView) convertView.findViewById(R.id.list_txt);
                    ImageView crossView = (ImageView) convertView.findViewById(R.id.list_cross);

                    // 아이템 내 각 위젯에 데이터 반영
                    imageView.setImageDrawable(listViewItem.getSharpImg());
                    textView.setText(listViewItem.getTxtStr());
                    crossView.setImageDrawable(listViewItem.getCrossImg());
                    break;
                case ITEM_VIEW_TYPE_LIST:
                    convertView = inflater.inflate(R.layout.search_listviewitem2, parent, false);
                    ImageView searchView = (ImageView) convertView.findViewById(R.id.search_img2);
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id.search_edt2);
//                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));
                    searchView.setImageDrawable(listViewItem.getSearchImg());
                    autoCompleteTextView.setText(listViewItem.getEdtStr());
                    break;
            }
        }
        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능

    public void addItem(Drawable sharp, String txt, Drawable cross) {
        ListViewItem item = new ListViewItem();

        item.setType(ITEM_VIEW_TYPE_SEARCH);
        item.setSharpImg(sharp);
        item.setTxtStr(txt);
        item.setCrossImg(cross);

        listViewItemList.add(item);
    }

    public void addItem(Drawable search, String edt) {
        ListViewItem item = new ListViewItem();

        item.setType(ITEM_VIEW_TYPE_LIST);
        item.setSearchImg(search);
        item.setEdtStr(edt);

        listViewItemList.add(item);
    }
}
