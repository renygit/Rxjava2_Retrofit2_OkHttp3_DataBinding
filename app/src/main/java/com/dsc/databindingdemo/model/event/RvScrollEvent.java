package com.dsc.databindingdemo.model.event;

/**
 * Created by reny on 2017/3/8.
 *
 * 给RecyclerView发送滚动事件
 */

public class RvScrollEvent {

    private String type;
    private int pos;

    public RvScrollEvent(String type, int pos){
        this.type = type;
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public int getPos() {
        return pos;
    }
}
