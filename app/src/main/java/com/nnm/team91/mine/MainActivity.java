package com.nnm.team91.mine;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nnm.team91.mine.fragments.DiaryListFragment;
import com.nnm.team91.mine.fragments.ExpenseListFragment;
import com.nnm.team91.mine.fragments.TimelineListFragment;
import com.nnm.team91.mine.fragments.TodoListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoListFragment.OnFragmentInteractionListener, DiaryListFragment.OnFragmentInteractionListener, ExpenseListFragment.OnFragmentInteractionListener, TimelineListFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static final int REQUEST_CODE = 101;
    Button DatePickerBtn, searchBtn, plusBtn, settingBtn;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;


    private boolean bTimeline;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter mSectionsTimelinePagerAdapter;
    private FloatingActionButton gotoListFloatBtn;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new TodoListFragment(), "ToDo");
        mSectionsPagerAdapter.addFragment(new DiaryListFragment(), "Diary");
        mSectionsPagerAdapter.addFragment(new ExpenseListFragment(), "Expense");

        mSectionsTimelinePagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsTimelinePagerAdapter.addFragment(new TimelineListFragment(), "Timeline");

        // Set into timeline mode
        bTimeline = true;


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsTimelinePagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // find all widgets and save
        gotoListFloatBtn = (FloatingActionButton) findViewById(R.id.goto_list_float_btn);

        // set btn listeners
        gotoListFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePageMode();
            }
        });


        final FragmentManager fragmentManager = getSupportFragmentManager();

        DatePickerBtn = (Button) findViewById(R.id.datepicker_btn);
        searchBtn = (Button) findViewById(R.id.search_btn);
        plusBtn = (Button) findViewById(R.id.plus_btn);
        settingBtn = (Button) findViewById(R.id.settings_btn);

        DatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO: 캘린더 버튼 클릭시 년/월/일 상자가 뜰 수 있도록 수정
                DialogFragment picker = new DatePickerFragment();
                picker.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

    }

    public void ChangePageMode() {
        mViewPager.setAdapter(null);
        if (bTimeline) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
            bTimeline = false;
        } else {
            mViewPager.setAdapter(mSectionsTimelinePagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
            bTimeline = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // BackKey Event
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (bTimeline) {
            if(0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
//            Toast.makeText(getApplicationContext(), "'뒤로'버튼을 한 번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), R.string.two_time_back, Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.WHITE);
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.DKGRAY);
                snackbar.show();
            }
        } else {
            ChangePageMode();
        }
    }
}