package com.ztech.common.date.entity;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.alibaba.fastjson.annotation.JSONField;
import com.ztech.validator.ValidatorUtil;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


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

