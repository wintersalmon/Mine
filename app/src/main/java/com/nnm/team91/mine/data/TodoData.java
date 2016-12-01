package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class TodoData extends CommonData implements TodoInterface {
    boolean status;
    String title;
    String descrition;

    public TodoData() {
        this.dataType = TYPE.Todo;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void setTitle(String name) {
        title = name;
    }

    @Override
    public void setDescrition(String name) {
        descrition = name;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescrition() {
        return descrition;
    }
}
