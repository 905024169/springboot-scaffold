package com.ztech.validator;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

public class ValidatorUtil {

  /*  @Autowired
    private static Validator validator;*/

    public ValidatorUtil() {
    }

    public static Validator getValidator() {
       // if (validator == null) {
           // validator = (Validator) SpringContextUtil.getBean("validator");
            //初始化检查器
            ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                    .configure()
                    .failFast( false )
                    .buildValidatorFactory();
          //   validator = validatorFactory.getValidator();
     //   }

        return validatorFactory.getValidator();
    }

    public static <T> Map<String, StringBuffer> validateMap(T obj) {


        Map<String, StringBuffer> errorMap = null;
        //检查参数
        Set<ConstraintViolation<T>> set = getValidator().validate(obj, new Class[]{Default.class});
        if (set != null && set.size() > 0) {
            errorMap = new HashMap();
            String property = null;
            Iterator var4 = set.iterator();

            while(var4.hasNext()) {
                ConstraintViolation<T> cv = (ConstraintViolation)var4.next();
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    ((StringBuffer)errorMap.get(property)).append("," + cv.getMessage());
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }

        return errorMap;
    }

    public static <T> boolean validate(T obj) {
        Set<ConstraintViolation<T>> set = getValidator().validate(obj, new Class[]{Default.class});
        return set != null && set.size() > 0;
    }

    public static <T> String validateStr(T obj) {
        Map<String, StringBuffer> errorMap = validateMap(obj);
        String msg = "";
        Entry validate;
        if (errorMap != null && errorMap.size() > 0) {
            for(Iterator var3 = errorMap.entrySet().iterator(); var3.hasNext(); msg = msg + ((StringBuffer)validate.getValue()).toString() + "\\r\\n") {
                validate = (Entry)var3.next();
            }
        }

        return msg;
    }
}

