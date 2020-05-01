package com.ztech.impl;

import com.ztech.codec.Md5Utils;

public class Md5PasswordEncoderImpl implements org.springframework.security.crypto.password.PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return Md5Utils.md5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equalsIgnoreCase(s);
    }
}
