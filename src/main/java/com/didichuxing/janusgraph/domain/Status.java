package com.didichuxing.janusgraph.domain;

/**
 * Created by zhzy on 2017/8/8.
 */
public class Status {
    public String code;
    public String errMsg;

    public Status(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
