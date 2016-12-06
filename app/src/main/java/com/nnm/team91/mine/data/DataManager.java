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
    private DateFormat datetimeFormat;
    private String dateFmtStr;
    private String timeFmtStr;
    private String datetimeFmtStr;

    private int loadYear;
    private int loadMonth;
    private int loadDay;

    private ArrayList<TimelineData> loadedDataTimeline;
    private ArrayList<TodoData> loadedDataTodo;
    private ArrayList<DiaryData> loadedDataDiary;
    private ArrayList<ExpenseData> loadedDataExpense;
    private ArrayList<Date> loadedDatetime;

    private SQLiteDatabase db;
    private MineSQLiteOpenHelper helper;

    public DataManager(Context context, int version) {
        loadedDataTimeline = new ArrayList<TimelineData>();
        loadedDataTodo = new ArrayList<TodoData>();
        loadedDataDiary = new ArrayList<DiaryData>();
        loadedDataExpense = new ArrayList<ExpenseData>();
        loadedDatetime = new ArrayList<Date>();
        initDataManager();

        helper = new MineSQLiteOpenHelper(context, "mine.db", null, version);
        // TODO: 2016-12-04 remove unused dummy insert statements
//        deleteDataBase(context);
    }

    private void initDataManager() {
        datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFmtStr = "%4d-%02d-%02d";
        timeFmtStr = "%02d:%02d";
        datetimeFmtStr = "%s %s";
    }

    public void updateLoadedData(int year, int month, int day) {
        // TODO : Verify year month day
        this.loadYear = year;
        this.loadMonth = month;
        this.loadDay = day;

//        fillDummyData();
        updateTodoData();
        updateDiaryData();
        updateExpenseData();
        matchDataArrayLength();
        updateTimelineData();
    }

    private void updateTodoData() {
        loadedDataTodo.clear();
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectTodo(loadYear,loadMonth,loadDay);
    }

    private void addTodo(int id, Date datetime, int status, ArrayList<String> hastags, String keyTag) {
        TodoData todo = new TodoData();

        todo.setId(id);
        todo.setDate(datetime);
        if (status == 1) todo.setStatus(true);
        else todo.setStatus(false);
        todo.setHashTagList(hastags);
        todo.setKeyTag(keyTag);

        loadedDataTodo.add(todo);
    }

    private void addEmptyTodo(int index, Date datetime) {
        TodoData emptyTodo = new TodoEmptyData();

        emptyTodo.setId(0);
        emptyTodo.setDate(datetime);

        loadedDataTodo.add(index, emptyTodo);
    }

    private void updateDiaryData() {
        loadedDataDiary.clear();
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectDiary(loadYear,loadMonth,loadDay);
    }

    private void addDiary(int id, Date date, String text, ArrayList<String> hastags, String keyTag) {
        DiaryData diary = new DiaryData();

        diary.setId(id);
        diary.setDate(date);
        diary.setText(text);
        diary.setHashTagList(hastags);
        diary.setKeyTag(keyTag);

        loadedDataDiary.add(diary);
    }

    private void addEmptyDiary(int index, Date datetime) {
        DiaryEmtpyData emptyDiary = new DiaryEmtpyData();

        emptyDiary.setId(0);
        emptyDiary.setDate(datetime);

        loadedDataDiary.add(index,emptyDiary);
    }

    private void updateExpenseData() {
        loadedDataExpense.clear();
        // TODO: 2016-12-04 select all data from db_mine_todo
        // TODO: 2016-12-04 fill array with selected data
        selectExpense(loadYear,loadMonth,loadDay);
    }



    private void addExpense(int id, Date date, int amount, ArrayList<String> hastags, String keyTag) {
        ExpenseData expense = new ExpenseData();

        expense.setId(id);
        expense.setDate(date);
        expense.setAmount(amount);
        expense.setHashTagList(hastags);
        expense.setKeyTag(keyTag);

        loadedDataExpense.add(expense);
    }

    private void addEmptyExpense(int index, Date datetime) {
        ExpenseEmptyData emptyExpense = new ExpenseEmptyData();

        emptyExpense.setId(0);
        emptyExpense.setDate(datetime);

        loadedDataExpense.add(index, emptyExpense);
    }

    private void matchDataArrayLength() {
        // init datetime
        Date currentDate;
        Date todoDate;
        Date diaryDate;
        Date expenseDate;

        // TODO: 2016. 12. 6. add exception handle for loaded data size 0
        if (loadedDataTodo.size() == 0 && loadedDataDiary.size() == 0 && loadedDataExpense.size() == 0)
            return;
        todoDate = loadedDataTodo.get(0).getRawDateTime();
        diaryDate = loadedDataDiary.get(0).getRawDateTime();
        expenseDate = loadedDataExpense.get(0).getRawDateTime();

        int loadedTodoLength = loadedDataTodo.size();
        int loadedDiaryLength = loadedDataDiary.size();
        int loadedExpenseLength = loadedDataExpense.size();

        int addedTodoCount = 0;
        int addedDiaryCount = 0;
        int addedExpenseCount = 0;

        int todoIndex = 0;
        int diaryIndex = 0;
        int expenseIndex = 0;

        while (addedTodoCount < loadedTodoLength || addedDiaryCount < loadedDiaryLength || addedExpenseCount < loadedExpenseLength) {

            // get current item datetime
            if (todoIndex < loadedDataTodo.size()) {
                todoDate = loadedDataTodo.get(todoIndex).getRawDateTime();
            } else {
                todoDate = null;
            }

            if (diaryIndex < loadedDataDiary.size()) {
                diaryDate = loadedDataDiary.get(diaryIndex).getRawDateTime();
            } else {
                diaryDate = null;
            }

            if (expenseIndex < loadedDataExpense.size()) {
                expenseDate = loadedDataExpense.get(expenseIndex).getRawDateTime();
            } else {
                expenseDate = null;
            }


            // find min datetime
            if (todoDate != null)
                currentDate = todoDate;
            else if(diaryDate != null)
                currentDate = diaryDate;
            else
                currentDate = expenseDate;

            if (diaryDate != null && currentDate.after(diaryDate)) currentDate = diaryDate;
            if (expenseDate != null && currentDate.after(expenseDate)) currentDate = expenseDate;

            loadedDatetime.add(currentDate);

            if (todoDate != null && todoDate.compareTo(currentDate) != 0) addEmptyTodo(todoIndex,currentDate);
            else  addedTodoCount++;

            if (diaryDate != null && diaryDate.compareTo(currentDate) != 0) addEmptyDiary(diaryIndex,currentDate);
            else addedDiaryCount++;

            if (expenseDate != null && expenseDate.compareTo(currentDate) != 0) addEmptyExpense(expenseIndex,currentDate);
            else addedExpenseCount++;

            todoIndex++;
            diaryIndex++;
            expenseIndex++;

            Log.i("MATCH_LENGTH", addedTodoCount + ", " + addedDiaryCount  + ", " +  addedExpenseCount);
            Log.i("MATCH_LENGTH", todoIndex + ", " + diaryIndex  + ", " +  expenseIndex);

        }
        Log.i("MATCH_LENGTH_DONE", loadedTodoLength + ", " + loadedDiaryLength  + ", " +  loadedExpenseLength);
        Log.i("MATCH_LENGTH_DONE", todoIndex + ", " + diaryIndex  + ", " +  expenseIndex);
    }

    private void updateTimelineData() {
        TodoData todo;
        DiaryData diary;
        ExpenseData expense;
        TodoEmptyData emptyTodo;
        DiaryEmtpyData emptyDiary;
        ExpenseEmptyData emptyExpense;
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
                emptyExpense = null;
            } else {
                emptyExpense = new ExpenseEmptyData();
                expense = emptyExpense;
            }

            if (emptyTodo != null) emptyTodo.setDate(datetime);
            if (emptyDiary != null) emptyDiary.setDate(datetime);
            if (emptyExpense != null) emptyExpense.setDate(datetime);

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
//        String[] selectionArgs = new String[1];
//        selectionArgs[0] = String.valueOf(_id);

