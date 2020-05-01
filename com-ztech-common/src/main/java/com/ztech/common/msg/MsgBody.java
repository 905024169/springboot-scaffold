package com.ztech.common.msg;

public class MsgBody {

    int code;
    String msg;

    public MsgBody(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public MsgBody() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
