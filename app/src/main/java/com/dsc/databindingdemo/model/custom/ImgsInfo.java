package com.dsc.databindingdemo.model.custom;

import java.io.Serializable;
import java.util.List;

/**
 * Created by reny on 2017/3/4.
 * 跳转大图浏览时传递的参数实体
 */

public class ImgsInfo implements Serializable{

    public static final String KEY = ImgsInfo.class.getSimpleName();

    private List<String> imgsList;
    private int curPos;

    public List<String> getImgsList() {
        return imgsList;
    }

    public void setImgsList(List<String> imgsList) {
        this.imgsList = imgsList;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }
}
