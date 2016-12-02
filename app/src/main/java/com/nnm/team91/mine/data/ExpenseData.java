package com.nnm.team91.mine.data;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by wintersalmon on 2016-11-29.
 * ExpenseData
 */

public class ExpenseData extends CommonData implements ExpenseInterface {
    int amount;
    String usage;
    String currency;

    public ExpenseData() {
        currency = Currency.getInstance(Locale.KOREA).getSymbol();
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
    public String getAmount() {
        return currency + String.valueOf(amount);
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
