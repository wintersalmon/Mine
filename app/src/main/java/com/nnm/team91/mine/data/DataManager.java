package com.nnm.team91.mine.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * DataManager
 */
// TODO: 2016-12-04 remove Logs from db connection methods
public class DataManager {
    static private DateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private int loadYear;
    private int loadMonth;
    private int loadDay;

    private ArrayList<TimelineData> loadedDataTimeline;
    private ArrayList<TodoData> loadedDataTodo;
    private ArrayList<DiaryData> loadedDataDiary;
    private ArrayList<ExpenseData> loadedDataExpense;

    private SQLiteDatabase db;
    private MineSQLiteOpenHelper helper;

    public DataManager(Context context, int version) {
        loadedDataTimeline = new ArrayList<TimelineData>();
        loadedDataTodo = new ArrayList<TodoData>();
        loadedDataDiary = new ArrayList<DiaryData>();
        loadedDataExpense = new ArrayList<ExpenseData>();

        helper = new MineSQLiteOpenHelper(context, "mine.db", null, version);
        // TODO: 2016-12-04 remove unused dummy insert statements
//        insertDummyTodoData();
//        insertDummyDiaryData();
//        insertDummyExpenseData();
//        context.deleteDatabase("mine.db");
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
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectTodo();
    }

    private void addTodo(Date datetime, int status, ArrayList<String> hastags, String keyTag) {
        TodoData todo = new TodoData();

        todo.setDate(datetime);
        if (status == 1) todo.setStatus(true);
        else todo.setStatus(false);
        todo.setHashTagList(hastags);
        todo.setKeyTag(keyTag);

        loadedDataTodo.add(todo);
    }

    private void addEmptyTodo(Date datetime) {
        TodoData emptyTodo = new TodoEmptyData();

        emptyTodo.setDate(datetime);

        loadedDataTodo.add(emptyTodo);
    }

    private void updateDiaryData() {
        loadedDataDiary.clear();
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectDiary();
    }


    private void addDiary(Date date, String text, ArrayList<String> hastags, String keyTag) {
        DiaryData diary = new DiaryData();

        diary.setDate(date);
        diary.setText(text);
        diary.setHashTagList(hastags);
        diary.setKeyTag(keyTag);

        loadedDataDiary.add(diary);
    }

    private void addEmptyDiary(Date datetime) {
        DiaryEmtpyData emptyDiary = new DiaryEmtpyData();

        emptyDiary.setDate(datetime);

        loadedDataDiary.add(emptyDiary);
    }

    private void updateExpenseData() {
        loadedDataExpense.clear();
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectExpense();
    }



    private void addExpense(Date date, int amount, ArrayList<String> hastags, String keyTag) {
        ExpenseData expense = new ExpenseData();

        expense.setDate(date);
        expense.setAmount(amount);
        expense.setHashTagList(hastags);
        expense.setKeyTag(keyTag);

        loadedDataExpense.add(expense);
    }

    private void addEmptyExpense(Date datetime) {
        ExpenseEmptyData emptyExpense = new ExpenseEmptyData();

        emptyExpense.setDate(datetime);

        loadedDataExpense.add(emptyExpense);
    }

    private void updateTimelineData() {
        TodoData todo = null;
        DiaryData diary = null;
        ExpenseData expense = null;
        TodoEmptyData emptyTodo = null;
        DiaryEmtpyData emptyDiary = null;
        ExpenseEmptyData expenseEmpty = null;
        Date datetime = null;
        int todoIdx = 0, todoCount = getLoadedDataTodo().size();
        int diaryIdx = 0, diaryCount = getLoadedDataDiary().size();
        int expenseIdx = 0, expenseCount = getLoadedDataExpense().size();

        while (todoIdx < todoCount ||
                diaryIdx < diaryCount ||
                expenseIdx < expenseCount) {
            if (todoIdx < todoCount) {
                todo = getLoadedDataTodo().get(todoIdx++);
                datetime = todo.getRawDateTime();
                emptyTodo = null;
            } else {
                emptyTodo = new TodoEmptyData();
                todo = emptyTodo;
            }

            if (diaryIdx < diaryCount) {
                diary = getLoadedDataDiary().get(diaryIdx++);
                datetime = diary.getRawDateTime();
                emptyDiary = null;
            } else {
                emptyDiary = new DiaryEmtpyData();
                diary = emptyDiary;
            }

            if (expenseIdx < expenseCount) {
                expense = getLoadedDataExpense().get(expenseIdx++);
                datetime = expense.getRawDateTime();
                expenseEmpty = null;
            } else {
                expenseEmpty = new ExpenseEmptyData();
                expense = expenseEmpty;
            }

            if (emptyTodo != null) emptyTodo.setDate(datetime);
            if (emptyDiary != null) emptyDiary.setDate(datetime);
            if (expenseEmpty != null) expenseEmpty.setDate(datetime);

            addTimeline(todo,diary,expense);
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

    private long insertHashtag(String tag) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tag", tag);

        return db.insert("hashtag", null, values);
    }

    private long insertCommon(Date datetime) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String datetimeStr = datetimeFormat.format(datetime);

        values.put("datetime", datetimeStr);

        return db.insert("common", null, values);
    }

