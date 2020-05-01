package com.ztech.common.date.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ztech.common.date.dao.CurdDao;
import com.ztech.common.date.entity.CurdEntity;
import com.ztech.common.date.service.CurdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public abstract class CurdServiceImpl<T extends CurdEntity, D extends CurdDao<T>> implements CurdService<T> {

    @Autowired
    D baseDao;


    public D getBaseDao() {
        return baseDao;
    }


    @Override
    public T selectById(Long id) {
        return getBaseDao().selectById(id);
    }

    @Override
    public T select(T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return getBaseDao().selectOne(queryWrapper);
    }

    @Override
    public T select(Wrapper<T> wrapper) {
        return getBaseDao().selectOne(wrapper);
    }

    @Override
    public List<T> selectList(T t){
            QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
            return getBaseDao().selectList(queryWrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> wrapper){
            return getBaseDao().selectList(wrapper);
    }


    @Override
    public IPage<T> selectPage(T t, int page, int pageSize){
            Page<T> p = new Page<>(page, pageSize);
            QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
            return getBaseDao().selectPage(p, queryWrapper);
    }

    @Override
    public IPage<T> selectPage(Wrapper<T> wrapper, int page, int pageSize){
            Page<T> p = new Page<>(page, pageSize);
            return getBaseDao().selectPage(p, wrapper);
    }

    @Override
    public int selectCount(T t){
            QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
            return getBaseDao().selectCount(queryWrapper);
    }

    @Override
    public int selectCount(Wrapper<T> wrapper){
            return getBaseDao().selectCount(wrapper);
    }

    @Override
    public int deleteById(Long id){
            return getBaseDao().deleteById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids){
            return getBaseDao().deleteBatchIds(ids);
    }

    @Override
    public int delete(T t){
            QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
            return getBaseDao().delete(queryWrapper);
    }

    @Override
    public int delete(Wrapper<T> wrapper){
            return getBaseDao().delete(wrapper);
    }

    @Override
    public int insert(T t){
        t.setCreateTime(new Date());
            return getBaseDao().insert(t);

    }


    @Override
    public int update(T t){
        t.setUpdateTime(new Date());
            return getBaseDao().updateById(t);
    }


    @Override
    public int update(T t, Wrapper<T> wrapper){
        t.setUpdateTime(new Date());
            return getBaseDao().update(t, wrapper);
    }

    @Override
    public int save(T t){
        if (t.getId() != null) {
            if (this.selectById(t.getId()) != null) {
                return update(t);
            } else {
                return insert(t);
            }
        } else {
            return insert(t);
        }
    }


}
