package com.nnm.team91.mine.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wintersalmon on 2016-11-29.
 * CommonData
 */

public class CommonData {
    static private DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static private DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    static private String dateFormatStr = "%04d-%02d-%02d";
    static private String timeFormatStr = "%02d:%02d";
    static private String datetimeFormatStr = "%s %s";

    int id;
    Calendar datetime;
    int keyTagIndex;
    String keyTag;
    ArrayList<String> hashTagList;

    public CommonData() {
        hashTagList = new ArrayList<String>();
        keyTagIndex = 1;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDate(Calendar date) {
        datetime = date;
    }
    public void setDate(int year, int month, int day) {
        datetime.set(year,month,day);
    }
    public void setTime(int hour, int minute) {
        int year = datetime.get(Calendar.YEAR);
        int month = datetime.get(Calendar.MONTH);
        int day = datetime.get(Calendar.DAY_OF_MONTH);

        datetime.set(year,month,day,hour,minute);
    }
    public void setHashTagList(ArrayList<String> list) {
        hashTagList.clear();
        for(String tag : list) {
            hashTagList.add(tag);
        }
    }
    public void setKeyTag(String keyTag) {
        this.keyTag = keyTag;
    }
    public void setKeyTagIndex(int keyTagIndex) {
        if (keyTagIndex < 0 || keyTagIndex > hashTagList.size()){
            this.keyTagIndex = 1;
        }else {
            this.keyTagIndex = keyTagIndex;
        }
    }

    public void setHashTag(String hashTagString) {
        String[] splited = hashTagString.split("\\s+");
        keyTagIndex = 0;
        keyTag = splited[0];
        hashTagList.clear();
        for (String tag : splited) {
            hashTagList.add(tag);
        }
    }

    public int getId() {
        return id;
    }

    public Calendar getRawDateTime() {
        return datetime;
    }

    public String getDateTime() {
        return String.format(datetimeFormatStr, getDate(), getTime());
    }

    public String getDate() {
        int year = datetime.get(Calendar.YEAR);
        int month = datetime.get(Calendar.MONTH);
        int day = datetime.get(Calendar.DAY_OF_MONTH);

        return String.format(dateFormatStr, year, month + 1, day);
    }

    public String getTime() {
        int hour = datetime.get(Calendar.HOUR_OF_DAY);
        int minute = datetime.get(Calendar.MINUTE);
        return String.format(timeFormatStr, hour, minute);
    }

    public String getHasTagListString() {
        String output = "";
        for (String tag : hashTagList) {
            output += tag + " ";
        }
        return output;
    }

    public ArrayList<String> getHashTagList() {
        return hashTagList;
    }

    public String getKeyTag() {
        return keyTag;
    }

    public int getYear() {
        return datetime.get(Calendar.YEAR);
    }

    public int getMonth() {
        return datetime.get(Calendar.MONTH);
    }

    public int getDay() {
        return datetime.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return datetime.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return datetime.get(Calendar.MINUTE);
    }
}
