package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.WxOSSClient;
import com.hzkans.crm.modules.activity.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizg on 2017/3/8.
 */
public class BaseUpload {

	@Autowired
	private WxOSSClient wxOSSClient;

	private final static Logger log = LoggerFactory.getLogger(BaseUpload.class);

	private static final String FILE_PATH = "deploy/data/www/static/upload/";

	private static final String EXTERNAL_ROOT = "ex";

	private String allowSuffix = "jpg,png,gif,jpeg";// 允许文件格式
	private long allowSize = 6L;// 允许文件大小
	private String fileName;
	private String[] fileNames;

	public String getAllowSuffix() {
		return allowSuffix;
	}

	public void setAllowSuffix(String allowSuffix) {
		this.allowSuffix = allowSuffix;
	}

	public long getAllowSize() {
		return allowSize * 1024 * 1024;
	}

	public void setAllowSize(long allowSize) {
		this.allowSize = allowSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	/**
	 * 重新命名文件
	 * 
	 * @return
	 */
	private String getFileNameNew() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fmt.format(new Date());
	}

	/**
	 * 多文件上传
	 * 
	 * @param files
	 * @param destDir
	 * @param request
	 * @throws Exception
	 */
	public void uploads(MultipartFile[] files, String destDir,
                        HttpServletRequest request) throws Exception {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		try {
			fileNames = new String[files.length];
			int index = 0;
			for (MultipartFile file : files) {
				String suffix = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1);
				int length = getAllowSuffix().toLowerCase().indexOf(suffix);
				if (length == -1) {
					throw new Exception("请上传允许格式的文件");
				}
				if (file.getSize() > getAllowSize()) {
					throw new Exception("您上传的文件大小已经超出范围");
				}
				String realPath = request.getSession().getServletContext()
						.getRealPath("/");
				File destFile = new File(realPath + destDir);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				String fileNameNew = getFileNameNew() + "." + suffix;//
				File f = new File(destFile.getAbsoluteFile() + "\\"
						+ fileNameNew);
				file.transferTo(f);
				f.createNewFile();
				fileNames[index++] = basePath + destDir + fileNameNew;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 功能：文件上传
	 * 
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	public void upload(MultipartFile file, HttpServletRequest request) throws ServiceException {

//		String path = request.getContextPath();
//        log.info("[{}] path:{}",path);
//		String basePath = path +"/"+FILE_PATH;
		try {
			String suffix = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf(".") + 1);
			int length = getAllowSuffix().toLowerCase().indexOf(suffix);
			if (length == -1) {
				throw new Exception("请上传允许格式的文件");
			}
			if (file.getSize() > getAllowSize()) {
				throw new Exception("您上传的文件大小已经超出范围");
			}

			String fileNameNew = getFileNameNew() + "." + suffix;

			// 文件保存路径
//			String filePath = request.getSession().getServletContext().getRealPath("/") +FILE_PATH
//					+ fileNameNew;

			String filePath ="/"+FILE_PATH + fileNameNew;
			log.info("[{}] filePath:{}",filePath);

			File destFile = new File(filePath);
			log.info("[{}] destFile:{}",destFile.exists());
			// 文件不存在就创建
			if (!destFile.exists()){
				destFile.mkdirs();
			}
			// 文件转换
			file.transferTo(destFile);
			//fileName = basePath + fileNameNew;
			fileName = fileNameNew;

			//上传到oss
			wxOSSClient.uploadFile(EXTERNAL_ROOT,fileName,filePath);

			// 上传完之后删除;
			FileUtil.destroyFile(fileName);
			log.info("上传success........");
		} catch (Exception e) {
			log.error("上传文件发生异常！", e);
			throw new ServiceException(ResponseEnum.B_E_UPOLAD_FILE_ERROR.getMsg());
		}
	}

}
