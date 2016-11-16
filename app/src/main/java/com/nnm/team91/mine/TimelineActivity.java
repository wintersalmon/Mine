package com.nnm.team91.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TimelineActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 101;

    private Button gotoTodoBtn, gotoDiaryBtn, gotoExpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // find all widgets and save
        gotoTodoBtn = (Button) findViewById(R.id.debug_goto_todo_btn);
        gotoDiaryBtn = (Button) findViewById(R.id.debug_goto_diary_btn);
        gotoExpenseBtn = (Button) findViewById(R.id.debug_goto_expense_btn);

        // set btn listeners
        gotoTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, ListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        gotoDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, ListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        gotoExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, ListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


}
