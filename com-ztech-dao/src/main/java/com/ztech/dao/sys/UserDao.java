package com.ztech.dao.sys;

import com.ztech.common.config.MyBatisDao;
import com.ztech.common.date.dao.CurdDao;
import com.ztech.vo.sys.User;

import java.util.List;

@MyBatisDao
public interface UserDao extends CurdDao<User> {
    List<User> selectUser();

}
