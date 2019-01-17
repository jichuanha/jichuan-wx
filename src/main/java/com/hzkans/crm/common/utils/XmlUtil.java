package com.hzkans.crm.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XmlUtil {

    public static String map2XmlStr(Map<String, String> data) {
        SAXWriter sw = new SAXWriter();
        Document document = new DOMDocument();
        document.setName("123");

        document.addElement("xml");
        Element rootElement = document.getRootElement();
        for (Entry entry : data.entrySet()) {
            Element element = new DOMElement((String) entry.getKey());
            element.setText((String) entry.getValue());
            rootElement.add(element);
        }
        return document.asXML();
    }

    public static Map<String, String> xmlStr2Map(String xmlData) {
        Map data = new HashMap();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlData);
        } catch (Exception e) {
        }
        Element rootElement = doc.getRootElement();
        List<Element> elementList = rootElement.elements();
        for (Element element : elementList) {
            data.put(element.getName(), element.getText());
        }
        return data;
    }
}
