package com.nnm.team91.mine.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * DataManager
 */

public class DataManager {
    private int loadYear;
    private int loadMonth;
    private int loadDay;
//    private int loadDayCountFront;
//    private int loadDayCountBack;

    private ArrayList<TimelineData> loadedDataTimeline;
    private ArrayList<TodoData> loadedDataTodo;
    private ArrayList<DiaryData> loadedDataDiary;
    private ArrayList<ExpenseData> loadedDataExpense;

    private MineSQLiteOpenHelper sqliteHelper;

//    public DataManager() {
//        loadedDataTimeline = new ArrayList<TimelineData>();
//        loadedDataTodo = new ArrayList<TodoData>();
//        loadedDataDiary = new ArrayList<DiaryData>();
//        loadedDataExpense = new ArrayList<ExpenseData>();
//
//        sqliteHelper = null;
//
////        this.loadDayCountFront = 10;
////        this.loadDayCountBack = 10;
//    }

    public DataManager(Context context, int version) {
        loadedDataTimeline = new ArrayList<TimelineData>();
        loadedDataTodo = new ArrayList<TodoData>();
        loadedDataDiary = new ArrayList<DiaryData>();
        loadedDataExpense = new ArrayList<ExpenseData>();

        sqliteHelper = new MineSQLiteOpenHelper(context, "mine.db", null, version);
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
        hashtags.add("Todo");
        hashtags.add("Weekly");
        hashtags.add("Happy");
        hashtags.add("VeryVeryLongHashTagForTodo");
        hashtags.add("Energy");

        for (int i=0; i<20; i++) {
            if (i%3 == 0) {
                addEmptyTodo(datetime);
            } else {
                addTodo(datetime, (i%2) == 0 ? true : false, hashtags, i%5);
            }
        }
    }

    private void addTodo(Date datetime, boolean status, ArrayList<String> hastags, int keyTagIndex) {
        TodoData todo = new TodoData();

        todo.setDate(datetime);
        todo.setStatus(status);
        todo.setHashTagList(hastags);
        todo.setKeyTagIndex(keyTagIndex);

        loadedDataTodo.add(todo);
    }

    private void addEmptyTodo(Date datetime) {
        TodoData emptyTodo = new TodoEmptyData();

        emptyTodo.setDate(datetime);

        loadedDataTodo.add(emptyTodo);
    }


    private void updateDiaryData() {
        ArrayList<String> hashtags = new ArrayList<String>();
        hashtags.add("Diary");
        hashtags.add("Monthly");
        hashtags.add("Sad");
        hashtags.add("ThisIsAVeryLongHashTagForDiary");
        hashtags.add("NotHungry");

        String text = "일기의 내용을 아무거나 집어넣어 보자\n" +
                "물은 춤을 추는 뱀이다.\n" +
                "\n" +
                "물을 늘리려고\n" +
                "\n" +
                "손으로 억지로 짓이겨 죽이면,\n" +
                "\n" +
                "흐리게 터진다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "감정 표현이 극과 극인 사람은\n" +
                "\n" +
                "쇠, 매끄러운 가죽이다.\n" +
                "\n" +
                "한 쪽 면은 아주 진하고\n" +
                "\n" +
                "다른 면은 아주 광채가 난다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "나는 레오나르도 다빈치를 닮았다.\n" +
                "\n" +
                "여러 방면의 세계를 맛보는걸 즐기며,\n" +
                "\n" +
                "나보다는 나의 후손들에게 이로운걸 남긴다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "자동차는 오리다.\n" +
                "\n" +
                "꽥꽥거린다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "한국인은 고블린이다.\n" +
                "\n" +
                "생긴거도 고블린이고\n" +
                "\n" +
                "사기치는거도 그 종족과 흡사 하다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "올림픽은 악마의 눈가림이다.\n" +
                "\n" +
                "선수들은 열정을 다 하지만\n" +
                "\n" +
                "결국 남는건 후회일 것이다.";

        Date datetime = Calendar.getInstance().getTime();
        for (int i=0; i<20; i++) {
            if (i%4 == 0) {
                addEmptyDiary(datetime);
            } else {
                addDiary(datetime, text, hashtags, i%5);
            }
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

    private void addEmptyDiary(Date datetime) {
        DiaryEmtpyData emptyDiary = new DiaryEmtpyData();

        emptyDiary.setDate(datetime);

        loadedDataDiary.add(emptyDiary);
    }

    private void updateExpenseData() {
        ArrayList<String> hashtags = new ArrayList<String>();
        hashtags.add("Brunch");
        hashtags.add("Lunch");
        hashtags.add("Dinner");
        hashtags.add("HashTagThatCouldBeQuiteLong");
        hashtags.add("Hello");

        Date datetime = Calendar.getInstance().getTime();
        for (int i=0; i<20; i++) {
            if (i%2 == 0) {
                addEmptyExpense(datetime);
            } else {
                addExpense(datetime, i*1000, hashtags, i%5);
            }
        }
    }

    private void addExpense(Date date, int amount, ArrayList<String> hastags, int keyTagIndex) {
        ExpenseData expense = new ExpenseData();

        expense.setDate(date);
        expense.setAmount(amount);
        expense.setHashTagList(hastags);
        expense.setKeyTagIndex(keyTagIndex);

        loadedDataExpense.add(expense);
    }

    private void addEmptyExpense(Date datetime) {
        ExpenseEmptyData emptyExpense = new ExpenseEmptyData();

        emptyExpense.setDate(datetime);

        loadedDataExpense.add(emptyExpense);
    }

    private void updateTimelineData() {
        for(int i=0; i<getLoadedDataTodo().size(); i++) {
            addTimeline(getLoadedDataTodo().get(i), getLoadedDataDiary().get(i),getLoadedDataExpense().get(i));
        }
    }

    private void addTimeline(TodoData todo, DiaryData diary, ExpenseData expense) {
        TimelineData timeline = new TimelineData();
        timeline.setDate(todo.getRawDateTime());
        timeline.setTodo(todo);
        timeline.setDiary(diary);
        timeline.setExpense(expense);

        loadedDataTimeline.add(timeline);
    }

    public void insertCommon(Date datetime) {

    }

    public void insertHashtag(String tag) {

    }

    public void insertHashtagInCommon(int commonId, int hashtagId) {

    }

    public void insertTodo(Date datetime,  boolean status, ArrayList<String> hastags, int keyTagIndex) {

    }

    public void insertDiary(Date date, String text, ArrayList<String> hastags, int keyTagIndex) {

    }

    public void insertExpense(Date date, int amount, ArrayList<String> hastags, int keyTagIndex) {

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