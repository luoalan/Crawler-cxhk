

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;

import org.jsoup.select.Elements;

import java.sql.PreparedStatement;
import java.sql.ResultSet; 



public class FlyPlanCrawler extends BreadthCrawler{


    /*visit函数定制访问每个页面时所需进行的操作*/
    @Override
    public void visit(Page page) {
    	
    	    Connection conn=null;
    	          
    	    System.out.println("正在抽取"+page.getUrl());
            
           Elements contents=page.getDoc().select("tr[title=点击查询]");
                        
            
            try {
    			Class.forName("com.mysql.jdbc.Driver");
    			 conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/cxhk", "root", "19871229");       	
    		} catch (Exception e) {
    			System.out.println("数据库连接失败" + e.getMessage());
    		}
            
            PreparedStatement ps = null;  
        	try {
				ps = conn.prepareStatement("insert into cxhkFlyPlan" +" "+
						"(OriCity,OriCode,OriTime,DestCity,DestCode,DestTime,Flynumber,DayOWeek,BegPlanDate,EndPlanDate)"
						+ "values (?,?,?,?,?,?,?,?,?,?);");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            for (int i=0;i<contents.size();i++){

            	/*获取tr属性*/
            	String[] Citycodelist =contents.get(i).attr("name").split("=");
            	
            	/*获取tr下面的Td集合*/
            	Elements tdcontents = contents.get(i).select("td[style=text-align: center;]");
            	
            	/*for(int ii=0;ii<tdcontents.size();ii++){
            		
            		System.out.println(tdcontents.get(ii).text());
            	}*/
            	
            	try {
					ps.setString(1,tdcontents.get(0).text());
					ps.setString(5,Citycodelist[0]);
	            	ps.setString(8,tdcontents.get(1).text());
	            	ps.setString(4,tdcontents.get(2).text());
	            	ps.setString(5,Citycodelist[1]);
	            	ps.setString(8,tdcontents.get(3).text());
	            	ps.setString(7,tdcontents.get(4).text());
	            	ps.setString(8,tdcontents.get(5).text());
	            	ps.setString(8,tdcontents.get(6).text());
	            	ps.setString(9,tdcontents.get(7).text());
	            	ps.addBatch();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            	
            	
            }
            
            /*执行SQL语句*/
            try {
				ps.executeBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}                                 
 
        }
    
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
 

