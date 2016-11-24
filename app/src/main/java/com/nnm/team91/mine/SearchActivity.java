package com.nnm.team91.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    ActionBar actionBar;
    FragmentManager fragmentManager;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        actionBar = getSupportActionBar();
        actionBar.hide();

        fragmentManager = getSupportFragmentManager();
        searchFragment = (SearchFragment) fragmentManager.findFragmentById(R.id.search_fm);
    }
}
