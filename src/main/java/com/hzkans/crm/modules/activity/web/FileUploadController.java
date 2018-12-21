package com.hzkans.crm.modules.activity.web;

import com.google.common.collect.Maps;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.utils.WxOSSClient;
import com.hzkans.crm.modules.activity.entity.ImageVO;
import com.hzkans.crm.modules.activity.utils.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author lizg
 * @date 2017/3/8
 */

@Controller
@RequestMapping(value = "${adminPath}/activity/activityLottery")
public class FileUploadController extends BaseUpload {

	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);

	@Autowired
    private WxOSSClient wxOSSClient;

    private static final String EXTERNAL_ROOT = "ex";

	private static final String FILE_PATH = "deploy/data/www/static/upload/";
	@RequestMapping(value = "/upload.do",produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		try {
				log.info("[{}] file:{}", multipartFile.getSize());
				if (multipartFile.isEmpty()) {
					return ResponseUtils
							.getFailApiResponseStr(ResponseEnum.B_E_UPOLAD_FILE_ERROR);
				}
				super.upload(multipartFile, request);
				return ResponseUtils.getSuccessResponseStr(super.getFileName());

		} catch (Exception e) {
			log.error("upload is error ...", e);
			return ResponseUtils.getFailApiResponseStr(
                    ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/ueupload.do", method = RequestMethod.GET)
    @ResponseBody
    public void ueupload(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		log.info("ueupload first request:{}");
        String action = request.getParameter("action");    
        if ("config".equals(action)) {    
            OutputStream os = response.getOutputStream();    
            IOUtils.copy(FileUploadController.class.getClassLoader().getResourceAsStream("config.json"), os);    
        }  
    }  
      
    @RequestMapping(value = "/ueupload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> ueUpload(HttpServletRequest request, @RequestParam CommonsMultipartFile upfile) throws IOException {
        Map<String, String> result = Maps.newHashMap(); 
        log.info("ueupload second request: image uploading...{}");
        System.out.println(upfile.getFileItem().getFieldName());  
        String path = getFilePath(upfile);  
        File file = new File(path);
        log.info("ueupload file upload success path: {}" + path);
        String state = "SUCCESS";  
        result.put("url", path); 
        result.put("size", String.valueOf(file.length()));    
        result.put("type", file.getName().substring(file.getName().lastIndexOf(".")));    
        result.put("state", state);    
        return result;    
    }  
    
    protected String getFilePath(CommonsMultipartFile uploadFile){
//      String absolutePath = "E:/upload";  //本地调试
//      File folder = new File(absolutePath); //本地调试
        File folder = new File("/" + FILE_PATH);  //测试环境调试
        if (!folder.exists()) {  
            folder.mkdirs();  
        }  
        String rawName = uploadFile.getFileItem().getName();  
        String fileExt = rawName.substring(rawName.lastIndexOf("."));  
        log.info("ueupload file fileExt: {}" + fileExt);
        String newName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;  
        log.info("ueupload file newName: {}" + newName);
        String filePath ="/" + FILE_PATH + newName;//测试环境调试
        File saveFile = new File(filePath);  
//      File saveFile = new File(absolutePath + File.separator + newName);//本地调试
        try {  
            uploadFile.getFileItem().write(saveFile);

            //上传到oss
            wxOSSClient.uploadFile(EXTERNAL_ROOT,newName,filePath);

            // 上传完之后删除;
             FileUtil.destroyFile(newName);
            log.info("ueupload file write success...");
        } catch (Exception e) {
            log.error("上传文件发生异常:{}",e.getMessage());
         //   e.printStackTrace();
            return "";  
        }  
        return newName; 
    }


    @RequestMapping("/list.do")
    @ResponseBody
    public String getUploadImgList(HttpServletRequest request) throws ServiceException {
        try {
            List<String> ossImageList =  wxOSSClient.listFiles(EXTERNAL_ROOT);
            Integer start = RequestUtils.getInt(request, "current_page", true, "", "");
            Integer count = RequestUtils.getInt(request, "page_size", true, "", "");
            if (start == null || start == 0) {
                start = 1;
            }
            if (count == null || count == 0) {
                count = 20;
            }
            List<ImageVO> imageVOList = Collections.EMPTY_LIST;
            if (CollectionUtils.isNotEmpty(ossImageList)) {
                imageVOList  = new ArrayList<>();

                int i = 0;
                for (String file : ossImageList) {
                    i++;
                    if (i > 500) {
                        break;
                    }
                    if (file.length() > 4 && !".mp3".equals(file.substring((file.length() - 4),file.length()))) {
                        String image = file.substring(3);
                        ImageVO imageVO = new ImageVO();
                        imageVO.setImage(image);
                        imageVOList.add(imageVO);
                    }
                }
            }

            Map map = page(imageVOList,start,count);
            return ResponseUtils.getSuccessApiResponseStr(map);
        } catch (Exception e) {
            log.error("getUploadImgList is error...", e);
            return ResponseUtils.getFailApiResponseStr(
                    ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
        }
    }
    /**
     * 分页
     *
     * @param result
     * @param start
     * @param count
     * @return
     */
    private Map page(List<ImageVO> result, Integer start, Integer count) {
        Map agentMap = new HashMap<>();
        Integer totalCount;
        if (count != null) {
            if (CollectionUtils.isNotEmpty(result) && result.size() > 0) {
                //总记录数
                totalCount = result.size();

                //分页起始下标
                Integer fromIndex = (start - 1) * count;

                //分页结束下标
                //totalCount * 1.0 比较小数位；
                //如果非最后一页，就取数组当前页最后下标；如果最后一页，取终点下标，终点下标即数组长度值
                int toIndex = (start < (totalCount * 1.0 / count))
                        ? (start * count) : totalCount;

                List<ImageVO> _result = new ArrayList<>();
                for (int i = fromIndex; i < toIndex; i++) {
                    _result.add(result.get(i));
                }

                agentMap.put("result", _result);
                agentMap.put("totalCount", totalCount);
            } else {
                agentMap.put("result", new ArrayList<>());
                agentMap.put("totalCount", 0);
            }
        }
        return agentMap;
    }
}
