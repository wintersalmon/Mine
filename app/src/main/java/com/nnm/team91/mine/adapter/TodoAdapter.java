package com.nnm.team91.mine.adapter;

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
import com.nnm.team91.mine.data.TodoData;
import com.nnm.team91.mine.data.TodoEmptyData;

import java.util.ArrayList;

/**
 * Created by wintersalmon on 2016. 11. 25..
 * TodoAdapter
 */

public class TodoAdapter  extends BaseAdapter {
    private ArrayList<TodoData> todoItemList = new ArrayList<TodoData>() ;

    public TodoAdapter() {

    }

    @Override
    public int getCount() {
        return todoItemList.size() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_todo, parent, false);
        }

        TextView datetimeView = (TextView) convertView.findViewById(R.id.todoTextViewTime);
        final CheckBox checkboxView = (CheckBox) convertView.findViewById(R.id.todoCheckBoxStatus);
        TextView keyTagView = (TextView) convertView.findViewById(R.id.todoTextViewKeyTag);
        TextView hashTagView = (TextView) convertView.findViewById(R.id.todoTextViewHasTag);

        TodoData todoItem = todoItemList.get(position);

        datetimeView.setText(todoItem.getTime());

        checkboxView.setChecked(todoItem.getStatus());
        if (todoItem instanceof TodoEmptyData) {
            checkboxView.setVisibility(View.INVISIBLE);
        } else {
            checkboxView.setVisibility(View.VISIBLE);
            checkboxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        keyTagView.setText(todoItem.getKeyTag());
        hashTagView.setText(todoItem.getHasTagListString());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return todoItemList.get(position) ;
    }

    public void addItem(TodoData todo) {
        todoItemList.add(todo);
    }

    public void clear() {
        todoItemList.clear();
    }
}