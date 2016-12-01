package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class ExpenseData extends CommonData implements ExpenseInterface {
    int amount;
    String usage;
    public ExpenseData() {
        this.dataType = TYPE.Expense;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
