package com.ztech.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ztech.service.sys.UserService;
import com.ztech.vo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
   /* @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.select(new LambdaQueryWrapper<User>()
                .eq(User::getUser, userName));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));
       // return new UserDetails(user.getId(), user.getName(), userName, passwordEncoder.encode(user.getPwd()), collection);
        return new UserDetails(user.getId(), user.getName(), userName, user.getPwd(), collection);
    }
}
