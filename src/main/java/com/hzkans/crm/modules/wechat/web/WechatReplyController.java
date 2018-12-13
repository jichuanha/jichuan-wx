/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.constants.ReplyType;
import com.hzkans.crm.modules.wechat.entity.*;
import com.hzkans.crm.modules.wechat.service.WechatReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


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
    private WechatReplyService wechatReplyService;

    @RequestMapping(value = "/link_auto_res_atten")
    public String autoResAtten() {
        return "modules/wechatplatform/autoResAtten";
    }

    @RequestMapping(value = "/link_auto_res_rece")
    public String autoResRece() {
        return "modules/wechatplatform/autoResRece";
    }

    @RequestMapping(value = "/link_auto_res_key")
    public String autoResKey() {
        return "modules/wechatplatform/autoResKey";
    }

    @RequestMapping(value = "/link_auto_res_key_new")
    public String autoResKeyNew() {
        return "modules/wechatplatform/autoResKeyNew";
    }

    /**
     * 获取自动回复详情
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "get_reply_details")
    @ResponseBody
    public String getReplyDetails(HttpServletRequest request) throws Exception {
        try {
            String ruleId = RequestUtils.getString(request, true, "rule_id", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "reply_type is null", "");

            WechatReplyNew wechatReplyNew = wechatReplyService.getFollowedOrImmeReply(wechatId, ruleId);
            return ResponseUtils.getSuccessApiResponseStr(wechatReplyNew);
        } catch (ServiceException e) {
            logger.error("listReply is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }
    }

    /**
     * 添加自动回复信息--被关注回复，收到信息回复
     *
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save_reply_new")
    @ResponseBody
    public String saveReplynew(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            String remarks = RequestUtils.getString(request, true, "remarks", "");
            String content = RequestUtils.getString(request, true, "content_all", "");
            String ruleName = RequestUtils.getString(request, true, "rule_name", "reply_desc is null");
            Integer replyWay = RequestUtils.getInt(request, "reply_way", false, "reply_way is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", false, "reply_type is null", "");
            Integer status = RequestUtils.getInt(request, "status", false, "reply_type is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }
            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setStatus(status);
            wechatReplyNew.setRemarks(remarks);
            wechatReplyNew.setReplyWay(replyWay);
            wechatReplyNew.setRuleName(ruleName);

            //插入主表信息 以及获取id
            String ruleId = wechatReplyService.saveReply(wechatReplyNew);

            wechatReplyService.saveReplyContent(wechatId, ruleId, content, ruleType);

            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR,e.getMessage());
        }

    }

    /**
     * 添加自动回复信息--关键字回复
     *
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save_reply_follow")
    @ResponseBody
    public String saveReplyFollow(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            String remarks = RequestUtils.getString(request, true, "remarks", "");
            String keywords = RequestUtils.getString(request, true, "keywords", "");
            String content = RequestUtils.getString(request, true, "content_all", "");
            String ruleName = RequestUtils.getString(request, false, "rule_name", "reply_desc is null");
            Integer replyWay = RequestUtils.getInt(request, "reply_way", false, "reply_way is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", true, "reply_type is null", "");
            Integer status = RequestUtils.getInt(request, "status", true, "reply_type is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setStatus(status);
            wechatReplyNew.setRemarks(remarks);
            wechatReplyNew.setReplyWay(replyWay);
            wechatReplyNew.setRuleName(ruleName);

            //插入主表信息 以及获取id
            String ruleId = wechatReplyService.saveReply(wechatReplyNew);

            //插入
            wechatReplyService.saveReplyContent(wechatId, ruleId, content, ruleType);

            wechatReplyService.saveReplyKeyword(wechatId, ruleId, keywords);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

    /**
     * 删除自动回复信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "remove_reply")
    @ResponseBody
    public String removeReplynew(HttpServletRequest request) throws Exception {
        try {
            String ruleId = RequestUtils.getString(request, true, "rule_id", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "reply_type is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", false, "reply_type is null", "");

            wechatReplyService.deleteReply(ruleId,wechatId,ruleType);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (ServiceException e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
        }
    }

    /**
     * 修改关键字回复
     *
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update_reply_keyword")
    @ResponseBody
    public String updateReplyFollow(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            String remarks = RequestUtils.getString(request, true, "remarks", "");
            String keywords = RequestUtils.getString(request, true, "keywords", "");
            String content = RequestUtils.getString(request, true, "content_all", "");
            String ruleName = RequestUtils.getString(request, false, "rule_name", "reply_desc is null");
            Integer replyWay = RequestUtils.getInt(request, "reply_way", false, "reply_way is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", false, "reply_type is null", "");
            String ruleId = RequestUtils.getString(request, false, "rule_id", "reply_desc is null");
            Integer status = RequestUtils.getInt(request, "status", true, "reply_type is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setRemarks(remarks);
            wechatReplyNew.setReplyWay(replyWay);
            wechatReplyNew.setRuleName(ruleName);
            wechatReplyNew.setId(ruleId);
            wechatReplyNew.setStatus(status);

            //主信息表
            wechatReplyService.updateReplyRrule(wechatReplyNew);
            //删除所有的关键字回复内容以及关键字
            wechatReplyService.deleteReplykeywordAndContent(ruleId,wechatId);
            //插入内容
            wechatReplyService.saveReplyContent(wechatId, ruleId, content, ruleType);
            //插入关键字
            wechatReplyService.saveReplyKeyword(wechatId, ruleId, keywords);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

    /**
     * 所有的关键词回复
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list_reply_all")
    @ResponseBody
    public String listReplyAll(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", false, "wechat_id is null", "");
            String ruleId = RequestUtils.getString(request, true, "rule_id", "");

            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyNew.setId(ruleId);
            List<WechatReplyNew> wechatReplyNewList= wechatReplyService.listWechatReply(wechatReplyNew);

            return ResponseUtils.getSuccessApiResponseStr(wechatReplyNewList);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

    /**
     * 暂停关键词回复
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "suspend_reply")
    @ResponseBody
    public String suspendReply(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer status = RequestUtils.getInt(request, "status", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", true, "wechat_id is null", "");
            String ruleId = RequestUtils.getString(request, true, "rule_id", "");

            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setId(ruleId);
            wechatReplyNew.setStatus(status);
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyService.suspendReply(wechatReplyNew);

            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

    /**
     * 搜索获得自动回复信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list_reply_by_name")
    @ResponseBody
    public String listReplyByName(HttpServletRequest request) throws Exception {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Integer ruleType = RequestUtils.getInt(request, "rule_type", false, "wechat_id is null", "");
            String ruleName = RequestUtils.getString(request, false, "rule_name", "");

            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setRuleType(ruleType);
            wechatReplyNew.setRuleName(ruleName);
            List<WechatReplyNew> wechatReplyNewList= wechatReplyService.listWechatReply(wechatReplyNew);

            return ResponseUtils.getSuccessApiResponseStr(wechatReplyNewList);
        } catch (Exception e) {
            logger.error("saveReplynew is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }



}