package com.nnm.team91.mine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wintersalmon on 2016. 12. 4..
 */

public class MineSQLiteOpenHelper extends SQLiteOpenHelper {
    public MineSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_table_hashtag =
                "CREATE TABLE hashtag (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tag TEXT NOT NULL);";

        String sql_create_table_common =
                " CREATE TABLE common (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "datetime DATETIME NOT NULL,";

        String sql_create_table_hashtag_in_common =
                "CREATE TABLE hashtag_in_common (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "common_id INTEGER," +
                        "hashtag_id INTEGER," +
                        "FOREIGN KEY(common_id) REFERENCES common(_id)" +
                        "FOREIGN KEY(hashtag_id) REFERENCES hashtag(_id));";

        String sql_create_table_todo =
                "CREATE TABLE todo (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "status INTEGER NOT NULL," +
                        "common_id INTEGER," +
                        "FOREIGN KEY(common_id) REFERENCES common(_id));";

        String sql_create_table_diary =
                "CREATE TABLE diary (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "contents TEXT NOT NULL," +
                        "common_id INTEGER," +
                        "FOREIGN KEY(common_id) REFERENCES common(_id));";

        String sql_create_table_expense =
                "CREATE TABLE expense (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "amount INTEGER NOT NULL," +
                        "common_id INTEGER," +
                        "FOREIGN KEY(common_id) REFERENCES common(_id));";


        db.execSQL(sql_create_table_hashtag);
        db.execSQL(sql_create_table_common);
        db.execSQL(sql_create_table_hashtag_in_common);
        db.execSQL(sql_create_table_todo);
        db.execSQL(sql_create_table_diary);
        db.execSQL(sql_create_table_expense);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_drop_expense = "drop table if exist expense";
        String sql_drop_diary = "drop table if exist expense";
        String sql_drop_todo = "drop table if exist expense";
        String sql_drop_hashtag_in_common = "drop table if exist expense";
        String sql_drop_common = "drop table if exist expense";
        String sql_drop_hashtag = "drop table if exist expense";

        db.execSQL(sql_drop_expense);
        db.execSQL(sql_drop_diary);
        db.execSQL(sql_drop_todo);
        db.execSQL(sql_drop_hashtag_in_common);
        db.execSQL(sql_drop_common);
        db.execSQL(sql_drop_hashtag);

        onCreate(db);
    }

}
