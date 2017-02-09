package com.dsc.databindingdemo.model;

import com.reny.mvpvmlib.http.HttpBaseModel;

/**
 * Created by reny on 2017/1/5.
 */

public class BaseModel implements HttpBaseModel{
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
