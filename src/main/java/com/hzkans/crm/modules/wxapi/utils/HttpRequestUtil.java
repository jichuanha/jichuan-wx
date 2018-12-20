package com.hzkans.crm.modules.wxapi.utils;


import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.wxapi.constants.SignType;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author jc
 * @description
 * @create 2018/3/19
 */
public class HttpRequestUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static final String GET_METHOD = "GET";

    public static final String POST_METHOD = "POST";

    public static final String DEFAULT_CHARSET = "utf-8";

    private static int DEFAULT_CONNTIME = 5000;

    private static int DEFAULT_READTIME = 5000;

    /**
     * http请求
     *
     * @param method
     *            请求方法GET/POST
     * @param path
     *            请求路径
     * @param timeout
     *            连接超时时间 默认为5000
     * @param readTimeout
     *            读取超时时间 默认为5000
     * @param
     * @return
     */

    public static String defaultConnection(String method, String path, int timeout, int readTimeout,
                                           String hostName,int port,String isHttps)
            throws Exception {
        String data = "";
        return defaultConnection(method,path,timeout,readTimeout,data,hostName,port,isHttps);
    }


    private static String defaultConnection(String method, String path, int timeout, int readTimeout,
                                           String data,String hostName,int port,String isHttps)
            throws Exception {
        String result = "";
        URL url = new URL(path);
        if (url != null) {
            HttpURLConnection conn = getConnection(method, url,hostName,port,isHttps);
            conn.setConnectTimeout(timeout == 0 ? DEFAULT_CONNTIME : timeout);
            conn.setReadTimeout(readTimeout == 0 ? DEFAULT_READTIME : readTimeout);
            if (data != null && !"".equals(data)) {
                OutputStream output = conn.getOutputStream();
                output.write(data.getBytes(DEFAULT_CHARSET));
                output.flush();
                output.close();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                result = inputToStr(input);
                input.close();
                conn.disconnect();
            }
        }
        return result;
    }

    /**
     * 根据url的协议选择对应的请求方式 例如 http://www.baidu.com 则使用http请求,https://www.baidu.
     *
     *
     * 则使用https请求
     *
     * @param method
     *            请求的方法
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String method, URL url,String hostName,int port,String isHttps) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext context = null;
            try {
                context = SSLContext.getInstance("SSL", "SunJSSE");
                context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },
                        new java.security.SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = null;
            if("true".equalsIgnoreCase(isHttps)) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(hostName, port));
                connHttps = (HttpsURLConnection) url.openConnection(proxy);
            }else {
                connHttps = (HttpsURLConnection) url.openConnection();
            }
            connHttps.setSSLSocketFactory(context.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    /**
     * 将输入流转换为字符串
     *
     * @param input
     *            输入流
     * @return 转换后的字符串
     */
    public static String inputToStr(InputStream input) {
        String result = "";
        if (input != null) {
            byte[] array = new byte[1024];
            StringBuffer buffer = new StringBuffer();
            try {
                for (int index; (index = (input.read(array))) > -1;) {
                    buffer.append(new String(array, 0, index, DEFAULT_CHARSET));
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = "";
            }
        }
        return result;
    }

    /**
     * 设置参数
     *
     * @param map
     *            参数map
     * @param path
     *            需要赋值的path
     * @param charset
     *            编码格式 默认编码为utf-8(取消默认)
     * @return 已经赋值好的url 只需要访问即可
     */
    public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {
        String result = "";
        boolean hasParams = false;
        if (path != null && !"".equals(path)) {
            if (map != null && map.size() > 0) {
                StringBuilder builder = new StringBuilder();
                Set<Map.Entry<String, String>> params = map.entrySet();
                for (Map.Entry<String, String> entry : params) {
                    String key = entry.getKey().trim();
                    String value = entry.getValue().trim();
                    if (hasParams) {
                        builder.append("&");
                    } else {
                        hasParams = true;
                    }
                    if(charset != null && !"".equals(charset)){
                        //builder.append(key).append("=").append(URLDecoder.(value, charset));
                        builder.append(key).append("=").append(urlEncode(value, charset));
                    }else{
                        builder.append(key).append("=").append(value);
                    }
                }
                result = builder.toString();
            }
        }
        return doUrlPath(path, result).toString();
    }

    /**
     * 设置连接参数
     *
     * @param path
     *            路径
     * @return
     */
    private static URL doUrlPath(String path, String query) throws Exception {
        URL url = new URL(path);
        if (StringUtils.isEmpty(path)) {
            return url;
        }
        if (StringUtils.isEmpty(url.getQuery())) {
            if (path.endsWith("?")) {
                path += query;
            } else {
                path = path + "?" + query;
            }
        } else {
            if (path.endsWith("&")) {
                path += query;
            } else {
                path = path + "&" + query;
            }
        }
        return new URL(path);
    }

    /**
     * 默认的http请求执行方法,返回
     *
     * @param method
     *            请求的方法 POST/GET
     * @param path
     *            请求path 路径
     * @param map
     *            请求参数集合
     * @param data
     *            输入的数据 允许为空
     * @return
     */
    public static String HttpDefaultExecute(String method, String path, Map<String, String> map, String data,
                                            String hostName,int port,String isHttps) {
        String result = "";
        try {
            String url = setParmas(map, path, "");
            result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data,hostName,port,isHttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 默认的https执行方法,返回
     *
     * @param method
     *            请求的方法 POST/GET
     * @param path
     *            请求path 路径
     * @param map
     *            请求参数集合
     * @param data
     *            输入的数据 允许为空
     * @return
     */
    public static String HttpsDefaultExecute(String method, String path, Map<String, String> map,
                                             String data,String hostName,int port,String isHttps) {
        String result = "";
        try {
            String url = setParmas( map, path, "");
            result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data,hostName,port,isHttps);
            logger.info("[{}] result +:{}", JsonUtil.toJson(result));
            logger.info("[{}] url +:{}", JsonUtil.toJson(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpsDefaultExecute(String method, String path, String data,String hostName,int port,String isHttps) {
        String result = "";
        try {
            result = defaultConnection(method, path, DEFAULT_CONNTIME, DEFAULT_READTIME, data,hostName,port,isHttps);
            logger.info("[{}] result +:{}", JsonUtil.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String HttpsDefaultExecute(String method, String path, Map<String, String> map,
                                             String hostName,int port,String isHttps) {
        String data = "";

        return HttpsDefaultExecute(method,path,map,data,hostName,port,isHttps);
    }

    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 回调验证签名
     * @param
     * @return
     */
    public boolean isPayResultNotifySignatureValid(Map<String, String> reqData,String key) throws Exception {
        String signTypeInData = reqData.get(WechatCofig.FIELD_SIGN_TYPE);
        SignType signType;
        if (signTypeInData == null) {
            signType = SignType.MD5;
        }
        else {
            signTypeInData = signTypeInData.trim();
            if (signTypeInData.length() == 0) {
                signType = SignType.MD5;
            }
            else if (WechatCofig.MD5.equals(signTypeInData)) {
                signType = SignType.MD5;
            }
            else if (WechatCofig.HMACSHA256.equals(signTypeInData)) {
                signType = SignType.HMACSHA256;
            }
            else {
                throw new Exception(String.format("Unsupported sign_type: %s", signTypeInData));
            }
        }
        return isSignatureValid(reqData, key, signType);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
        if (!data.containsKey(WechatCofig.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WechatCofig.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     * @param data
     * @param key
     * @param signType
     * @return
     * @throws Exception
     */

    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WechatCofig.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


    public static String uploadMaterial(File file, String type, String url,
                                        String title, String introduction, String host, Integer port, String isHttps) {
        String result = null;

        try {
            URL uploadURL = new URL(url);
            HttpURLConnection conn = getConnection(HttpRequestUtil.POST_METHOD, uploadURL, host, port, isHttps);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());
            output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());

            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(file);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }

			/*对类型为video的素材进行特殊处理*/
            if ("video".equals(type)) {
                output.write(("--" + boundary + "\r\n").getBytes());
                output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
                output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title, introduction).getBytes());
            }

            output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();

            InputStream resp = conn.getInputStream();

            StringBuffer sb = new StringBuffer();

            while ((len = resp.read(data)) > -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            resp.close();
            result = sb.toString();
        } catch (IOException e) {
          logger.error("uploadMaterial is error:",e);
        }

        return result;
    }




}
