package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 */

public class DiaryData extends CommonData {
    String text;

    public DiaryData() {
        this.dataType = TYPE.Diary;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
