package com.nnm.team91.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TimelineActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 101;
    private FloatingActionButton gotoListFloatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // find all widgets and save
        gotoListFloatBtn = (FloatingActionButton) findViewById(R.id.goto_list_float_btn);

        // set btn listeners
         gotoListFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, ListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }
}
