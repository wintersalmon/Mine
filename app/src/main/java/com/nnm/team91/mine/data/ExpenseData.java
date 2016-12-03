package com.nnm.team91.mine.data;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by wintersalmon on 2016-11-29.
 * ExpenseData
 */

public class ExpenseData extends CommonData implements ExpenseInterface {
    int amount;
    String currency;

    public ExpenseData() {
        currency = Currency.getInstance(Locale.KOREA).getSymbol();
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String getAmount() {
        return currency + String.valueOf(amount);
    }

}
