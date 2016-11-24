package com.nnm.team91.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    // TODO: ListActivity에 TimelineActivity에서 클릭한 fragment를 띄어줄 것이다, 각각 클릭한 프래그먼트를 ListActivity에 띄어줘야 한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
