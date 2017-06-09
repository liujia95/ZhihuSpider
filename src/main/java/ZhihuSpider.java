package main.java;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class ZhihuSpider {

    private String rootUrl = "https://www.zhihu.com";

    private int count;

    public void getZhihuUrls(){
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";
        Pattern p = Pattern.compile(regex);
        try {
            url = new URL(rootUrl+"/collection/31322066");
            urlconn = url.openConnection();
            pw = new PrintWriter(new FileWriter("d:/zhihu_urls.txt"), true);//这里我们把收集到的链接存储在了E盘底下的一个叫做url的txt文件中
            br = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
//                Matcher buf_m = p.matcher(buf);
//                while (buf_m.find()) {
//                    pw.println(buf_m.group());
//                }
                String secondUrl = getSecondLink(buf);
                if(!TextUtils.isEmpty(secondUrl)){
                    writeHtml2File((count++)+".html",secondUrl);
                }
                pw.println(buf);
            }
            System.out.println("获取成功！");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }

    private void writeHtml2File(String fileName,String urlStr){
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";
        Pattern p = Pattern.compile(regex);
        try {
            url = new URL(urlStr);
            urlconn = url.openConnection();
            pw = new PrintWriter(new FileWriter("D:\\second/"+fileName), true);//这里我们把收集到的链接存储在了E盘底下的一个叫做url的txt文件中
            br = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
//                Matcher buf_m = p.matcher(buf);
//                while (buf_m.find()) {
//                    pw.println(buf_m.group());
//                }
                pw.println(buf);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }

    private String getSecondLink(String str){
        String url = "";
        String startMatchStr = "<link itemprop=\"url\" href=\"";
        String endMatchStr="\">";
        if(str.contains(startMatchStr) && str.contains("question")){
            url =rootUrl+ str.substring(startMatchStr.length(),str.indexOf(endMatchStr));
            System.out.println("url:"+url);
        }
        return url;
    }

}
