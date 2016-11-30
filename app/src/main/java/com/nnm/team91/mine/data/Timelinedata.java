package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 11. 30..
 */

public class TimelineData extends CommonData {
    private boolean todoStatus;
    private String todoHashTag;
    private String diaryHashTag;
    private String expenseHashTag;
    private int expenseAmount;

    public boolean getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(boolean todoStatus) {
        this.todoStatus = todoStatus;
    }

    public String getTodoHashTag() {
        return todoHashTag;
    }

    public void setTodoHashTag(String todoHashTag) {
        this.todoHashTag = todoHashTag;
    }

    public String getDiaryHashTag() {
        return diaryHashTag;
    }

    public void setDiaryHashTag(String diaryHashTag) {
        this.diaryHashTag = diaryHashTag;
    }

    public String getExpenseHashTag() {
        return expenseHashTag;
    }

    public void setExpenseHashTag(String expenseHashTag) {
        this.expenseHashTag = expenseHashTag;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
