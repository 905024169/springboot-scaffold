package com.ztech.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztech.lang.PinYinUtils;
import com.ztech.lang.StringUtils;
import com.ztech.msg.Constant;
import com.ztech.msg.MsgBody;
import com.ztech.msg.MsgDataBody;
import com.ztech.service.sys.PhonebookService;
import com.ztech.vo.sys.Phonebook;
import com.ztech.web.ApiController;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("v1/phone")
public class PhonebookController extends ApiController<Phonebook, PhonebookService> {

    @Value("${imageFilePath}")
    private String imageFilePath;

    @RequestMapping("selectPhone")
    public MsgDataBody<Map<String, Object>> selectPhoneList() {
        MsgDataBody<Map<String, Object>> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("查询数据失败");
        try {
            Map<String, Object> map = new HashMap<>();
            char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                    'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                    'W', 'X', 'Y', 'Z'};
            List<Phonebook> phoneBooks = getBaseService().selectList(new LambdaQueryWrapper<Phonebook>());
            for (int i = 0; i < letters.length; i++) {
                String letter = String.valueOf(letters[i]);
                List<Phonebook> phonebookList = phoneBooks.stream().filter(v -> v.getInitial() != null && v.getInitial().equals(letter))
                        .collect(Collectors.toList());
                if (phonebookList.size() > 0) {
                    map.put(letter, phonebookList);
                }
            }
            msgBody.setData(map);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("查询数据成功");
        } catch (Exception e) {
            msgBody.setMsg("查询数据失败[" + e.getMessage() + "]");
        }
        return msgBody;
    }


    @Override
    public MsgBody save(HttpServletRequest request, @RequestBody Phonebook phonebook) {
        MsgBody msgBody = new MsgBody();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("保存实体失败");
        try {
            String initial = String.valueOf(PinYinUtils.getPinYin(phonebook.getName()).charAt(0)).toUpperCase(); // 获取首字母(大写)
            phonebook.setInitial(initial);
            getBaseService().save(phonebook);
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setMsg("保存实体成功");
        } catch (Exception e) {
            msgBody.setMsg(e.getMessage());
        }
        return msgBody;
    }

    @RequestMapping("selectPhonePage")
    public MsgDataBody<IPage<Phonebook>> selectPhonePage(HttpServletRequest request, int pageNum, int pageSize) {
        MsgDataBody<IPage<Phonebook>> msgBody = new MsgDataBody<>();
        msgBody.setMsg("查询失败");
        msgBody.setCode(Constant.Error.getCode());
        try {
            String name = request.getParameter("name");
            IPage<Phonebook> iPage = getBaseService().selectPage(new LambdaQueryWrapper<Phonebook>()
                    .like(StringUtils.isNotEmpty(name), Phonebook::getName, name), pageNum, pageSize);
            msgBody.setMsg("查询成功");
            msgBody.setCode(Constant.Success.getCode());
            msgBody.setData(iPage);
        } catch (Exception e) {
            msgBody.setMsg(e.getMessage());
        }
        return msgBody;
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("upload")
    public MsgDataBody<Map<String, Object>> uploadImage(MultipartFile file) {
        MsgDataBody<Map<String, Object>> msgBody = new MsgDataBody<>();
        msgBody.setCode(Constant.Error.getCode());
        msgBody.setMsg("上传失败");
        try {
            if (!file.isEmpty()) {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String newFileName = sdf.format(date) + suffixName;
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
                Map<String, Object> map = new HashMap<>();
                map.put("title", newFileName);
                map.put("src", "/image/" + newFileName);
                msgBody.setCode(Constant.Success.getCode());
                msgBody.setMsg("上传成功");
                msgBody.setData(map);
            }
        } catch (Exception e) {
            msgBody.setMsg(e.getMessage());
        }
        return msgBody;
    }
}

