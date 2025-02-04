package co.wali;

import edu.duke.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part4 {

    public void run(){
        System.out.println(readUrlByJsoup("https://www.dukelearntoprogram.com/course2/data/manylinks.html"));
//        System.out.println(findYoutube("https://www.youtube.com/watch?v=ji5_MqicxSo"));
        /*System.out.println("Print URL........");
     */   printUrls("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
    }


    private void printUrls(String url) {
        URLResource myurl = new URLResource(url);
        for(String word : myurl.words()) {
            if(word.toLowerCase().indexOf("youtube.com") != -1) {
                System.out.println(word);

            }

        }
    }

    private void testUrl() {
        printUrls("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
    }



    private String readUrlByJsoup(String str){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document doc = Jsoup.connect(str).get();
            Elements links = doc.select("a");

            String textContent = doc.body().html();
            for (Element el : links  ) {
                if (el.attr("href").toLowerCase().contains("youtube")){
                    System.out.println(el.attr("href"));
                }
            }
        }catch (Error | IOException error){
            return error.getMessage();
        }
        return stringBuilder.toString();
    }


    private String findYoutube(String str){
        String keyword = "youtube.com";
        int start = str.indexOf(keyword);
        int end = start+(keyword.length());
        System.out.println(str.lastIndexOf(keyword,5));
        return "\""+str.substring(start,end)+"\"";
    }
}
