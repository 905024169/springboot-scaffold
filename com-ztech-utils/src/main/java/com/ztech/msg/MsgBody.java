package com.ztech.msg;

public class MsgBody {

    int code;
    String msg;

    public MsgBody(int code, String msg) {
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

    public void success(String msg) {
        setMsg(msg);
        setCode(Constant.Success.getCode());
    }

    public void find(String msg) {
        setMsg(msg);
        setCode(Constant.Error.getCode());
    }
}
