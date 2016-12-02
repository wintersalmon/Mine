package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 11. 30..
 * TimelineData
 */

public class TimelineData extends CommonData {
    private TodoData todo;
    private DiaryData diary;
    private ExpenseData expense;

    public TimelineData() {

    }

    public void setTodo(TodoData todo) {
        this.todo = todo;
    }

    public void setDiary(DiaryData diary) {
        this.diary = diary;
    }

    public void setExpense(ExpenseData expense) {
        this.expense = expense;
    }

    public TodoData getTodo() {
        return todo;
    }

    public DiaryData getDiary() {
        return diary;
    }

    public ExpenseData getExpense() {
        return expense;
    }

    public boolean getTodoStatus() {
        return this.todo.getStatus();
    }

    public String getTodoHashTag() {
        return this.todo.getKeyTag();
    }

    public String getDiaryHashTag() {
        return this.diary.getKeyTag();
    }

    public String getExpenseHashTag() {
        return this.expense.getKeyTag();
    }

    public String getExpenseAmount() {
        return this.expense.getAmount();
    }

}
