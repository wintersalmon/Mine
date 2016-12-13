package com.nnm.team91.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

public class MainActivity extends AppCompatActivity implements TodoListFragment.OnTodoFragmentInteractionListener, DiaryListFragment.OnDiaryListFragmentInteractionListener, ExpenseListFragment.OnExpenseFragmentInteractionListener, TimelineListFragment.OnTimelineFragmentInteractionListener, DetailTodoActivity.OnTodoDetailActivityInteractionListener, DetailDiaryActivity.OnDiaryDetailActivityInteractionListener, DetailExpenseActivity.OnExpenseDetailActivityInteractionListener, AddActivity.OnAddActivityInteractionListener {

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

    Button DatePickerBtn, searchBtn, plusBtn, settingBtn;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;


    private boolean bTimeline;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter mSectionsTimelinePagerAdapter;

    private TodoListFragment todoListFragment;
    private DiaryListFragment diaryListFragment;
    private ExpenseListFragment expenseListFragment;
    private TimelineListFragment timelineListFragment;

    private int selectedTodoPosition;
    private int selectedDiaryPosition;
    private int selectedExpensePosition;

    private int selectedPosition;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

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

        todoListFragment = TodoListFragment.newInstance("DUMMY", "DUMMY");
        diaryListFragment = DiaryListFragment.newInstance("DUMMY", "DUMMY");
        expenseListFragment = ExpenseListFragment.newInstance("DUMMY", "DUMMY");
        timelineListFragment = TimelineListFragment.newInstance("DUMMY", "DUMMY");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(todoListFragment, "ToDo");
        mSectionsPagerAdapter.addFragment(diaryListFragment, "Diary");
        mSectionsPagerAdapter.addFragment(expenseListFragment, "Expense");

        mSectionsTimelinePagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsTimelinePagerAdapter.addFragment(timelineListFragment, "Timeline");

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
//                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogEditView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                buider.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016. 12. 7. 메인의 선택된 날짜 변경 & 데이터 리로드
                        DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        int day = datePicker.getDayOfMonth();

                        Log.d("DATA_AFTER", year + "-" + month  + "-" + day);

                        datamanager.updateLoadedData(year,month,day);
                        SectionsPagerAdapter adapter = (SectionsPagerAdapter) mViewPager.getAdapter();
                        int itemPosition = mViewPager.getCurrentItem();
                        adapter.getItem(itemPosition).onResume();
//                        todoListFragment.onResume();
//                        diaryListFragment.onResume();
//                        expenseListFragment.onResume();
//                        timelineListFragment.onResume();
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
                DatePicker datePicker = (DatePicker) dialogEditView.findViewById(R.id.dialog_edit_date_picker);

                int year = datamanager.getLoadYear();
                int month = datamanager.getLoadMonth();
                int day = datamanager.getLoadDay();
                Log.d("DATA_BEFORE", year + "-" + month  + "-" + day);
                datePicker.updateDate(year, month - 1, day);
                dialog.show();
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
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
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

        // TODO: 2016. 12. 12. set current date
        datamanager = new DataManager(MainActivity.this, 1);
        datamanager.updateLoadedData(2016,12,1);

        // TODO: 2016. 12. 12. set current focus position to closest current time
        selectedPosition = 0;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (positionOffset == 0 && positionOffsetPixels == 0) {
//                    timelineListFragment.setCurrentPosition(getSelectedPosition());
//                    todoListFragment.setCurrentPosition(getSelectedPosition());
//                    diaryListFragment.setCurrentPosition(getSelectedPosition());
//                    expenseListFragment.setCurrentPosition(getSelectedPosition());
//
//                    timelineListFragment.setScrollTop(true);
//                }
//                Log.d("SCROLLED", ":" + position + "," + positionOffset + ", " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("SELECTED", ":" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private TodoData findTodoWithPosition(int position) {
        TodoData todo;
        try {
            todo = datamanager.getLoadedDataTodo().get(position);
        } catch (NullPointerException e) {
            todo = null;
        }

        if (todo != null && todo.getId() != 0) {
            selectedTodoPosition = position;
            return todo;
        } else {
            return null;
        }
    }

