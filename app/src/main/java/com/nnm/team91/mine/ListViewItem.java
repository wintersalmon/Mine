package com.nnm.team91.mine;

import android.graphics.drawable.Drawable;

/**
 * Created by tristan on 2016-11-10.
 */

public class ListViewItem {
    private int type;
    private Drawable sharpImg, crossImg, searchImg;
    private String txtStr, edtStr;

    public void setType(int type) {
        this.type = type;
    }
    public void setSharpImg(Drawable sharp) {
        sharpImg = sharp;
    }
    public void setTxtStr(String txt) {
        txtStr = txt;
    }
    public void setEdtStr(String edt) {
        edtStr = edt;
    }
    public void setCrossImg(Drawable cross){
        crossImg = cross;
    }
    public void setSearchImg(Drawable search){
        searchImg = search;
    }


    public int getType() {
        return this.type;
    }
    public Drawable getSharpImg() {
        return this.sharpImg;
    }
    public String getTxtStr() {
        return this.txtStr;
    }
    public String getEdtStr() {
        return this.edtStr;
    }
    public Drawable getCrossImg() {
        return this.crossImg;
    }
    public Drawable getSearchImg() {
        return this.searchImg;
    }
}
