import java.io.IOException;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;

    public class testR extends BreadthCrawler{  
      
        /*visit函数定制访问每个页面时所需进行的操作*/  
        @Override  
        public void visit(Page page) {  
            String question_regex="^http://www.zhihu.com/question/[0-9]+";  
            if(Pattern.matches(question_regex, page.getUrl())){  
                System.out.println("正在抽取"+page.getUrl());  
                /*抽取标题*/  
                String title=page.getDoc().title();  
                System.out.println(title);  
                /*抽取提问内容*/  
                String question=page.getDoc().select("div[id=zh-question-detail]").text();  
                System.out.println(question);  
                System.out.println(question);  
      
            }  
        }         
      
    }  