package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * DiaryEmptyData
 */

public class DiaryEmtpyData extends DiaryData {
    static private String emptyString = "";

    public DiaryEmtpyData() {
        super();
    }

    @Override
    public String getHasTagList() {
        return emptyString;
    }

    @Override
    public String getKeyTag() {
        return emptyString;
    }

    @Override
    public String getText() {
        return emptyString;
    }
}
