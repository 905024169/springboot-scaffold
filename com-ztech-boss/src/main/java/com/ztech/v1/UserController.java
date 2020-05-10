package com.ztech.v1;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ztech.impl.SystemService;
import com.ztech.msg.Constant;
import com.ztech.msg.MsgDataBody;
import com.ztech.redis.RedisFactory;
import com.ztech.service.sys.UserService;
import com.ztech.vo.sys.User;
import com.ztech.web.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("sys/user")
public class UserController extends ApiController<User, UserService> {

    @Autowired
    SystemService systemService;
    @Autowired
    RedisFactory redisFactory;

    /*
     * 公共接口
     * 分页查询(page,第几页，pageSize，每页显示条数)：http://localhost:8082/v1/user/selectPage?page=1&pageSize=4
     * 保存/修改(传id为修改，不传id为新增)：(post请求)http://localhost:8082/v1/user/save
     * 删除:(传主键id)http://localhost:8082/v1/user/r?id=1111
     * 查询单条数据:(传主键id)http://localhost:8082/v1/user?id=111
     * */
    @RequestMapping("selectUser")//查询数据列表，返回List，需要分页直接调用公共分页接口v1/user/selectPage?page=1&pageSize=4
    public MsgDataBody<List<User>> selectUserList() {
        MsgDataBody<List<User>> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("查询数据失败");
        try {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            List<User> userList = getBaseService().selectUser();
            msgBody.setData(userList);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("查询数据成功");
        } catch (Exception e) {
            msgBody.setMsg("查询数据失败[" + e.getMessage() + "]");
        }
        return msgBody;
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @RequestMapping("getUser")
    public MsgDataBody<User> getUser() {
        MsgDataBody<User> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("查询数据失败");
        try {
           User user = systemService.getUser();
            msgBody.setData(user);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("查询数据成功");
        } catch (Exception e) {
            msgBody.setMsg("查询数据失败[" + e.getMessage() + "]");
        }
        return msgBody;
    }

    @RequestMapping("str")
    public String str() {
        redisFactory.saveByStr("name","123");
        return getBaseService().str("============");
    }

    @PostMapping("saveUser")
    public String saveUser(User user) {
       getBaseService().save(user);
        return getBaseService().str("============");
    }
   /* @PostMapping("saveList")//批量保存，一条保存失败，数据全部回滚，测试事务是否生效
    public MsgBody saveUser(@RequestBody User[] userList){
        MsgBody msgBody = new MsgBody();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("新增失败");
        try {
            for(User user:userList){//新增操作之前先判断前端传值并返回信息
                if(user.getUser()==null){
                    msgBody.setMsg("账号不能为空");
                    return msgBody;
                }
            }
            getBaseService().saveUser(userList);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("新增成功");
        }catch (Exception e){
            msgBody.setMsg("新增失败["+e.getMessage()+"]");
        }
        return msgBody;
    }*/

   /* @PostMapping("excelList")//批量保存，一条保存失败，数据全部回滚，测试事务是否生效
    public MsgDataBody excelList(MultipartFile file){
        MsgDataBody<List<User>> msgBody = new MsgDataBody();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("导入失败");
        try {
           List<User> userList = PoiUtils.importExcel(file,User.class);
           *//*
     * 相关导入操作
     * *//*
           msgBody.setData(userList);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("导入失败");
        }catch (Exception e){
            msgBody.setMsg("导入失败["+e.getMessage()+"]");
        }
        return msgBody;
    }*/
}

