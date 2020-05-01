package com.ztech.common.msg;

public class MsgDataBody<T> extends MsgBody {

    T data;

    public MsgDataBody(){

    }

    public MsgDataBody(int code, String msg) {
        super(code, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
