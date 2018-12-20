//package com.hzkans.zj.test;
//
//import org.junit.After;
//import org.junit.Before;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class BaseTest {
//    ClassPathXmlApplicationContext context = null;
//
//    @Before
//    public void setUp() throws Exception {
//
//        String configDir = "E:\\workspace\\dongyin-CRM\\src\\main\\filters";
//
//        File path = new File(configDir);
//        if (!(path.exists() && path.isDirectory())) {
//            System.out.println("Valid cofig path must be in params, startup process was terminated.");
//        }
//
//        Properties p = new Properties();
//        p.putAll(System.getProperties());
//        try {
//            InputStream is = new FileInputStream(configDir + "/filter-local.properties");
//            p.load(is);
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.setProperties(p);
//        System.out.println("--------初始化---------");
//        context = new ClassPathXmlApplicationContext(
//                new String[] { "spring-context.xml" });
//        context.start();
//        String[] beans = context.getBeanDefinitionNames();
//        for (String bean : beans) {
//            System.out.println(bean);
//        }
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        context.close();
//    }
//}
