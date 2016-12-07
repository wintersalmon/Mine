package com.nnm.team91.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nnm.team91.mine.adapter.DiaryAdapater;
import com.nnm.team91.mine.adapter.ExpenseAdapter;
import com.nnm.team91.mine.adapter.TimelineAdapter;
import com.nnm.team91.mine.adapter.TodoAdapter;
import com.nnm.team91.mine.data.DataManager;
import com.nnm.team91.mine.data.DiaryData;
import com.nnm.team91.mine.data.ExpenseData;
import com.nnm.team91.mine.data.TimelineData;
import com.nnm.team91.mine.data.TodoData;
import com.nnm.team91.mine.fragments.DiaryListFragment;
import com.nnm.team91.mine.fragments.ExpenseListFragment;
import com.nnm.team91.mine.fragments.TimelineListFragment;
import com.nnm.team91.mine.fragments.TodoListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoListFragment.OnTodoFragmentInteractionListener, DiaryListFragment.OnDiaryListFragmentInteractionListener, ExpenseListFragment.OnExpenseFragmentInteractionListener, TimelineListFragment.OnTimelineFragmentInteractionListener, DetailTodoActivity.OnDetailActivityInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static AppCompatActivity activity;

    public static final int REQUEST_CODE = 101;
    public static final int REQUEST_TODO_PREV = 1000;
    public static final int REQUEST_TODO_NEXT = 1001;
    public static final int REQUEST_DIARY_PREV = 2000;
    public static final int REQUEST_DIARY_NEXT = 2001;
    public static final int REQUEST_EXPENSE_PREV = 3000;
    public static final int REQUEST_EXPENSE_NEXT = 3001;

    Button DatePickerBtn, searchBtn, plusBtn, settingBtn;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;


    private boolean bTimeline;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter mSectionsTimelinePagerAdapter;
    private FloatingActionButton gotoListFloatBtn;

    private int selectedTodoPosition;
    private TodoData selectedTodo;
    private DiaryData selectedDiary;
    private ExpenseData selectedExpense;

    private DataManager datamanager;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

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

        final FragmentManager fragmentManager = getSupportFragmentManager();

        DatePickerBtn = (Button) findViewById(R.id.datepicker_btn);
        searchBtn = (Button) findViewById(R.id.search_btn);
        plusBtn = (Button) findViewById(R.id.plus_btn);
        settingBtn = (Button) findViewById(R.id.settings_btn);

        DatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogEditView = inflater.inflate(R.layout.dialog_edit_date, null);

                AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this);
                buider.setTitle("날짜 선택"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 메인의 선택된 날짜 변경 & 데이터 리로드
                    }
                });

                buider.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 취소 출력 메세지 변경
                        Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = buider.create();
