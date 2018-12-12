/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hzkans.crm.modules.wechat.service.WechatMaterialService;

/**
 * 微信公众号素材库Controller
 * @author dtm
 * @version 2018-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat_material")
public class WechatMaterialController extends BaseController {

	@Autowired
	private WechatMaterialService materialService;
	@RequestMapping(value = "/link_sound_list")
	public String linkSoundList() {
		return "modules/wechatplatform/sound_list";
	}

	@RequestMapping(value = "/link_pic_list")
	public String linkPicList() {
		return "modules/wechatplatform/pic_list";
	}

	@RequestMapping(value = "/link_article_list")
	public String linkArticleList() {
		return "modules/wechatplatform/article_list";
	}

	/**
	 * 查询所有的微信公众号素材信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list_material")
	@ResponseBody
	public String list( HttpServletRequest request) {
		try {
			Integer start = RequestUtils.getInt(request, "current_page", true, "", "");
			Integer count = RequestUtils.getInt(request, "page_size", true, "", "");
			Integer type = RequestUtils.getInt(request, "type", true, "id is null", "");
			Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "id is null", "");

			if (start == null || start == 0) {
				start = 1;
			}
			if (count == null || count == 0) {
				count = 20;
			}

			Page wechatMaterialPage = new Page<WechatMaterial>();
			wechatMaterialPage.setPageNo(start);
			wechatMaterialPage.setPageSize(count);

			WechatMaterial material = new WechatMaterial();
			material.setType(type);
			material.setWechatId(wechatId);

			Page<WechatMaterial> page = materialService.findPage(wechatMaterialPage, material);
			return ResponseUtils.getSuccessApiResponseStr(page);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
		}
	}

	/**
	 * 更改微信公众号素材
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update_material")
	@ResponseBody
	public String form(HttpServletRequest request) {
		try {
			String id = RequestUtils.getString(request, false, "id", "id is null");
			String title = RequestUtils.getString(request, true, "title", "app_secret is null");
			String coverPicture = RequestUtils.getString(request, true, "cover_picture", "token is null");
			String content = RequestUtils.getString(request, true, "content", "token is null");
			String brief = RequestUtils.getString(request, true, "brief", "token is null");
			String uri = RequestUtils.getString(request, true, "uri", "token is null");
			String articleUri = RequestUtils.getString(request, true, "article_uri", "article_uri is null");
			Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "id is null", "");

			User user = UserUtils.getUser();
			if (null == user){
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
			}

			WechatMaterial material = new WechatMaterial();
			material.setId(id);
			material.setTitle(title);
			material.setCoverPicture(coverPicture);
			material.setContent(content);
			material.setBrief(brief);
			material.setUri(uri);
			material.setArticleUri(articleUri);
			material.setCreator(user.getName());
			material.setUpdator(user.getName());
			material.setWechatId(wechatId);

			materialService.update(material);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
		}
	}

	/**
	 * 添加微信公众号素材
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save_material")
	@ResponseBody
	public String saveMaterial(HttpServletRequest request) {
		try {
			String title = RequestUtils.getString(request, false, "title", "title is null");
			String coverPicture = RequestUtils.getString(request, true, "cover_picture", "cover_picture is null");
			String content = RequestUtils.getString(request, true, "content", "content is null");
			String brief = RequestUtils.getString(request, true, "brief", "brief is null");
			String uri = RequestUtils.getString(request, true, "uri", "uri is null");
			String articleUri = RequestUtils.getString(request, true, "article_uri", "article_uri is null");
			Integer type = RequestUtils.getInt(request, "type", false, "type is null", "");
			Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
			String mediaId = RequestUtils.getString(request, "mediaId");
			User user = UserUtils.getUser();
			if (null == user){
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
			}
			if(null == mediaId) {
				mediaId = "";
			}

			WechatMaterial material = new WechatMaterial();
			material.setTitle(title);
			material.setCoverPicture(coverPicture);
			material.setContent(content);
			material.setBrief(brief);
			material.setUri(uri);
			material.setArticleUri(articleUri);
			material.setType(type);
			material.setWechatId(wechatId);
			material.setCreator(user.getName());
			material.setUpdator(user.getName());
			material.setMediaId(mediaId);

			String id = materialService.saveWechatMaterial(material);
			return ResponseUtils.getSuccessApiResponseStr(id);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}

	/**
	 * 删除单个微信公众号素材
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "remove_material")
	@ResponseBody
	public String removeMaterial(HttpServletRequest request) {
		try {
			String id = RequestUtils.getString(request, true, "id", "id is null");
			WechatMaterial material = new WechatMaterial();
			material.setId(id);
			materialService.delete(material);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (ServiceException e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}

}