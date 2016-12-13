package com.nnm.team91.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.data.ExpenseData;

import java.util.ArrayList;

/**
 * Created by wintersalmon on 2016. 11. 25..
 * ExpenseAdapter
 */

public class ExpenseAdapter extends BaseAdapter {
    private ArrayList<ExpenseData> listViewItemList = new ArrayList<ExpenseData>() ;

    public ExpenseAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_expense, parent, false);
        }

        TextView timeTextView = (TextView) convertView.findViewById(R.id.expenseTextViewTime) ;
        TextView amountTextView = (TextView) convertView.findViewById(R.id.expenseTextViewAmount) ;
        TextView keyTagTextView = (TextView) convertView.findViewById(R.id.expenseTextViewKeyTag) ;
        TextView hashTagTextView = (TextView) convertView.findViewById(R.id.expenseTextViewHasTag) ;

        ExpenseData expense = listViewItemList.get(position);

        timeTextView.setText(expense.getTime());
        amountTextView.setText(expense.getAmount());
        keyTagTextView.setText(expense.getKeyTag());
        hashTagTextView.setText(expense.getHasTagListString());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(ExpenseData expense) {
        listViewItemList.add(expense);
    }

    public void clear() {
        listViewItemList.clear();
    }
}