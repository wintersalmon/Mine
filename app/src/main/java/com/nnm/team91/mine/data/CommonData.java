package com.nnm.team91.mine.data;

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
    Date datetime;
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
    public void setDate(Date date) {
        datetime = date;
    }
    public void setDate(int year, int month, int day) {
        String dateStr = String.format(dateFormatStr, year, month, day);
        String timeStr = getTime();
        String datetimeStr = String.format(datetimeFormatStr, dateStr, timeStr);
        try {
            datetime = datetimeFormat.parse(datetimeStr);
        } catch (Exception e) {

        }
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

    public int getId() {
        return id;
    }

    public Date getRawDateTime() {
        return datetime;
    }

    public String getDateTime() {
        return datetimeFormat.format(datetime);
    }

    public String getDate() {
        return dateFormat.format(datetime);
    }

    public String getTime() {
        return timeFormat.format(datetime);
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

    // TODO: 2016. 12. 9. fix constant data to calander
    public int getYear() {
        return 2016;
    }

    public int getMonth() {
        return 12;
    }

    public int getDay() {
        return 1;
    }
}
