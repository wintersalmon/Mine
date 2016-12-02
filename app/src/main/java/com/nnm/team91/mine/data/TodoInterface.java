package com.nnm.team91.mine.data;

/**
 * Created by wintersalmon on 2016. 12. 1..
 * TodoInterface
 */

public interface TodoInterface {
    void setStatus(boolean status);
    void setTitle(String name);
    void setDescrition(String name);
    boolean getStatus();
    String getTitle();
    String getDescrition();
}
