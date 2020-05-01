package com.ztech.impl.sys;


import com.ztech.common.date.service.impl.CurdServiceImpl;
import com.ztech.dao.sys.PhonebookDao;
import com.ztech.service.sys.PhonebookService;
import com.ztech.vo.sys.Phonebook;
import org.springframework.stereotype.Service;

@Service("phonebookService")
public class PhonebookServiceImpl extends CurdServiceImpl<Phonebook, PhonebookDao> implements PhonebookService {

}
