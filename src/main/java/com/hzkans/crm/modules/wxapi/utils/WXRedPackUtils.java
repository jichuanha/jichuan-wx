package com.hzkans.crm.modules.wxapi.utils;

import java.io.*;
import java.security.SecureRandom;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import com.hzkans.crm.modules.wxapi.constants.WxPayConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jc
 */
public class WXRedPackUtils {

    private static final Logger logger = LoggerFactory.getLogger(WXRedPackUtils.class);

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

    public static String getWxParamSign(Map<String, String> paramMap) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }
        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=")
                .append(WxPayConfig.WECHAT_H5_PARTNER_KEY).toString();
        return DigestUtils.md5Hex(toSignStr).toUpperCase();
    }

    /**
     * 不需要证书
     * @param url
     * @param data
     * @param hostName
     * @param port
     * @param isHttps
     * @return
     * @throws Exception
     */
    public static String reqeustOnceNotUserCert(String url, String data,String hostName,int port,String isHttps) throws Exception{
        return requestOnce(url, data, false, hostName, port, isHttps);
    }

    /**
     * 需要证书
     * @param url
     * @param data
     * @param hostName
     * @param port
     * @param isHttps
     * @return
     * @throws Exception
     */
    public static String reqeustOnceUserCert(String url, String data,String hostName,int port,String isHttps) throws Exception{
        return requestOnce(url, data, true, hostName, port, isHttps);
    }

    public static String requestOnce(String url, String data, boolean userCert,String hostName,int port,String isHttps) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if(userCert) {
            //加载证书
            String certDir = WechatCofig.KEYSTORE_FILE +"apiclient_cert.p12";
            FileInputStream instream = new FileInputStream(new File(certDir));//P12文件目录
            char[] password = WechatCofig.MCH_ID.toCharArray();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try {
                ks.load(instream, password);
            } finally {
                instream.close();
            }
            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslsf)
                            .build(),
                    null,
                    null,
                    null);
        }else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }


        HttpClient httpClient = null;
         //true 表示启用代理，false 表示停用代理服务器
        if (!"false".equals(isHttps)) {
            //用代理
            httpClient = proxy(hostName, port).setConnectionManager(connManager).build();
        }else{
            //不用代理
            httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
        }

        HttpPost httpost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpost.setEntity(postEntity);
        httpost.addHeader("Content-Type", "text/xml");
        httpost.addHeader("User-Agent", WechatCofig.USER_AGENT + " " + WechatCofig.MCH_ID);
        HttpResponse httpResponse = httpClient.execute(httpost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");

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

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            logger.warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }

    /**
     * 根据预支付id 生成包含所有必须参数的map对象 返回给前端jssdk使用
     *
     * @param prepayId
     * @return
     * @throws Exception
     */
    public static  Map<String, String> getClientPrepayMap(String prepayId, String nonceStr) throws Exception {
        Map<String, String> map = new TreeMap<>();
        map.put("appId", "wxc34e2d05619f3915");
        map.put("timeStamp", String.valueOf(getSecondTimestamp(new Date())));
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", "MD5");
        String sign = getWxParamSign(map);
        map.put("paySign", sign);
        return map;
    }
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip) ? ip : request.getRemoteAddr();
        }
    }

    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(getOrderNo());
    }
}
