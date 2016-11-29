package com.nnm.team91.mine.data;

import java.text.SimpleDateFormat;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class TodoData extends CommonData {
    int status;
    String shortName;
    String longName;

    public TodoData() {
        this.dataType = TYPE.Todo;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setShortName(String name) {
        shortName = name;
    }

    public void setLongName(String name) {
        longName = name;
    }

    public int getStatus() {
        return status;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
