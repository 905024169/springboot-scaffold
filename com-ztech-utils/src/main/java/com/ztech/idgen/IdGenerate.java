package com.ztech.idgen;

import com.ztech.codec.EncodeUtils;
import com.ztech.lang.ObjectUtils;
import com.ztech.lang.StringUtils;

import java.security.SecureRandom;
import java.util.UUID;


/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @version 2014-8-19
 */
public class IdGenerate {


    private static SecureRandom random = new SecureRandom();
    private static final Snowflake snowflake = new Snowflake();


    public IdGenerate() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtils.encodeBase62(randomBytes);
    }

    public static String nextIdStr() {
        return String.valueOf(snowflake.nextId());
    }

    public static long nextId() {
        return snowflake.nextId();
    }


    public static String nextCode(String code) {
        if (code == null) {
            return null;
        } else {
            String str = code.trim();
            int len = str.length() - 1;
            int lastNotNumIndex = 0;

            for (int i = len; i >= 0; --i) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    lastNotNumIndex = i;
                    break;
                }
            }

            if (str.charAt(len) >= '0' && str.charAt(len) <= '9' && lastNotNumIndex == len) {
                lastNotNumIndex = -1;
            }

            String prefix = str.substring(0, lastNotNumIndex + 1);
            String numStr = str.substring(lastNotNumIndex + 1, str.length());
            long num = ObjectUtils.toLong(numStr);
            str = prefix + StringUtils.leftPad(String.valueOf(num + 1L), numStr.length(), "0");
            return str;
        }
    }


    public static void main(String[] args) {
        System.out.println(IdGenerate.nextId());
    }

}
