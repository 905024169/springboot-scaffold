package com.ztech.impl;

import com.ztech.vo.sys.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)
        ){
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = new UserDetails();
            BeanUtils.copyProperties(principal, userDetails);


            User user = new User();
            user.setId(userDetails.getId());
            user.setUser(userDetails.getUsername());
            user.setName(userDetails.getName());

            return user;
        }
        return null;
    }
}