//                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                // TODO: 2016. 12. 7.  안쓰는 코드 삭제
                // DialogFragment picker = new DatePickerFragment();
                // picker.show(getSupportFragmentManager(), "DatePicker");
                // TODO: http://androidtrainningcenter.blogspot.kr/2012/10/creating-datepicker-using.html
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

        datamanager = new DataManager(MainActivity.this, 1);
        datamanager.updateLoadedData(2016,12,1);
    }

    public void updateTodoData(TodoData todo) {
        datamanager.updateTodo(todo);
    }

    public void deleteTodoData(int id) {
        datamanager.deleteTodo(id);
    }

    public TodoData getSelectedTodo() {
        if (selectedTodo != null && selectedTodo.getId() != 0) {
            return selectedTodo;
        } else {
            return null;
        }
    }

    public void DetailTodo(int position) {
        try {
            selectedTodo = datamanager.getLoadedDataTodo().get(position);
        } catch (NullPointerException e) {
            selectedTodo = null;
        }

        if (selectedTodo != null && selectedTodo.getId() != 0) {
            Intent intent = new Intent(MainActivity.this, DetailTodoActivity.class);

            Bundle b = new Bundle();
            b.putInt("position", position);
            intent.putExtras(b);

            startActivity(intent);
        }
    }

    public void DetailDiary(int position) {
        try {
            selectedDiary = datamanager.getLoadedDataDiary().get(position);
        } catch (NullPointerException e) {
            selectedDiary = null;
        }

        if (selectedDiary != null && selectedDiary.getId() != 0) {
            Intent intent = new Intent(MainActivity.this, DetailDiaryActivity.class);

            Bundle b = new Bundle();
            b.putInt("position", position);
            b.putInt("common_id", selectedDiary.getId());
            b.putString("date", selectedDiary.getDate());
            b.putString("time", selectedDiary.getTime());
            b.putString("contents", selectedDiary.getText());
            b.putString("key_tag", selectedDiary.getKeyTag());
            b.putStringArrayList("hash_tag_list", selectedDiary.getHashTagList());

            intent.putExtras(b);
            startActivity(intent);
        }
    }

    public void DetailExpense(int position) {
        try {
            selectedExpense = datamanager.getLoadedDataExpense().get(position);
        } catch (NullPointerException e) {
            selectedExpense = null;
        }

        if (selectedExpense != null && selectedExpense.getId() != 0) {
            Intent intent = new Intent(MainActivity.this, DetailExpenseActivity.class);

            Bundle b = new Bundle();
            b.putInt("position", position);
            b.putInt("common_id", selectedExpense.getId());
            b.putString("date", selectedExpense.getDate());
            b.putString("time", selectedExpense.getTime());
            b.putInt("amount", selectedExpense.getAmountValue());
            b.putString("key_tag", selectedExpense.getKeyTag());
            b.putStringArrayList("hash_tag_list", selectedExpense.getHashTagList());

            intent.putExtras(b);
            startActivity(intent);
        }
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

    public DataManager getDatamanager() {
        return datamanager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TODO_NEXT) {
            Toast.makeText(MainActivity.this, "REQUEST_TODO_NEXT", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_TODO_PREV) {
            Toast.makeText(MainActivity.this, "REQUEST_TODO_PREV", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_DIARY_NEXT) {
            Toast.makeText(MainActivity.this, "REQUEST_DIARY_NEXT", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_DIARY_PREV) {
            Toast.makeText(MainActivity.this, "REQUEST_DIARY_PREV", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_EXPENSE_NEXT) {
            Toast.makeText(MainActivity.this, "REQUEST_EXPENSE_NEXT", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_DIARY_PREV) {
            Toast.makeText(MainActivity.this, "REQUEST_DIARY_PREV", Toast.LENGTH_SHORT).show();
        }
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
    public void onTimelineFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDiaryFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTodoFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExpenseFragmentInteraction(Uri uri) {

    }

    @Override
    public void updateTimelineAdapter(TimelineAdapter adapter) {
        adapter.clear();
        for (TimelineData data : datamanager.getLoadedDataTimeline()) {
            adapter.addItem(data);
        }
    }

    @Override
    public void updateTodoAdapter(TodoAdapter adapter) {
        adapter.clear();
        for (TodoData data : datamanager.getLoadedDataTodo()) {
            adapter.addItem(data);
        }
    }

    @Override
    public void updateDairyAdapater(DiaryAdapater adapter) {
        adapter.clear();
        for (DiaryData data : datamanager.getLoadedDataDiary()) {
            adapter.addItem(data);
        }
    }

    @Override
    public void updateExpenseAdapter(ExpenseAdapter adapter) {
        adapter.clear();
        for (ExpenseData data : datamanager.getLoadedDataExpense()) {
            adapter.addItem(data);
        }
    }

    @Override
    public TodoData getSelectedTodo(int position) {
        TodoData todo;
        try {
            todo = datamanager.getLoadedDataTodo().get(position);
        } catch (NullPointerException e) {
            todo = null;
        }

        if (todo != null && todo.getId() != 0) {
            return todo;
        } else {
            return null;
        }
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