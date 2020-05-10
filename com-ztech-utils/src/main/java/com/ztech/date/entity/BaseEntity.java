package com.ztech.date.entity;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.ztech.validator.ValidatorUtil;

import java.io.Serializable;


public abstract class BaseEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 7597828305558156627L;

    public BaseEntity() {
    }

    public BaseEntity clone() throws CloneNotSupportedException {
        return (BaseEntity)super.clone();
    }

    public boolean validate() {
        return ValidatorUtil.validate(this);
    }

    public String validateStr() {
        return ValidatorUtil.validateStr(this);
    }


}

