package com.nnm.team91.mine.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.nnm.team91.mine.R;

public class DetailTodoActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button EditButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);

        TextView dateTextView = (TextView) findViewById(R.id.detail_todo_date);
        TextView timeTextView = (TextView) findViewById(R.id.detail_todo_time);
        CheckBox statusCheckBox = (CheckBox) findViewById(R.id.detail_todo_status);
        TextView keyTextView = (TextView) findViewById(R.id.detail_todo_key_tag);
        TextView hashTextView = (TextView) findViewById(R.id.detail_todo_hash_tag);

        Bundle b = getIntent().getExtras();
    }
}
