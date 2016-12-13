package com.nnm.team91.mine.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * DataManager
 */

public class DataManager {
    private SimpleDateFormat datetimeFormat;
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
    private ArrayList<Calendar> loadedDatetime;

    private SQLiteDatabase db;
    private MineSQLiteOpenHelper helper;

    public DataManager(Context context, int version) {
        loadedDataTimeline = new ArrayList<TimelineData>();
        loadedDataTodo = new ArrayList<TodoData>();
        loadedDataDiary = new ArrayList<DiaryData>();
        loadedDataExpense = new ArrayList<ExpenseData>();
        loadedDatetime = new ArrayList<Calendar>();
        initDataManager();

        helper = new MineSQLiteOpenHelper(context, "mine.db", null, version);
//        deleteDataBase(context);
    }

    private void initDataManager() {
        datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        dateFmtStr = "%4d-%02d-%02d";
        timeFmtStr = "%02d:%02d";
        datetimeFmtStr = "%s %s";
    }

    public void updateLoadedData() {
        updateLoadedData(loadYear, loadMonth, loadDay);
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
        selectTodo(loadYear,loadMonth,loadDay);
    }

    private void addTodo(int id, Calendar datetime, int status, ArrayList<String> hastags, String keyTag) {
        TodoData todo = new TodoData();

        todo.setId(id);
        todo.setDate(datetime);
        if (status == 1) todo.setStatus(true);
        else todo.setStatus(false);
        todo.setHashTagList(hastags);
        todo.setKeyTag(keyTag);

        loadedDataTodo.add(todo);
    }

    private void addEmptyTodo(int index, Calendar datetime) {
        TodoData emptyTodo = new TodoEmptyData();

        emptyTodo.setId(0);
        emptyTodo.setDate(datetime);

        loadedDataTodo.add(index, emptyTodo);
    }

    private void updateDiaryData() {
        loadedDataDiary.clear();
        selectDiary(loadYear,loadMonth,loadDay);
    }

    private void addDiary(int id, Calendar date, String text, ArrayList<String> hastags, String keyTag) {
        DiaryData diary = new DiaryData();

        diary.setId(id);
        diary.setDate(date);
        diary.setText(text);
        diary.setHashTagList(hastags);
        diary.setKeyTag(keyTag);

        loadedDataDiary.add(diary);
    }

    private void addEmptyDiary(int index, Calendar datetime) {
        DiaryEmtpyData emptyDiary = new DiaryEmtpyData();

        emptyDiary.setId(0);
        emptyDiary.setDate(datetime);

        loadedDataDiary.add(index,emptyDiary);
    }

    private void updateExpenseData() {
        loadedDataExpense.clear();
        selectExpense(loadYear,loadMonth,loadDay);
    }

    private void addExpense(int id, Calendar date, int amount, ArrayList<String> hastags, String keyTag) {
        ExpenseData expense = new ExpenseData();

        expense.setId(id);
        expense.setDate(date);
        expense.setAmount(amount);
        expense.setHashTagList(hastags);
        expense.setKeyTag(keyTag);

        loadedDataExpense.add(expense);
    }

    private void addEmptyExpense(int index, Calendar datetime) {
        ExpenseEmptyData emptyExpense = new ExpenseEmptyData();

        emptyExpense.setId(0);
        emptyExpense.setDate(datetime);

        loadedDataExpense.add(index, emptyExpense);
    }

