package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 * TodoData
 */

public class TodoData extends CommonData implements TodoInterface {
    boolean status;

    public TodoData() {
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}