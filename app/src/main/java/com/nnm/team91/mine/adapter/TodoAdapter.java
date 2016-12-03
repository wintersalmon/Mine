package com.nnm.team91.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.TodoData;
import com.nnm.team91.mine.data.TodoEmptyData;

import java.util.ArrayList;

/**
 * Created by wintersalmon on 2016. 11. 25..
 * TodoAdapter
 */

public class TodoAdapter  extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<TodoData> todoItemList = new ArrayList<TodoData>() ;

    // TimelineAdapter의 생성자
    public TodoAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return todoItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_todo, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView datetimeView = (TextView) convertView.findViewById(R.id.todoTextViewTime);
        CheckBox checkboxView = (CheckBox) convertView.findViewById(R.id.todoCheckBoxStatus);
        TextView keyTagView = (TextView) convertView.findViewById(R.id.todoTextViewKeyTag);
        TextView hashTagView = (TextView) convertView.findViewById(R.id.todoTextViewHasTag);

        // Data Set(todoItemList)에서 position에 위치한 데이터 참조 획득
        TodoData todoItem = todoItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        datetimeView.setText(todoItem.getTime());

        checkboxView.setChecked(todoItem.getStatus());
        // set checkbox hidden if it's data is empty
        if (todoItem instanceof TodoEmptyData) {
            checkboxView.setVisibility(View.INVISIBLE);
        } else {
            checkboxView.setVisibility(View.VISIBLE);
        }

        keyTagView.setText(todoItem.getKeyTag());
        hashTagView.setText(todoItem.getHastagList());
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
        return todoItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(TodoData todo) {
        todoItemList.add(todo);
    }

    public void clear() {
        todoItemList.clear();
    }
}