    private DiaryData findDiaryWithPosition(int position) {
        DiaryData diary;
        try {
            diary = datamanager.getLoadedDataDiary().get(position);
        } catch (NullPointerException e) {
            diary = null;
        }

        if (diary != null && diary.getId() != 0) {
            selectedDiaryPosition = position;
            return diary;
        } else {
            return null;
        }
    }

    private ExpenseData findExpenseWithPosition(int position) {
        ExpenseData expense;
        try {
            expense = datamanager.getLoadedDataExpense().get(position);
        } catch (NullPointerException e) {
            expense = null;
        }

        if (expense != null && expense.getId() != 0) {
            selectedExpensePosition = position;
            return expense;
        } else {
            return null;
        }
    }

    public void DetailTodo(int position) {
        TodoData todo = findTodoWithPosition(position);
        if (todo != null) {
            selectedTodo = todo;
            selectedTodoPosition = position;

            Intent intent = new Intent(MainActivity.this, DetailTodoActivity.class);
            startActivity(intent);
        }
    }

    public void DetailDiary(int position) {
        DiaryData diary = findDiaryWithPosition(position);
        if (diary != null) {
            selectedDiary = diary;
            selectedDiaryPosition = position;

            Intent intent = new Intent(MainActivity.this, DetailDiaryActivity.class);
            startActivity(intent);
        }
    }

    public void DetailExpense(int position) {
        ExpenseData expense = findExpenseWithPosition(position);
        if (expense != null) {
            selectedExpense = expense;
            selectedExpensePosition = position;

            Intent intent = new Intent(MainActivity.this, DetailExpenseActivity.class);
            startActivity(intent);
        }
    }

