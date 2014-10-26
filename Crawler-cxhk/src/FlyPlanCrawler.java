

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Timer;

import org.jsoup.select.Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet; 



public class FlyPlanCrawler extends BreadthCrawler{


    /*visit函数定制访问每个页面时所需进行的操作*/
    @Override
    public void visit(Page page) {
    	          
    	    System.out.println("正在抽取"+page.getUrl());
            
           Elements contents=page.getDoc().select("tr[title=点击查询]");
                        
            
            try {
    			Class.forName("com.mysql.jdbc.Driver");
    			Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/cxhk", "root", "19871229");       	
    		} catch (Exception e) {
    			System.out.println("数据库连接失败" + e.getMessage());
    		}
            
            for (int i=0;i<contents.size();i++){
            	System.out.println(contents.get(i).attr("name"));
            	String[] Citycodelist =contents.get(i).attr("name").split("=");
            	
            	Elements tdcontents = contents.get(i).select("td[style=text-align: center;]");
            	for(int ii=0;ii<tdcontents.size();ii++){
            		
            		System.out.println(tdcontents.get(ii).text());
            	}
            	           			
            			
            	PreparedStatement prestmt = conn.prepareStatement("insert into cxhkFlyPlan " +
               		"(OriCity,OriCode,OriTime,DestCity,DestCode,DestTime,Flynumber,DayOWeek,BegPlanDate,EndPlanDate) " +
               		"values (?,?,?,?,?,?,?,?,?,?)");
               prestmt.setString(1,tdcontents.get(0).text());
               prestmt.setString(5,Citycodelist[0]);
               prestmt.setString(8,tdcontents.get(1).text());
               prestmt.setString(4,tdcontents.get(2).text());
               prestmt.setString(5,Citycodelist[1]);
               prestmt.setString(8,tdcontents.get(3).text());
               prestmt.setString(7,tdcontents.get(4).text());
               prestmt.setString(8,tdcontents.get(5).text());
               prestmt.setString(8,tdcontents.get(6).text());
               prestmt.setString(9,tdcontents.get(7).text());
            	
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
 

