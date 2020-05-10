package com.ztech.msg;

public enum Constant {
    Error(101),
    Success(100);

    private int code;

    private Constant(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
