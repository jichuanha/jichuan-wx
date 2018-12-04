package com.hzkans.crm.modules.sys.service;

import com.hzkans.crm.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/23
 * Time:21:54
 */
@Service
@Transactional
public interface ChangePasswordService {
    /**
     * 发送邮件
     * @param user
     * @return
     */
    String sendMail(User user);

    /**
     * 验证链接
     * @param cid
     * @param userId
     * @param userName
     * @param sid
     * @return
     */
    String verification(Integer cid,String userId,String userName,String sid);


}
