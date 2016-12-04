package com.nnm.team91.mine.data;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wintersalmon on 2016-11-29.
 * CommonData
 */

public class CommonData {
    static private DateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    static private DateFormat dateFormat = new SimpleDateFormat("MM/dd");
    static private DateFormat timeFormat = new SimpleDateFormat("HH:mm");

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
    public String getHasTagList() {
        String output = "";
        for (String tag : hashTagList) {
            output += tag + " ";
        }
        return output;
    }
    public String getKeyTag() {
        return keyTag;
    }
}
