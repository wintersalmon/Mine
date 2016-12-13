package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 13..
 */

public class HashTagData {
    private int id;
    private int commonId;
    private String hashTag;

    public HashTagData() {

    }

    public HashTagData(int id, String hashTag) {
        setId(id);
        setHashTag(hashTag);
    }

    public HashTagData(int id, int commonId, String hashTag) {
        this.id = id;
        this.commonId = commonId;
        this.hashTag = hashTag;
    }

    public int getCommonId() {
        return commonId;
    }

    public void setCommonId(int commonId) {
        this.commonId = commonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
}
