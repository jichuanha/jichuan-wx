package com.hzkans.crm.modules.wxapi.utils;

import java.util.SortedMap;
import java.util.TreeMap;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发送红包工具类
 * @author lizg
 */
public class WXRedPackUtils {

    public static String createSign(Map<String, Object> map) {
        SortedMap<String, String> packageParams = new TreeMap<>();

        for (Map.Entry<String, Object> m : map.entrySet()) {
            packageParams.put(m.getKey(), m.getValue().toString());
        }

        StringBuffer sb = new StringBuffer();
        Set<?> es = packageParams.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!StringUtils.isEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WechatCofig.WECHAT_H5_PARTNER_KEY);

        String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
        return sign;
    }

    public static String doSendMoney(String url, String data,String hostName,int port,String isHttps) throws Exception {

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        String certDir = WechatCofig.KEYSTORE_FILE +"apiclient_cert.p12";
        FileInputStream instream = new FileInputStream(new File(certDir));//P12文件目录
        try {
            keyStore.load(instream, WechatCofig.MCH_ID.toCharArray());//默认密码为微信支付商户号
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WechatCofig.MCH_ID.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = null;

         //true 表示启用代理，false 表示停用代理服务器
        if (!"false".equals(isHttps)) {
            //用代理
            httpclient = proxy(hostName, port).setSSLSocketFactory(sslsf).build();
        }else{
            //不用代理
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     * 设置代理
     * @param hostOrIP
     * @param port
     */
    public static HttpClientBuilder proxy(String hostOrIP, int port){
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return HttpClients.custom().setRoutePlanner(routePlanner);
    }

    /**
     * 随机16为数值
     * @return
     */
    public static String buildRandom() {

        //生成3位随机数
        int rRum = new Random().nextInt(900)+100;

        //当前时间的13位数
        String time = String.valueOf(System.currentTimeMillis())+String.valueOf(rRum);

        return time;
    }

    /**
     * 生成订单号
     * @return
     */
    public static String getOrderNo() {
        String order = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            order += r.nextInt(9);
        }
        return order;
    }

    /**
     * 创建签名所需要的参数xml格式
     * @param map
     * @return
     */
    public static String createXML(Map<String, Object> map){
        String xml = "<xml>";
        Set<String> set = map.keySet();
        Iterator<String> i = set.iterator();
        while(i.hasNext()){
            String str = i.next();
            xml+="<"+str+">"+"<![CDATA["+map.get(str)+"]]>"+"</"+str+">";
        }
        xml+="</xml>";
        return xml;
    }

    public static void main(String[] args) {
        System.out.println(getOrderNo());
    }
}
