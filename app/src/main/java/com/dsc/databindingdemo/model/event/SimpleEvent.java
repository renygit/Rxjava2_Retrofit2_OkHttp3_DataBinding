package com.dsc.databindingdemo.model.event;

/**
 * Created by reny on 2017/3/8.
 */

public class SimpleEvent {

    private String type;

    public SimpleEvent withType(String type){
        this.type = type;
        return this;
    }

    public String getType(){
        return type;
    }

    //public static final String name = "";

}
