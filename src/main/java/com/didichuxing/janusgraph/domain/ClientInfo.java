package com.didichuxing.janusgraph.domain;

import java.util.Map;

/**
 * Created by zhzy on 2017/8/8.
 */
public class ClientInfo {

    private Status status;
    private Object data;

    public ClientInfo(){}

    public ClientInfo(Status status, Map<String, Object> data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
