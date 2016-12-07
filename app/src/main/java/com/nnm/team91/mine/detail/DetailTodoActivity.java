package com.nnm.team91.mine.detail;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.edit.EditTodoActivity;

import java.util.ArrayList;

public class DetailTodoActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button editButton;
    private Button deleteButton;


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

        prevButton = (Button) findViewById(R.id.detail_todo_button_prev);
        nextButton = (Button) findViewById(R.id.detail_todo_button_next);
        editButton = (Button) findViewById(R.id.detail_todo_button_edit);
        deleteButton = (Button) findViewById(R.id.detail_todo_button_delete);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add prev function
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add next function
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTodoActivity.this, EditTodoActivity.class);

                Bundle b = new Bundle();
                b.putInt("position", position);
                b.putInt("common_id", commonId);
                b.putString("date", date);
                b.putString("time", time);
                b.putBoolean("status", status);
                b.putString("key_tag", keyTag);
                b.putStringArrayList("hash_tag_list", hashTagList);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016. 12. 7. add delete dialog function
            }
        });

        reloadViewContents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setTodoData();
        setViewData();
    }

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
