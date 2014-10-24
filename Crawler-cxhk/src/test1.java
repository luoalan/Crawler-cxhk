import java.io.IOException;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;

    public class test1 extends BreadthCrawler{  
      
        /*visit?½æ•°å®šåˆ¶è®¿é—®æ¯ä¸ªé¡µé¢?¶æ????è¡Œç??ä?*/  
        @Override  
        public void visit(Page page) {  
            String question_regex="^http://www.zhihu.com/question/[0-9]+";  
            if(Pattern.matches(question_regex, page.getUrl())){  
                System.out.println("æ­?œ¨?½å?"+page.getUrl());  
                /*?½å??‡é?*/  
                String title=page.getDoc().title();  
                System.out.println(title);  
                /*?½å??é—®?…å®¹*/  
                String question=page.getDoc().select("div[id=zh-question-detail]").text();  
                System.out.println(question);  
      
            }  
        }  
      
        /*?¯åŠ¨?¬è™«*/  
        public static void main(String[] args) throws Exception{    
            test1 crawler=new test1();  
            crawler.addSeed("http://www.zhihu.com/question/21003086");  
            crawler.addRegex("http://www.zhihu.com/.*");  
            crawler.start(5);    
        }  
      
      
    }  