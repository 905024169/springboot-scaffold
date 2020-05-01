package com.ztech.common.web;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ztech.common.date.entity.CurdEntity;
import com.ztech.common.date.service.CurdService;
import com.ztech.common.msg.Constant;
import com.ztech.common.msg.MsgBody;
import com.ztech.common.msg.MsgDataBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


public abstract class ApiController<T extends CurdEntity, S extends CurdService<T>> {

    @Autowired
    S baseService;



    public S getBaseService() {
        return baseService;
    }


    @GetMapping()
    public MsgDataBody<?> selectById(HttpServletRequest request, @RequestParam String id) {

        MsgDataBody<T> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("获取实体失败");
        if (StringUtils.isNotEmpty(id)) {
            try {
                T t = getBaseService().selectById(Long.parseLong(id));
                if (t != null) {
                    msgBody.setData(t);
                    msgBody.setCode(Constant.Success.getCode());
                    msgBody.setMsg("获取实体成功");
                }
            } catch (Exception e) {
                msgBody.setMsg(e.getMessage());
            }
        }
        return msgBody;
    }

    @RequestMapping(value = "/r")
    public MsgBody deleteById(HttpServletRequest request, @RequestParam String id) {
        MsgBody msgBody = new MsgBody();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("删除实体失败");
        if (StringUtils.isNotEmpty(id)) {
            try {
                int rows = getBaseService().deleteById(Long.parseLong(id));
                if (rows > 0) {
                    msgBody.setCode(Constant.Success.getCode());
                    msgBody.setMsg("删除实体成功");
                }
            } catch (Exception e) {
                msgBody.setMsg(e.getMessage());
            }
        }
        return msgBody;
    }


    @PostMapping(value = "save")
    public MsgBody save(HttpServletRequest request, @RequestBody T t) {
        MsgBody msgBody = new MsgBody();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("保存实体失败");
        try {
            int rows = getBaseService().save(t);
            if (rows > 0) {
                msgBody.setCode(Constant.Success.getCode());
                msgBody.setMsg("保存实体成功");
            }
        } catch (Exception e) {
            msgBody.setMsg(e.getMessage());
        }
        return msgBody;

    }


    protected MsgDataBody<?> selectPage(Wrapper<T> wrapper, int page, int pageSize) {
        MsgDataBody<IPage<T>> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("数据分页失败");
        try {
            IPage<T> iPage = getBaseService().selectPage(wrapper, page, pageSize);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("数据分页成功");
            msgBody.setData(iPage);
        } catch (Exception e) {
            msgBody.setMsg(e.getMessage());
        }
        return msgBody;
    }

    @GetMapping(value = "selectPage")
    public MsgDataBody<?> selectPage(HttpServletRequest request, @RequestParam int page, @RequestParam int pageSize) {
        Wrapper<T> wrapper = new QueryWrapper<T>();
        return selectPage(wrapper, page, pageSize);
    }
}