    private void matchDataArrayLength() {
        Calendar currentDate;
        Calendar todoDate;
        Calendar diaryDate;
        Calendar expenseDate;

        // TODO: 2016. 12. 6. add exception handle for loaded data size 0
        if (loadedDataTodo.size() == 0 && loadedDataDiary.size() == 0 && loadedDataExpense.size() == 0)
            return;

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

//            Log.i("MATCH_LENGTH", addedTodoCount + ", " + addedDiaryCount  + ", " +  addedExpenseCount);
//            Log.i("MATCH_LENGTH", todoIndex + ", " + diaryIndex  + ", " +  expenseIndex);

        }
//        Log.i("MATCH_LENGTH_DONE", loadedTodoLength + ", " + loadedDiaryLength  + ", " +  loadedExpenseLength);
//        Log.i("MATCH_LENGTH_DONE", todoIndex + ", " + diaryIndex  + ", " +  expenseIndex);
    }

    private void updateTimelineData() {
        loadedDataTimeline.clear();
        TodoData todo;
        DiaryData diary;
        ExpenseData expense;
        TodoEmptyData emptyTodo;
        DiaryEmtpyData emptyDiary;
        ExpenseEmptyData emptyExpense;
        Calendar datetime = null;
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

    public ArrayList<HashTagData> searchHashTag(String keyword) {
        ArrayList<HashTagData> result = new ArrayList<HashTagData>();

        db = helper.getReadableDatabase();
        String[] selectionArgs = new String[]{keyword};

        Cursor c  = db.rawQuery("SELECT common.common_id, common.hashtag_id, common.is_key_tag, hash.tag FROM hashtag_in_common common LEFT OUTER JOIN hashtag hash ON common.hashtag_id = hash._id WHERE hash.tag LIKE ?", selectionArgs);
        while(c.moveToNext()) {
            int hashTagId = c.getInt(c.getColumnIndex("hashtag_id"));
            int commonId =  c.getInt(c.getColumnIndex("common_id"));
            String hashTagString = c.getString(c.getColumnIndex("tag"));

            HashTagData tag = new HashTagData(hashTagId, commonId, hashTagString);
            result.add(tag);
        }

        return result;
    }

    private long insertHashtag(String tag) {
        int _id = selectHashTagId(tag);
        if (_id == 0) {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("tag", tag);

            _id = (int) db.insert("hashtag", null, values);
        }
        return _id;
    }

    private long insertCommon(String datetimeStr) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

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

    public void insertTodo(TodoData todo) {
        int status = todo.getStatus() ? 1 : 0;
        insertTodo(todo.getDateTime(), status, todo.getHashTagList(), 0);
    }

    public void insertDiary(DiaryData diary) {
        insertDiary(diary.getDateTime(), diary.getText(), diary.getHashTagList(), 0);
    }

    public void insertExpense(ExpenseData expense) {
        insertExpense(expense.getDateTime(), expense.getAmountValue(), expense.getHashTagList(), 0);
    }

    public void insertTodo(String datetimeStr, int status, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetimeStr);

        values.put("status", status);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("todo", null, values);
    }

    public void insertDiary(String datetimeStr, String contents, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetimeStr);

        values.put("contents", contents);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("diary", null, values);
    }

    public void insertExpense(String datetimeStr, int amount, ArrayList<String> hashtags, int keyTagIndex) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long common_id = insertCommon(datetimeStr);

        values.put("amount", amount);
        values.put("common_id", common_id);

        insertHashtagInCommon(common_id, hashtags, keyTagIndex);

        db.insert("expense", null, values);
    }

    private Calendar selectDatetimeFromCommon(int _id) {
        Calendar datetime = Calendar.getInstance();
        String datetimeStr = "";
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT datetime FROM common WHERE _id = '" + String.valueOf(_id) + "'",null);

        if (c != null) {
            c.moveToFirst();
            datetimeStr = c.getString(c.getColumnIndex("datetime"));
            try {
                datetime.setTime(datetimeFormat.parse(datetimeStr));
            } catch (Exception e) {
//                Log.d("DATETIME", e.toString());
            }
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

    public CommonData selectCommon(int commonId) {
        CommonData common = new CommonData();

        ArrayList<String> hashtags = selectHashtagInCommon(commonId);
        String keyTag = selectKeyTagInCommon(commonId);

        common.setId(commonId);
        common.setHashTagList(hashtags);
        common.setKeyTag(keyTag);
        return common;
    }

    public void selectTodo(int year, int month, int day) {
        db = helper.getReadableDatabase();
        String[] selectionArgs = new String[2];
        selectionArgs[0] = createDateString(year,month,day);
        selectionArgs[1] = createDateString(year,month,day);

        Cursor c  = db.rawQuery("SELECT * FROM todo t LEFT OUTER JOIN common c ON c._id = t.common_id WHERE c.datetime BETWEEN date(?,'start of day') AND date(?,'start of day','+1 day')", selectionArgs);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int status = c.getInt(c.getColumnIndex("status"));
            int common_id = c.getInt(c.getColumnIndex("common_id"));

            Calendar datetime = null;
            try {
                datetime = selectDatetimeFromCommon(common_id);
            } catch (Exception e) {
//                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addTodo(common_id,datetime,status,hashtags,keyTag);
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

            Calendar datetime = null;
            try {
                datetime = selectDatetimeFromCommon(common_id);
            } catch (Exception e) {
//                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addDiary(common_id,datetime,contents,hashtags,keyTag);
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

            Calendar datetime = null;
            try {
                datetime = selectDatetimeFromCommon(common_id);
            } catch (Exception e) {
//                Log.i("db_select_exception", e.toString());
            }

            ArrayList<String> hashtags = selectHashtagInCommon(common_id);
            String keyTag = selectKeyTagInCommon(common_id);

            addExpense(common_id,datetime,amount,hashtags,keyTag);
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
//            Log.i("DELETE_TODO", e.toString());
        }
        updateLoadedData();
    }

    public void deleteDiary(int id) {
        db = helper.getWritableDatabase();
        try {
            db.delete("hashtag_in_common", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("diary", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("common", "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
//            Log.i("DELETE_TODO", e.toString());
        }
        updateLoadedData();
    }

    public void deleteExpense(int id) {
        db = helper.getWritableDatabase();
        try {
            db.delete("hashtag_in_common", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("expense", "common_id=?", new String[]{String.valueOf(id)});
            db.delete("common", "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
//            Log.i("DELETE_TODO", e.toString());
        }
        updateLoadedData();
    }

    private void updateCommonDate(CommonData common) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (common != null && common.getId() != 0) {
            values.put("datetime", common.getDateTime());
            db.update("common", values, "_id = ?", new String[]{String.valueOf(common.getId())});
        }
    }

    private void updateCommonHashTag(CommonData currCommon) {
        int commonId = currCommon.getId();
        db = helper.getReadableDatabase();

        CommonData prevCommon = selectCommon(commonId);

        HashSet<String> previousHashTag = new HashSet<String>(prevCommon.getHashTagList());
        HashSet<String> currentHashTag = new HashSet<String>(currCommon.getHashTagList());

        HashSet<String> removeTag = (HashSet<String>) previousHashTag.clone();
        HashSet<String> insertTag = (HashSet<String>) currentHashTag.clone();

        removeTag.removeAll(currentHashTag);
        insertTag.removeAll(previousHashTag);


        insertHashTagToCommon(commonId, insertTag);

        updateKeyTagInCommon(commonId, prevCommon.getKeyTag(), currCommon.getKeyTag());

        removeHashTagFromCommon(commonId, removeTag);
    }

    private void removeHashTagFromCommon(int commonId, HashSet<String> removeTag) {
        db = helper.getWritableDatabase();
        for(String hashTag : removeTag) {
            int hashTagId = selectHashTagId(hashTag);
            try {
                db.delete("hashtag_in_common", "common_id=? AND hashtag_id=?", new String[]{String.valueOf(commonId),String.valueOf(hashTagId) });
            } catch (Exception e) {
//                Log.d("DELETE", "HASHTAG_ERROR");
            }
        }
    }

    private int selectHashTagId(String hashTag) {
        int _id;
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM hashtag WHERE tag = '" + hashTag + "'", null);

        if (c.moveToNext()) {
            _id = c.getInt(c.getColumnIndex("_id"));
        } else {
            _id = 0;
        }

        return _id;
    }

    private void insertHashTagToCommon(int commonId, HashSet<String> insertTag) {
        for (String hashTag : insertTag) {
            long tag_id = insertHashtag(hashTag);
            insertHashtagInCommon(commonId, tag_id, 0);
        }
    }

    private void updateKeyTagInCommon(int commonId, String deleteKeyTag, String insertKeyTag) {
        int delete_key_tag_id = selectHashTagId(deleteKeyTag);
        int insert_key_tag_id = selectHashTagId(insertKeyTag);

        ContentValues delete_values = new ContentValues();
        delete_values.put("is_key_tag", 0);

        ContentValues insert_values = new ContentValues();
        insert_values.put("is_key_tag", 1);

        db = helper.getWritableDatabase();

        db.update("hashtag_in_common", delete_values, "common_id = ? and hashtag_id = ?", new String[]{String.valueOf(commonId), String.valueOf(delete_key_tag_id)});
        db.update("hashtag_in_common", insert_values, "common_id = ? and hashtag_id = ?", new String[]{String.valueOf(commonId), String.valueOf(insert_key_tag_id)});

    }

    public void updateTodo(TodoData todo) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        updateCommonDate(todo);

        if (todo != null && todo.getId() != 0) {
            if (todo.getStatus()) values.put("status", 1);
            else values.put("status", 0);

            db.update("todo", values, "common_id = ?", new String[]{String.valueOf(todo.getId())});
        }

        updateCommonHashTag(todo);

        updateLoadedData();
    }

    public void updateTodoStatus(TodoData todo) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (todo != null && todo.getId() != 0) {
            if (todo.getStatus()) values.put("status", 1);
            else values.put("status", 0);

            db.update("todo", values, "common_id = ?", new String[]{String.valueOf(todo.getId())});
        }

        updateLoadedData();
    }

    public void updateDiary(DiaryData diary) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        updateCommonDate(diary);

        if (diary != null && diary.getId() != 0) {
            values.put("contents", diary.getText());

//            Log.i("UPDATE_DIARY", values.toString());
            db.update("diary", values, "common_id = ?", new String[]{String.valueOf(diary.getId())});
        }

        updateCommonHashTag(diary);

        updateLoadedData();
    }

    public void updateExpense(ExpenseData expense) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        updateCommonDate(expense);

        if (expense != null && expense.getId() != 0) {
            values.put("amount", expense.getAmount().substring(1));

//            Log.i("UPDATE_EXPENSE", values.toString());
            db.update("expense", values, "common_id = ?", new String[]{String.valueOf(expense.getId())});
        }

        updateCommonHashTag(expense);

        updateLoadedData();
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

    private String createDateString(int year, int month, int day) {
        return String.format(dateFmtStr, year, month, day);
    }

    private String createTimeString(int hour, int minutes) {
        return String.format(timeFmtStr, hour, minutes);
    }

    private String createDateTimeString(int year, int month, int day, int hour, int minutes) {
        return String.format(datetimeFmtStr, createDateString(year,month,day), createTimeString(hour,minutes));
    }
}