    public void ChangePageMode() {
        if (bTimeline) {
            timelineListFragment.setCurrentPosition(getSelectedPosition());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
            bTimeline = false;
        } else {
            setSelectedPosition(timelineListFragment.getCurrentPosition());
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
        if (timelineListFragment.getAdapter() != null)
            timelineListFragment.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateTodoAdapter(TodoAdapter adapter) {
        adapter.clear();
        for (TodoData data : datamanager.getLoadedDataTodo()) {
            adapter.addItem(data);
        }
        if (todoListFragment.getAdapter() != null)
            todoListFragment.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateDairyAdapater(DiaryAdapater adapter) {
        adapter.clear();
        for (DiaryData data : datamanager.getLoadedDataDiary()) {
            adapter.addItem(data);
        }
        if (diaryListFragment.getAdapter() != null)
            diaryListFragment.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateExpenseAdapter(ExpenseAdapter adapter) {
        adapter.clear();
        for (ExpenseData data : datamanager.getLoadedDataExpense()) {
            adapter.addItem(data);
        }
        if (expenseListFragment.getAdapter() != null)
            expenseListFragment.getAdapter().notifyDataSetChanged();
    }


    /*
     *
     * Override OnTodoDetailActivityInteractionListener
     *
     */
    @Override
    public TodoData getSelectedTodo() {
        if (selectedTodo != null && selectedTodo.getId() != 0) {
            return selectedTodo;
        } else {
            return null;
        }
    }

    @Override
    public TodoData findPrevTodo() {
        for (int position = selectedTodoPosition - 1; position >= 0; position--) {
            TodoData todo = findTodoWithPosition(position);
            if (todo != null && todo.getId() != 0) {
                selectedTodo = todo;
                selectedTodoPosition = position;
                return selectedTodo;
            }
        }
        return null;
    }

    @Override
    public TodoData findNextTodo() {
        for (int position = selectedTodoPosition + 1; position < datamanager.getLoadedDataTodo().size(); position++) {
            TodoData todo = findTodoWithPosition(position);
            if (todo != null && todo.getId() != 0) {
                selectedTodo = todo;
                selectedTodoPosition = position;
                return selectedTodo;
            }
        }
        return null;
    }

    @Override
    public void updateTodoData(TodoData todo) {
        datamanager.updateTodo(todo);
    }

    @Override
    public void updateTodoDataStatus(TodoData todo) {
        datamanager.updateTodoStatus(todo);
    }

    @Override
    public void deleteTodoData(TodoData todo) {
        datamanager.deleteTodo(todo.getId());
        selectedTodo = null;
    }


    /*
     *
     * Override OnDiaryDetailActivityInteractionListener
     *
     */
    @Override
    public DiaryData getSelectedDiary() {
        if (selectedDiary != null && selectedDiary.getId() != 0) {
            return selectedDiary;
        } else {
            return null;
        }
    }

    @Override
    public DiaryData findPrevDiary() {
        for (int position = selectedDiaryPosition - 1; position >= 0; position--) {
            DiaryData diary = findDiaryWithPosition(position);
            if (diary != null && diary.getId() != 0) {
                selectedDiary = diary;
                selectedDiaryPosition = position;
                return selectedDiary;
            }
        }
        return null;
    }

    @Override
    public DiaryData findNextDiary() {
        for (int position = selectedDiaryPosition + 1; position < datamanager.getLoadedDataDiary().size(); position++) {
            DiaryData diary = findDiaryWithPosition(position);
            if (diary != null && diary.getId() != 0) {
                selectedDiary = diary;
                selectedDiaryPosition = position;
                return selectedDiary;
            }
        }
        return null;
    }

    @Override
    public void updateDiaryData(DiaryData diary) {
        datamanager.updateDiary(diary);
    }

    @Override
    public void deleteDiaryData(DiaryData diary) {
        datamanager.deleteDiary(diary.getId());
        selectedDiary = null;
    }

    /*
     *
     * Override OnExpenseDetailActivityInteractionListener Override
     *
     */
    @Override
    public ExpenseData getSelectedExpense() {
        if (selectedExpense != null && selectedExpense.getId() != 0) {
            return selectedExpense;
        } else {
            return null;
        }
    }

    @Override
    public ExpenseData findPrevExpense() {
        for (int position = selectedExpensePosition - 1; position >= 0; position--) {
            ExpenseData expense = findExpenseWithPosition(position);
            if (expense != null && expense.getId() != 0) {
                selectedExpense = expense;
                selectedExpensePosition = position;
                return selectedExpense;
            }
        }
        return null;
    }

    @Override
    public ExpenseData findNextExpense() {
        for (int position = selectedExpensePosition + 1; position < datamanager.getLoadedDataExpense().size(); position++) {
            ExpenseData expense = findExpenseWithPosition(position);
            if (expense != null && expense.getId() != 0) {
                selectedExpense = expense;
                selectedExpensePosition = position;
                return selectedExpense;
            }
        }
        return null;
    }

    @Override
    public void updateExpenseData(ExpenseData expense) {
        datamanager.updateExpense(expense);
    }

    @Override
    public void deleteExpenseData(ExpenseData expense) {
        datamanager.deleteExpense(expense.getId());
        selectedExpense = null;
    }

    @Override
    public ArrayList<String> loadTopHashTag(int size) {
        return null;
    }

    @Override
    public void insertTodoData(String datetimeStr, int status, ArrayList<String> hashtags, int keyTagIndex) {
        datamanager.insertTodo(datetimeStr,status,hashtags,keyTagIndex);
    }

    @Override
    public void insertDiaryData(String datetimeStr, String contents, ArrayList<String> hashtags, int keyTagIndex) {
        datamanager.insertDiary(datetimeStr,contents,hashtags,keyTagIndex);
    }

    @Override
    public void insertExpenseData(String datetimeStr, int amount, ArrayList<String> hashtags, int keyTagIndex) {
        datamanager.insertExpense(datetimeStr,amount,hashtags,keyTagIndex);
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

        public Fragment getFragment(int position) {
            if (position < 0 || position > mFragmentList.size())
                return null;
            return mFragmentList.get(position);
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