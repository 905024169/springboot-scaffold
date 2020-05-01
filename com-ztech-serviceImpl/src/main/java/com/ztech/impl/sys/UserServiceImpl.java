package com.ztech.impl.sys;


import com.ztech.common.date.service.impl.CurdServiceImpl;
import com.ztech.dao.sys.UserDao;
import com.ztech.service.sys.UserService;
import com.ztech.vo.sys.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends CurdServiceImpl<User, UserDao> implements UserService {

    @Override
    public List<User> selectUser() {
        return getBaseDao().selectUser();
    }

    public String str(String str) {
        return str;
    }


   /* @Override
    public List<User> selectUserList() {
        return selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public void saveUser(User[] userList) {//测试事务是否生效
        for (User user : userList) {
            insert(user);
        }
    }*/

}
