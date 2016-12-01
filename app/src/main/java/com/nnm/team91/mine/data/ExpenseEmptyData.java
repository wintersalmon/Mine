package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 */

public class ExpenseEmptyData extends ExpenseData {

    static private String emptyString = "";

    public ExpenseEmptyData() {
        super();
    }

    @Override
    public int getAmount() {
        // TODO: 2016. 12. 1. change getAmount() return type to String
        return 0;
    }

    @Override
    public String getUsage() {
        return emptyString;
    }

    @Override
    public String getHastagList() {
        return emptyString;
    }

    @Override
    public String getKeyTag() {
        return emptyString;
    }
}
