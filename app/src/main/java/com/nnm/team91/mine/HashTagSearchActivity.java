package com.nnm.team91.mine;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nnm.team91.mine.adapter.HashTagAdapter;
import com.nnm.team91.mine.data.DataManager;
import com.nnm.team91.mine.data.HashTagData;
import com.nnm.team91.mine.fragments.HashTagListFragment;

import java.util.ArrayList;

public class HashTagSearchActivity extends AppCompatActivity implements HashTagListFragment.OnFragmentInteractionListener {

    private DataManager datamanager;

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_search);

        final EditText searchBar = (EditText) findViewById(R.id.hash_tag_search_bar);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Editable editable = searchBar.getText();
                    keyword = editable.toString();

                    HashTagListFragment listFragment = (HashTagListFragment) getFragmentManager().findFragmentById(R.id.fragment_hash_tag_list);
                    ArrayList<HashTagData> tagList = datamanager.searchHashTag(keyword);
                    listFragment.updateHashTagAdapter(tagList);

//                    Toast.makeText(getApplicationContext(), keyword, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        MainActivity main = (MainActivity) MainActivity.activity;
        datamanager = main.getDatamanager();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//
//    @Override
//    public void updateHashTagAdapter(HashTagAdapter adapter) {
//        if (keyword != null && keyword.length() > 0) {
//            adapter.clear();
//            for (HashTagData hashTag : datamanager.searchHashTag(keyword)) {
//                adapter.addItem(hashTag);
//            }
//        }
//    }
}
