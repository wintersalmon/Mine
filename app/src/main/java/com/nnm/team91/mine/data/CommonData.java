package com.nnm.team91.mine.data;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class CommonData {
    static public enum TYPE {
        Todo, Diary, Expense
    }
    static private DateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    static private DateFormat dateFormat = new SimpleDateFormat("MM/dd");
    static private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    static private String sampleHashTag = "#October#happy#halloween";

    Date datetime;
    TYPE dataType;

    public void setDate(Date date) {
        datetime = date;
    }
    public void setType(TYPE type) {
        dataType = type;
    }

    public String getDateTime() {
        return datetimeFormat.format(datetime);
    }
    public String getDate() { return dateFormat.format(datetime); }
    public String getTime() { return timeFormat.format(datetime); }

    public TYPE getDataType() {
        return dataType;
    }

    public String getHastagList() { return sampleHashTag; }
}
