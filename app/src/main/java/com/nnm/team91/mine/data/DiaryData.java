package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016-11-29.
 * DiaryData
 */

public class DiaryData extends CommonData implements DiaryInterface {
    String text;

    public DiaryData() {
        this.dataType = TYPE.Diary;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
