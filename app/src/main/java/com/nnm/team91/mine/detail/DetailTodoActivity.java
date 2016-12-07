package com.nnm.team91.mine.detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.TodoData;

import java.util.ArrayList;

public class DetailTodoActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button EditButton;
    private Button deleteButton;
    private FloatingActionButton refreshFloatButton;


    private TextView dateTextView;
    private TextView timeTextView;
    private CheckBox statusCheckBox;
    private TextView keyTextView;
    private TextView hashTextView;

    private int position;
    private int commonId;
    private String date;
    private String time;
    private boolean status;
    private String keyTag;
    private String hashTagString;
    private ArrayList<String> hashTagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);

        dateTextView = (TextView) findViewById(R.id.detail_todo_date);
        timeTextView = (TextView) findViewById(R.id.detail_todo_time);
        statusCheckBox = (CheckBox) findViewById(R.id.detail_todo_status);
        keyTextView = (TextView) findViewById(R.id.detail_todo_key_tag);
        hashTextView = (TextView) findViewById(R.id.detail_todo_hash_tag);

        refreshFloatButton = (FloatingActionButton) findViewById(R.id.todo_detail_float_btn);
        refreshFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                reloadContent();
            }
        });

        reloadViewContents();
    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        setTodoData();
//        setViewData();
//    }

    private void reloadViewContents() {
        setTodoData();
        setViewData();
    }

    public void setTodoData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            position = b.getInt("position");
            commonId = b.getInt("common_id");
            date = b.getString("date");
            time = b.getString("time");
            status = b.getBoolean("status");
            keyTag = b.getString("key_tag");
            hashTagList = b.getStringArrayList("hash_tag_list");
        }
    }

    private void setViewData() {
        dateTextView.setText(date);
        timeTextView.setText(time);
        statusCheckBox.setChecked(status);
        keyTextView.setText(keyTag);

        hashTagString = "";
        for(String s : hashTagList) {
            hashTagString += s + " ";
        }
        hashTextView.setText(hashTagString);
    }
}
