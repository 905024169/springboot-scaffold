package com.ztech.vo.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ztech.common.date.entity.CurdEntity;


@TableName("wx_user")
public class User extends CurdEntity {
    String name;
    String user;
    Long groupValue;
    String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(Long groupValue) {
        this.groupValue = groupValue;
    }
}
