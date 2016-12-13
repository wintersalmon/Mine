package com.nnm.team91.mine.adapter;

/**
 * Created by wintersalmon on 2016. 11. 25..
 * TimelineAdapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.DataManager;
import com.nnm.team91.mine.data.TimelineData;
import com.nnm.team91.mine.data.TodoData;
import com.nnm.team91.mine.data.TodoEmptyData;

import java.util.ArrayList;

public class TimelineAdapter extends BaseAdapter {
    private ArrayList<TimelineData> listViewItemList = new ArrayList<TimelineData>() ;

    public TimelineAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_timeline, parent, false);
        }

        TextView timeTextView = (TextView) convertView.findViewById(R.id.timelineTimeTextView);
        CheckBox todoCheckbox = (CheckBox) convertView.findViewById(R.id.timelineTodoCheckbox);
        TextView todoTextView = (TextView) convertView.findViewById(R.id.timelineTodoTextView);
        TextView diaryTextView = (TextView) convertView.findViewById(R.id.timelineDiaryTextView);
        TextView expenseAmountTextView = (TextView) convertView.findViewById(R.id.timelineExpenseAmount);
        TextView expenseTextView = (TextView) convertView.findViewById(R.id.timelineExpenseTextView);

        TimelineData timeline = listViewItemList.get(position);

        timeTextView.setText(timeline.getTime());
        todoCheckbox.setChecked(timeline.getTodoStatus());
        todoTextView.setText(timeline.getTodoHashTag());
        diaryTextView.setText(timeline.getDiaryHashTag());
        expenseAmountTextView.setText(timeline.getExpenseAmount());
        expenseTextView.setText(timeline.getExpenseHashTag());

        if (timeline.getTodo() instanceof TodoEmptyData) {
            todoCheckbox.setVisibility(View.INVISIBLE);
        } else {
            todoCheckbox.setVisibility(View.VISIBLE);
            todoCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MainActivity main = (MainActivity) MainActivity.activity;
                    DataManager datamanager = main.getDatamanager();
                    TodoData todo = datamanager.getLoadedDataTodo().get(position);
                    todo.setStatus(isChecked);
                    datamanager.updateTodoStatus(todo);
                }
            });
        }

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

    public void addItem(TimelineData timeline) {
        listViewItemList.add(timeline);
    }

    public void clear() {
        listViewItemList.clear();
    }
}