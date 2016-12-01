package com.nnm.team91.mine.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.DiaryData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wintersalmon on 2016. 11. 25..
 */

public class DiaryAdapater extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<DiaryData> listViewItemList = new ArrayList<DiaryData>() ;

    // TimelineAdapter의 생성자
    public DiaryAdapater() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_diary, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView timeTextView = (TextView) convertView.findViewById(R.id.diaryTextViewTime);
        TextView textTextView = (TextView) convertView.findViewById(R.id.diaryTextViewText);
        TextView hashtagTextView = (TextView) convertView.findViewById(R.id.diaryTextViewHastag);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DiaryData diary = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        timeTextView.setText(diary.getTime());
        textTextView.setText(diary.getText());
        hashtagTextView.setText(diary.getHastagList());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(DiaryData diary) {
        listViewItemList.add(diary);
    }

    public void clear() {
        listViewItemList.clear();
    }
}