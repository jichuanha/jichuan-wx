/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.entity.WechatReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hzkans.crm.modules.wechat.service.WechatReplyService;

/**
 * 微信公众号自动回复Controller
 *
 * @author dtm
 * @version 2018-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat_reply")
public class WechatReplyController extends BaseController {

    @Autowired
    private WechatReplyService wechatNewReplyService;

    /**
     * 获取所有的自动回复信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list_reply")
    @ResponseBody
    public String listReply(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Integer start = RequestUtils.getInt(request, "current_page", true, "", "");
            Integer count = RequestUtils.getInt(request, "page_size", true, "", "");
            Integer replyType = RequestUtils.getInt(request, "reply_type", true, "reply_type is null", "");

            if (start == null || start == 0) {
                start = 1;
            }
            if (count == null || count == 0) {
                count = 20;
            }

            Page wechatNewReplyPage = new Page<WechatReply>();
            wechatNewReplyPage.setPageNo(start);
            wechatNewReplyPage.setPageSize(count);

            WechatReply wechatMaterial = new WechatReply();
            wechatMaterial.setReplyType(replyType);
            wechatMaterial.setDeleted(0);

            Page<WechatReply> page = wechatNewReplyService.findPage(wechatNewReplyPage, wechatMaterial);
            return ResponseUtils.getSuccessApiResponseStr(page);
        } catch (ServiceException e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

    /**
     * 更改单条自动回复信息
     * @param request
     * @return
     */
    @RequestMapping(value = "update_reply.do")
    @ResponseBody
    public String updateReply(HttpServletRequest request) {
        try {
            String id = RequestUtils.getString(request, true, "id", "id is null"); Integer unOpen = RequestUtils.getInt(request, "un_open", true, "un_open is null", "");
            Integer replyType = RequestUtils.getInt(request, "reply_type", true, "reply_type is null", "");
            Integer contentType = RequestUtils.getInt(request, "content_type", true, "content_type is null", "");
            Integer keyType = RequestUtils.getInt(request, "key_type", true, "key_type is null", "");
            String keywords = RequestUtils.getString(request, true, "keywords", "keywords is null");
            String remarks = RequestUtils.getString(request, true, "remarks", "");
            String replyDesc = RequestUtils.getString(request, true, "reply_desc", "reply_desc is null");
            Integer replyWay = RequestUtils.getInt(request, "reply_way", true, "reply_way is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatReply reply = new WechatReply();
            reply.setId(id);
            reply.setUnOpen(unOpen);
            reply.setReplyType(replyType);
            reply.setContentType(contentType);
            reply.setKeyType(keyType);
            reply.setKeywords(keywords);
            reply.setRemarks(remarks);
            reply.setReplyDesc(replyDesc);
            reply.setReplyWay(replyWay);
            reply.setUpdator(user.getName());
            wechatNewReplyService.update(reply);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (ServiceException e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
        }
    }

    /**
     * 添加自动回复信息
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save_reply")
    @ResponseBody
    public String saveReply(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Integer unOpen = RequestUtils.getInt(request, "un_open", false, "un_open is null", "");
            Integer replyType = RequestUtils.getInt(request, "reply_type", false, "reply_type is null", "");
            Integer contentType = RequestUtils.getInt(request, "content_type", false, "content_type is null", "");
            Integer keyType = RequestUtils.getInt(request, "key_type", true, "key_type is null", "");
            String keywords = RequestUtils.getString(request, false, "keywords", "keywords is null");
            String remarks = RequestUtils.getString(request, true, "remarks", "");
            String replyDesc = RequestUtils.getString(request, false, "reply_desc", "reply_desc is null");
            Integer replyWay = RequestUtils.getInt(request, "reply_way", false, "reply_way is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatReply reply = new WechatReply();
            reply.setUnOpen(unOpen);
            reply.setReplyType(replyType);
            reply.setContentType(contentType);
            reply.setKeyType(keyType);
            reply.setKeywords(keywords);
            reply.setRemarks(remarks);
            reply.setReplyDesc(replyDesc);
            reply.setReplyWay(replyWay);
            reply.setWechatId(wechatId);
            reply.setCreator(user.getName());
            reply.setUpdator(user.getName());

            wechatNewReplyService.save(reply);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (ServiceException e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
        }
    }

    /**
     * 删除自动回复信息
     * @param request
     * @return
     */
    @RequestMapping(value = "remove_reply")
    @ResponseBody
    public String removeReply(HttpServletRequest request) {
        try {
            String id = RequestUtils.getString(request, true, "id", "id is null");
            WechatReply reply = new WechatReply();
            reply.setId(id);

            wechatNewReplyService.delete(reply);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (ServiceException e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
        }
    }


}