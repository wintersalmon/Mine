package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * TodoEmptyData
 */

public class TodoEmptyData extends TodoData {
    static private String emptyString = "";
    public TodoEmptyData() {
        super();
    }

    @Override
    public boolean getStatus() {
        // TODO: 2016. 12. 1. Change getStatus() return type to something else to adapte for Empty Data
        return false;
    }

    @Override
    public String getTitle() {
        return emptyString;
    }

    @Override
    public String getDescrition() {
        return emptyString;
    }

    @Override
    public String getKeyTag() {
        return emptyString;
    }

    @Override
    public String getHastagList() {
        return emptyString;
    }
}
