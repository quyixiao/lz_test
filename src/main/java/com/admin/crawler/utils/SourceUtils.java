package com.admin.crawler.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SourceUtils {


    public static List<String> getFileContents(String classpath) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        List<String> list = new ArrayList<>();
        try {
            Resource[] metaInfResources = resourcePatternResolver.getResources("classpath*:code/**/*.tsh");
            for (Resource r : metaInfResources) {
                list.add(getContent(r));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getContent(Resource resource) {
        URLConnection conn = null;
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = resource.getURL();//path为url路径
            conn = url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream(); // 得到网络返回的输入流
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                sb.append(s).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                isr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
