package com.ztech.dao.sys;


import com.ztech.config.MyBatisDao;
import com.ztech.date.dao.CurdDao;
import com.ztech.vo.sys.User;

import java.util.List;

@MyBatisDao
public interface UserDao extends CurdDao<User> {
    List<User> selectUser();

}
