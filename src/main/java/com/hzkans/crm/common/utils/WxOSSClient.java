package com.hzkans.crm.common.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/6
 */
public class WxOSSClient {
    private OSSClient ossClient;
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;

    public WxOSSClient() {
    }

    public WxOSSClient(String endpoint, String bucketName, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public void init() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setMaxConnections(10);
        conf.setConnectionTimeout(5000);
        conf.setMaxErrorRetry(3);
        conf.setSocketTimeout(2000);
        this.ossClient = new OSSClient(this.endpoint, this.getAccessKeyId(), this.getAccessKeySecret(), conf);
        if (!this.ossClient.doesBucketExist(this.bucketName)) {
            this.ossClient.createBucket(this.bucketName);
        }

    }

    public void cleanUp() {
    }

    public List<String> listFiles(String parentPath) {
        List<String> fileList = new ArrayList();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(parentPath + "/");
        ObjectListing listing = this.ossClient.listObjects(listObjectsRequest);
        Iterator i$ = listing.getObjectSummaries().iterator();

        while(i$.hasNext()) {
            OSSObjectSummary objectSummary = (OSSObjectSummary)i$.next();
            fileList.add(objectSummary.getKey());
        }

        return fileList;
    }

    public List<String> listDirs(String parentPath) {
        List<String> fileList = new ArrayList();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucketName);
        listObjectsRequest.setDelimiter("/");
        if (parentPath != null && parentPath.length() > 0) {
            listObjectsRequest.setPrefix(parentPath + "/");
        }

        ObjectListing listing = this.ossClient.listObjects(listObjectsRequest);
        Iterator i$ = listing.getCommonPrefixes().iterator();

        while(i$.hasNext()) {
            String commonPrefix = (String)i$.next();
            fileList.add(commonPrefix);
        }

        return fileList;
    }

    public void uploadFile(String destFileDir, String destFileName, String sourceFilePath) throws FileNotFoundException {
        this.putObject(destFileDir, destFileName, sourceFilePath);
    }

    public PutObjectResult putObject(String destFileDir, String destFileName, String sourceFilePath) throws FileNotFoundException {
        File file = new File(sourceFilePath);
        InputStream content = new FileInputStream(file);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.length());
        PutObjectResult result = this.ossClient.putObject(this.bucketName, destFileDir + "/" + destFileName, content, meta);

        try {
            content.close();
        } catch (Exception var9) {
            ;
        }

        return result;
    }

    public void multipartUpload(String key, String filePath) throws IOException {
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(this.bucketName, key);
        InitiateMultipartUploadResult initiateMultipartUploadResult = this.ossClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        int partSize = 5242880;
        File partFile = new File(filePath);
        int partCount = (int)(partFile.length() / 5242880L);
        if (partFile.length() % 5242880L != 0L) {
            ++partCount;
        }

        List<PartETag> partETags = new ArrayList();

        for(int i = 0; i < partCount; ++i) {
            FileInputStream fis = new FileInputStream(partFile);
            long skipBytes = (long)(5242880 * i);
            fis.skip(skipBytes);
            long size = 5242880L < partFile.length() - skipBytes ? 5242880L : partFile.length() - skipBytes;
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(this.bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
            uploadPartRequest.setInputStream(fis);
            uploadPartRequest.setPartSize(size);
            uploadPartRequest.setPartNumber(i + 1);
            UploadPartResult uploadPartResult = this.ossClient.uploadPart(uploadPartRequest);
            partETags.add(uploadPartResult.getPartETag());
            fis.close();
        }

    }

    public void deletePicture(String key) {
        this.ossClient.deleteObject(this.bucketName, key);
        this.ossClient.shutdown();
    }

    public void deletePicture(List<String> keys) {
        DeleteObjectsResult deleteObjectsResult = this.ossClient.deleteObjects((new DeleteObjectsRequest(this.bucketName)).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        System.out.println(deletedObjects);
        this.ossClient.shutdown();
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
