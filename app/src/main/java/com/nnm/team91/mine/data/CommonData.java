package com.nnm.team91.mine.data;

import java.util.ArrayList;
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
    int keyTagIndex;
    ArrayList<String> hashTagList;

    public CommonData() {
        hashTagList = new ArrayList<String>();
        keyTagIndex = 1;
    }


    public void setDate(Date date) {
        datetime = date;
    }
    public void setType(TYPE type) {
        dataType = type;
    }
    public void setHashTagList(ArrayList<String> list) {
        hashTagList.clear();
        for(String tag : list) {
            hashTagList.add(tag);
        }
    }
    public void setKeyTagIndex(int keyTagIndex) {
        if (keyTagIndex < 0 || keyTagIndex > hashTagList.size()){
            this.keyTagIndex = 1;
        }else {
            this.keyTagIndex = keyTagIndex;
        }
    }
    public Date getRawDateTime() { return datetime; }
    public String getDateTime() {
        return datetimeFormat.format(datetime);
    }
    public String getDate() { return dateFormat.format(datetime); }
    public String getTime() { return timeFormat.format(datetime); }

    public TYPE getDataType() {
        return dataType;
    }

    public String getHastagList() {
        String output = "";
        for (String tag : hashTagList) {
            output += tag + " ";
        }
        return output;
    }

    public String getKeyTag() {
        return hashTagList.get(keyTagIndex);
    }

}
