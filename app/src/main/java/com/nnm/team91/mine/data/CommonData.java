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
    static private DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm");

    Date datetime;
    TYPE dataType;

    public void setDate(Date date) {
        datetime = date;
    }
    public void setType(TYPE type) {
        dataType = type;
    }

    public String getDateTime() {
        return dateFormat.format(datetime);
    }

    public TYPE getDataType() {
        return dataType;
    }
}
