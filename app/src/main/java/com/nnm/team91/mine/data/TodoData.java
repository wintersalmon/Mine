package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class TodoData extends CommonData {
    boolean status;
    String title;
    String descrition;

    public TodoData() {
        this.dataType = TYPE.Todo;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTitle(String name) {
        title = name;
    }

    public void setDescrition(String name) {
        descrition = name;
    }

    public boolean getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescrition() {
        return descrition;
    }
}