//        Cursor c = db.query("common", columns, selection, selectionArgs, null, null, null);
        Cursor c = db.rawQuery("SELECT datetime FROM common WHERE _id = '" + String.valueOf(_id) + "'",null);
//        Cursor c = db.rawQuery("SELECT datetime FROM common WHERE _id = ?", selectionArgs);

        if (c != null) {
            c.moveToFirst();
            datetime = c.getString(c.getColumnIndex("datetime"));
            Log.i("db_select_common","success id:" + String.valueOf(_id) + " : " + datetime);
        }

        if (c != null) c.close();

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

        c.close();

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

        c.close();

        return keyTag;
    }

    public void selectAllTodo() {
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
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addTodo(common_id,datetime,status,hashtags,keyTag);

            Log.i("db_select_todo", "datetime:" + datetimeStr + "id:" + _id + ", status:" + status + ", common_id:" + common_id);
            Log.i("db_select_todo_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectTodo(int year, int month, int day) {
        TodoData todo = null;
        db = helper.getReadableDatabase();
        String[] selectionArgs = new String[2];
        selectionArgs[0] = createDateString(year,month,day);
        selectionArgs[1] = createDateString(year,month,day);

        Cursor c  = db.rawQuery("SELECT * FROM todo t LEFT OUTER JOIN common c ON c._id = t.common_id WHERE c.datetime BETWEEN date(?,'start of day') AND date(?,'start of day','+1 day')", selectionArgs);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int status = c.getInt(c.getColumnIndex("status"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addTodo(common_id,datetime,status,hashtags,keyTag);

            Log.i("db_select_todo", "datetime:" + datetimeStr + "id:" + _id + ", status:" + status + ", common_id:" + common_id);
            Log.i("db_select_todo_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectAllDiary() {
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
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addDiary(common_id,datetime,contents,hashtags,keyTag);

            Log.i("db_select_diary", "datetime:" + datetimeStr + "id:" + _id + ", common_id:" + common_id);
            Log.i("db_select_diary_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectDiary(int year, int month, int day) {
        db = helper.getReadableDatabase();
        String[] selectionArgs = new String[2];
        selectionArgs[0] = createDateString(year,month,day);
        selectionArgs[1] = createDateString(year,month,day);

        Cursor c  = db.rawQuery("SELECT * FROM diary d LEFT OUTER JOIN common c ON c._id = d.common_id WHERE c.datetime BETWEEN date(?,'start of day') AND date(?,'start of day','+1 day')", selectionArgs);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String contents = c.getString(c.getColumnIndex("contents"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addDiary(common_id,datetime,contents,hashtags,keyTag);

            Log.i("db_select_diary", "datetime:" + datetimeStr + "id:" + _id + ", common_id:" + common_id);
            Log.i("db_select_diary_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }


    public void selectAllExpense() {
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
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addExpense(common_id,datetime,amount,hashtags,keyTag);

            Log.i("db_select_expense", "datetime:" + datetimeStr + "amount:" + amount + ", common_id:" + common_id);
            Log.i("db_select_expense_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void selectExpense(int year, int month, int day) {
        db = helper.getReadableDatabase();
        String[] selectionArgs = new String[2];
        selectionArgs[0] = createDateString(year,month,day);
        selectionArgs[1] = createDateString(year,month,day);

        Cursor c  = db.rawQuery("SELECT * FROM expense e LEFT OUTER JOIN common c ON c._id = e.common_id WHERE c.datetime BETWEEN date(?,'start of day') AND date(?,'start of day','+1 day')", selectionArgs);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int amount = c.getInt(c.getColumnIndex("amount"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Date datetime = null;
            String datetimeStr = selectDatetimeFromCommon(common_id);
            try {
                datetime = datetimeFormat.parse(datetimeStr);
            } catch (Exception e) {
                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addExpense(common_id,datetime,amount,hashtags,keyTag);

            Log.i("db_select_expense", "datetime:" + datetimeStr + "amount:" + amount + ", common_id:" + common_id);
            Log.i("db_select_expense_tags", "keytag:" + keyTag + " hashtags : " + hashtags.size());
        }

        c.close();
    }

    public void deleteTodo(int id) {
        db = helper.getWritableDatabase();
        try {
            db.delete("hashtag_in_common", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("todo", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("common", "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.i("DELETE_TODO", e.toString());
        }
    }

    public void deleteDiary(int id) {
        db = helper.getWritableDatabase();
        try {
            db.delete("hashtag_in_common", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("diary", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("common", "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.i("DELETE_TODO", e.toString());
        }
    }

    public void deleteExpense(int id) {
        db = helper.getWritableDatabase();
        try {
            db.delete("hashtag_in_common", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("expense", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("common", "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.i("DELETE_TODO", e.toString());
        }
    }

//    private void testDeleteAllData() {
//        for (TodoData todo : loadedDataTodo) {
//            int id = todo.getId();
//            deleteTodo(id);
//        }
//
//        for (DiaryData diary : loadedDataDiary) {
//            int id = diary.getId();
//            deleteDiary(id);
//        }
//
//        for (ExpenseData expense : loadedDataExpense) {
//            int id = expense.getId();
//            deleteExpense(id);
//        }
//    }

    public void updateTodo(TodoData todo) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (todo != null && todo.getId() != 0) {
            if (todo.getStatus()) values.put("status", 1);
            else values.put("status", 0);

            // TODO: 2016. 12. 6. add hashtag update function
            Log.i("UPDATE_TODO", values.toString());
            db.update("todo", values, "common_id = ?", new String[]{String.valueOf(todo.getId())});
        }
    }

    public void updateDiary(DiaryData diary) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (diary != null && diary.getId() != 0) {
            values.put("contents", diary.getText());

            // TODO: 2016. 12. 6. add hashtag update function
            Log.i("UPDATE_DIARY", values.toString());
            db.update("diary", values, "common_id = ?", new String[]{String.valueOf(diary.getId())});
        }
    }

    public void updateExpense(ExpenseData expense) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (expense != null && expense.getId() != 0) {
            values.put("amount", expense.getAmount());

            // TODO: 2016. 12. 6. add hashtag update function
            Log.i("UPDATE_EXPENSE", values.toString());
            db.update("expense", values, "common_id = ?", new String[]{String.valueOf(expense.getId())});
        }
    }

//    private void testUpdateAllData() {
//        for (TodoData todo : loadedDataTodo) {
//            todo.setStatus(false);
//            updateTodo(todo);
//        }
//
//        for (DiaryData diary : loadedDataDiary) {
//            diary.setText("hi");
//            updateDiary(diary);
//        }
//
//        for (ExpenseData expense : loadedDataExpense) {
//            expense.setAmount(9999);
//            updateExpense(expense);
//        }
//    }

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
        ArrayList<String> hashtags = new ArrayList<String>();
        hashtags.add("Todo");
        hashtags.add("Weekly");
        hashtags.add("Happy");
        hashtags.add("VeryVeryLongHashTagForTodo");
        hashtags.add("Energy");

        Date datetime = Calendar.getInstance().getTime();
        int year = 2016;
        int month = 12;
        int count = 0;

        int status;
        int keyTagIndex;

        for (int day=1; day<3; day++) {
            for (int hour=9; hour < 24; hour += 2) {

                int minutes = 0;
                while (minutes < 60) {
                    String datetimeStr = createDateTimeString(year,month,day,hour,minutes);
                    Log.i("TODO", datetimeStr);
                    try {
                        datetime = datetimeFormat.parse(datetimeStr);
                    } catch (Exception e) {
                        Log.i("insertDummyTodoData", e.toString());
                        continue;
                    }

                    status = (count%2) == 0 ? 1 : 0;
                    keyTagIndex = count%hashtags.size();

                    insertTodo(datetime, status, hashtags, keyTagIndex);

                    if (count%2 == 1) {
                        minutes += 15;
                    }
                    count++;
                }
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

        Date datetime = Calendar.getInstance().getTime();
        int year = 2016;
        int month = 12;
        int count = 0;

        int keyTagIndex;

        for (int day=1; day<3; day++) {
            for (int hour=9; hour < 24; hour += 5) {

                int minutes = 0;
                while (minutes < 60) {
                    String datetimeStr = createDateTimeString(year,month,day,hour,minutes);
                    Log.i("DIARY", datetimeStr);
                    try {
                        datetime = datetimeFormat.parse(datetimeStr);
                    } catch (Exception e) {
                        Log.i("insertDummyDiaryData", e.toString());
                        continue;
                    }

                    keyTagIndex = count%hashtags.size();

                    insertDiary(datetime, sample_diary_contents, hashtags, keyTagIndex);

                    if (count%2 == 1) {
                        minutes += 20;
                    }
                    count++;
                }
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
        int year = 2016;
        int month = 12;
        int count = 0;

        int amount;
        int keyTagIndex;

        for (int day=1; day<3; day++) {
            for (int hour=9; hour < 24; hour += 3) {
                Log.i("EXPENSE", "day:" + day);
                int minutes = 0;
                while (minutes < 60) {
                    String datetimeStr = createDateTimeString(year,month,day,hour,minutes);

                    try {
                        datetime = datetimeFormat.parse(datetimeStr);
                    } catch (Exception e) {
                        Log.i("insertDummyExpenseData", e.toString());
                        continue;
                    }

                    amount = count * 500 + 1000;
                    keyTagIndex = count%hashtags.size();

                    insertExpense(datetime, amount, hashtags, keyTagIndex);

                    if (count%2 == 1) {
                        minutes += 30;
                    }
                    count++;
                }
            }
        }
    }

    private String createDateString(int year, int month, int day) {
        return String.format(dateFmtStr, year, month, day);
    }

    private String createTimeString(int hour, int minutes) {
        return String.format(timeFmtStr, hour, minutes);
    }

    private String createDateTimeString(int year, int month, int day, int hour, int minutes) {
        return String.format(datetimeFmtStr, createDateString(year,month,day), createTimeString(hour,minutes));
    }

    private void fillDummyData() {
        insertDummyTodoData();
        insertDummyDiaryData();
        insertDummyExpenseData();
    }

    private void deleteDataBase(Context context) {
        context.deleteDatabase("mine.db");
    }

    static private String sample_diary_contents = "일기의 내용을 아무거나 집어넣어 보자\n" +
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
}