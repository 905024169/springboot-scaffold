package com.ztech.service.sys;

import com.ztech.date.service.CurdService;
import com.ztech.vo.sys.User;

import java.util.List;


public interface UserService extends CurdService<User> {

    List<User> selectUser();

    String str(String str);
}
