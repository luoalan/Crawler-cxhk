import java.io.IOException;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;

    public class test1 extends BreadthCrawler{  
      
        /*visit?�数定制访问每个页面?��????行�??��?*/  
        @Override  
        public void visit(Page page) {  
            String question_regex="^http://www.zhihu.com/question/[0-9]+";  
            if(Pattern.matches(question_regex, page.getUrl())){  
                System.out.println("�?��?��?"+page.getUrl());  
                /*?��??��?*/  
                String title=page.getDoc().title();  
                System.out.println(title);  
                /*?��??�问?�容*/  
                String question=page.getDoc().select("div[id=zh-question-detail]").text();  
                System.out.println(question);  
      
            }  
        }  
      
        /*?�动?�虫*/  
        public static void main(String[] args) throws Exception{    
            test1 crawler=new test1();  
            crawler.addSeed("http://www.zhihu.com/question/21003086");  
            crawler.addRegex("http://www.zhihu.com/.*");  
            crawler.start(5);    
        }  
      
      
    }  