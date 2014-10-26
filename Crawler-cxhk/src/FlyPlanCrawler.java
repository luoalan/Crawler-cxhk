

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

import java.util.Timer;

import org.jsoup.select.Elements; 



public class FlyPlanCrawler extends BreadthCrawler{


    /*visit函数定制访问每个页面时所需进行的操作*/
    @Override
    public void visit(Page page) {
  	          
    	System.out.println("正在抽取"+page.getUrl());      
        Elements contents=page.getDoc().select("tr[title=点击查询]");                                             
		
    	
            for (int i=0;i<contents.size();i++){

            	/*获取tr属性*/
            	String[] Citycodelist =contents.get(i).attr("name").split("=");
            	
            	/*获取tr下面的Td集合*/
            	Elements tdcontents = contents.get(i).select("td[style=text-align: center;]");           	           	
            	
            	String sql2="insert into cxhkFlyPlan " +
                  		"(OriCity,OriCode,OriTime,DestCity,DestCode,DestTime,Flynumber,DayOWeek,BegPlanDate,EndPlanDate) " +
                  		"values(?,?,?,?,?,?,?,?,?,?)";

                 Object[] obj = new Object[]{tdcontents.get(0).text(),
                		 Citycodelist[0], 
                		 tdcontents.get(1).text(),
                		 tdcontents.get(2).text(),
                		 Citycodelist[1],
                		 tdcontents.get(3).text(),
                		 tdcontents.get(4).text(),
                		 tdcontents.get(5).text(),
                		 tdcontents.get(6).text(),
                		 tdcontents.get(7).text()};
                 
                 DBHelper.executeNonQuery(sql2,obj);

                 System.out.println(tdcontents.get(0).text() +"到" + tdcontents.get(2).text() +"的航班"+tdcontents.get(4).text() + "插入成功！");                  
					      	
            }
          }
 
       // }
    
    /*failed函数定制访问访问失败的动作
     * 定义失败后重试i次
     * @luojiandong
     */
    public void failed(Page page) {
    	int i =0; 
    	System.out.println("爬去失败，正在重试第【" + i +"】次");
    	RemindTask retry = new RemindTask();
    	while(i<=5){
    	retry.run();
    	i++;
      }
    }


    /*启动爬虫*/
    public static void main(String[] args) throws Exception { 
    	
    	final int hours =1000*60*60;
        /*设置递归爬取时每个页面产生的URL数量，这里不需要递归爬取*/  
        Config.topN=0;  
        Timer timer = new Timer();        
		timer.schedule(new RemindTask(), 0,hours*24);
        
        }
    } 
 