    private void insertHashtagInCommon(long common_id, ArrayList<String> hashtags, int keyTagIndex) {
        if (hashtags != null) {
            for (String tag : hashtags) {
                long hashtag_id = insertHashtag(tag);
                int bIsKeyTag = 0;
                if (keyTagIndex >= 0 &&
                    keyTagIndex < hashtags.size() &&
                    tag.equals(hashtags.get(keyTagIndex))) {
                    bIsKeyTag = 1;
                }
                insertHashtagInCommon(common_id,hashtag_id,bIsKeyTag);
            }
        }
    }

    private void insertHashtagInCommon(long commonId, long hashtagId, int isKeyTag) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("common_id", commonId);
        values.put("hashtag_id", hashtagId);
        values.put("is_key_tag", isKeyTag);

        db.insert("hashtag_in_common", null, values);
    }

    public void insertTodo(Date datetime, int status, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetime);

        values.put("status", status);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("todo", null, values);
    }

    public void insertDiary(Date datetime, String contents, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetime);

        values.put("contents", contents);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("diary", null, values);
    }

    public void insertExpense(Date datetime, int amount, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetime);

        values.put("amount", amount);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("expense", null, values);
    }

    private String selectDatetimeFromCommon(int _id) {
        String datetime = "";
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT datetime FROM common WHERE _id = '" + String.valueOf(_id) + "'",null);

        if (c != null) {
            c.moveToFirst();
            Log.i("db_select_common","success id:" + String.valueOf(_id));
            datetime = c.getString(c.getColumnIndex("datetime"));
        }

        c.close();
        return datetime;
    }

    private ArrayList<String> selectHashtagInCommon(int common_id) {
        ArrayList<String> hashtags = new ArrayList<String>();
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM hashtag_in_common LEFT OUTER JOIN hashtag ON hashtag_in_common.hashtag_id = hashtag._id where common_id = '" + common_id + "'",null);

        while (c.moveToNext()) {
            String tag = c.getString(c.getColumnIndex("tag"));
            hashtags.add(tag);
        }

        return hashtags;
    }

    private String selectKeyTagInCommon(int common_id) {
        String keyTag = "";
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM hashtag_in_common LEFT OUTER JOIN hashtag ON hashtag_in_common.hashtag_id = hashtag._id where common_id = '" + common_id + "'",null);

        while (c.moveToNext()) {
            String tag = c.getString(c.getColumnIndex("tag"));
            int is_key_tag = c.getInt(c.getColumnIndex("is_key_tag"));
            if (is_key_tag == 1) {
                keyTag = tag;
                break;
            }
        }
        return keyTag;
    }

    public void selectTodo() {
        TodoData todo = null;
        db = helper.getReadableDatabase();
        Cursor c = db.query("todo", null, null, null, null, null, null);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int status = c.getInt(c.getColumnIndex("status"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", "err");
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addTodo(datetime,status,hashtags,keyTag);

            Log.i("db_select_todo", "datetime:" + datetimeStr + "id:" + _id + ", status:" + status + ", common_id:" + common_id);
            Log.i("db_select_todo_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectDiary() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("diary",null,null,null,null,null,null);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String contents = c.getString(c.getColumnIndex("contents"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", "err");
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addDiary(datetime,contents,hashtags,keyTag);

            Log.i("db_select_diary", "datetime:" + datetimeStr + "id:" + _id + ", common_id:" + common_id);
            Log.i("db_select_diary_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectExpense() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("expense",null,null,null,null,null,null);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int amount = c.getInt(c.getColumnIndex("amount"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", "err");
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addExpense(datetime,amount,hashtags,keyTag);


            Log.i("db_select_expense", "datetime:" + datetimeStr + "amount:" + amount + ", common_id:" + common_id);
            Log.i("db_select_expense_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
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

    private void insertDummyTodoData() {
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
                insertTodo(datetime, (i%2) == 0 ? 1 : 0, hashtags, i%5);
            }
        }
    }

    private void insertDummyDiaryData() {
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
                insertDiary(datetime, text, hashtags, i%5);
            }
        }
    }
    private void insertDummyExpenseData() {
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
                insertExpense(datetime, i*1000 + 1000, hashtags, i%5);
            }
        }
    }
}