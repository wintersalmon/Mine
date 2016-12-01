package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 */

public interface ExpenseInterface {
    void setAmount(int amount);
    void setUsage(String usage);
    int getAmount();
    String getUsage();
}
