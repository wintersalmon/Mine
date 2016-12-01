package com.nnm.team91.mine.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 */

public class DataManager {
    private int loadYear;
    private int loadMonth;
    private int loadDay;
    private int loadDayCountFront;
    private int loadDayCountBack;

    private ArrayList<TimelineData> loadedDataTimeline;
    private ArrayList<TodoData> loadedDataTodo;
    private ArrayList<DiaryData> loadedDataDiary;
    private ArrayList<ExpenseData> loadedDataExpense;

    public DataManager() {
        loadedDataTimeline = new ArrayList<TimelineData>();
        loadedDataTodo = new ArrayList<TodoData>();
        loadedDataDiary = new ArrayList<DiaryData>();
        loadedDataExpense = new ArrayList<ExpenseData>();

        this.loadDayCountFront = 10;
        this.loadDayCountBack = 10;
    }

    public void updateLoadedData(int year, int month, int day) {
        // TODO : Verify year month day
        this.loadYear = year;
        this.loadMonth = month;
        this.loadDay = day;

        updateTodoData();
        updateDiaryData();
        updateExpenseData();
        updateTimelineData();
    }

    private void updateTodoData() {
        loadedDataTodo.clear();

        // create common data
        Date datetime = Calendar.getInstance().getTime();
        ArrayList<String> hashtags = new ArrayList<String>();
        hashtags.add("TodoHappy");
        hashtags.add("TodoHalloween");
        hashtags.add("TodoOctober");

        for (int i=0; i<20; i++) {
            addTodo(datetime, (i%2) == 0 ? true : false, i + "제목", i + "상세한 설명", hashtags, i%3);
        }
    }

    private void addTodo(Date datetime, boolean status, String title, String desc, ArrayList<String> hastags, int keyTagIndex) {
        TodoData todo = new TodoData();

        todo.setDate(datetime);
        todo.setStatus(status);
        todo.setTitle(title);
        todo.setDescrition(desc);
        todo.setHashTagList(hastags);
        todo.setKeyTagIndex(keyTagIndex);

        loadedDataTodo.add(todo);
    }


    private void updateDiaryData() {
        ArrayList<String> hashtags = new ArrayList<String>();
        hashtags.add("DiaryHappy");
        hashtags.add("DiaryHalloween");
        hashtags.add("DiaryOctober");
        Date datetime = Calendar.getInstance().getTime();
        for (int i=0; i<20; i++) {
            addDiary(datetime, i + "일기 내용의 첫부분이 여기에 들어간다....", hashtags, i%3);
        }
    }

    private void addDiary(Date date, String text, ArrayList<String> hastags, int keyTagIndex) {
        DiaryData diary = new DiaryData();

        diary.setDate(date);
        diary.setText(text);
        diary.setHashTagList(hastags);
        diary.setKeyTagIndex(keyTagIndex);

        loadedDataDiary.add(diary);
    }

    private void updateExpenseData() {
        ArrayList<String> hashtags = new ArrayList<String>();

        hashtags.add("ExpenseHappy");
        hashtags.add("ExpenseHalloween");
        hashtags.add("ExpenseOctober");
        Date datetime = Calendar.getInstance().getTime();
        for (int i=0; i<20; i++) {
            addExpense(datetime, i*1000, "사용 목적", hashtags, i%3);
        }
    }

    private void addExpense(Date date, int amount, String usage, ArrayList<String> hastags, int keyTagIndex) {
        ExpenseData expense = new ExpenseData();

        expense.setDate(date);
        expense.setAmount(amount);
        expense.setUsage(usage);
        expense.setHashTagList(hastags);
        expense.setKeyTagIndex(keyTagIndex);

        loadedDataExpense.add(expense);
    }

    private void updateTimelineData() {

    }

    public ArrayList<TimelineData> getLoadedDataTimeline() {
        return loadedDataTimeline;
    }

    public ArrayList<TodoData> getLoadedDataTodo() {
        return loadedDataTodo;
    }

    public ArrayList<DiaryData> getLoadedDataDiary() {
        return loadedDataDiary;
    }

    public ArrayList<ExpenseData> getLoadedDataExpense() {
        return loadedDataExpense;
    }

    public int getLoadDay() {
        return loadDay;
    }

    public int getLoadMonth() {
        return loadMonth;
    }

    public int getLoadYear() {
        return loadYear;
    }
}