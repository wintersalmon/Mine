package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class ExpenseData extends CommonData {
    int amount;
    String usage;
    public ExpenseData() {
        this.dataType = TYPE.Expense;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getAmount() {
        return amount;
    }

    public String getUsage() {
        return usage;
    }
}
