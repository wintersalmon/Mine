package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * ExpenseEmptyData
 */

public class ExpenseEmptyData extends ExpenseData {

    static private String emptyString = "";

    public ExpenseEmptyData() {
        super();
    }

    @Override
    public String getAmount() {
        return emptyString;
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
