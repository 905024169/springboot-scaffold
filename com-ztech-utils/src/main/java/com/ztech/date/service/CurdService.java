package com.ztech.date.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztech.date.entity.CurdEntity;

import java.util.List;

public interface CurdService<T extends CurdEntity> {

    T selectById(Long id);

    T select(T t);

    T select(Wrapper<T> wrapper);

    List<T> selectList(T t);

    List<T> selectList(Wrapper<T> wrapper);

    IPage<T> selectPage(T t, int page, int pageSize);

    IPage<T> selectPage(Wrapper<T> wrapper, int page, int pageSize);

    int selectCount(T t);

    int selectCount(Wrapper<T> wrapper);

    int deleteById(Long id);

    int deleteByIds(List<Long> ids);

    int delete(T t);

    int delete(Wrapper<T> wrapper);

    int insert(T t);

    int update(T t);

    int update(T t, Wrapper<T> wrapper);

    int save(T t);


}
