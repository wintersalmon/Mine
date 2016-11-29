package com.nnm.team91.mine.todo;


/**
 * Created by wintersalmon on 2016. 11. 25..
 */

public class TodoItem {
    private String time;
    private boolean status;
    private String title;
    private String description;

    public String getTime() {
        return time;
    }

    public boolean getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
