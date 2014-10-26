
import java.util.TimerTask;

public class RemindTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub

		FlyPlanCrawler crawler = new FlyPlanCrawler();
		crawler.addSeed("http://help.ch.com/services/FlightDate");
		crawler.addRegex(".*");
				
		/* 设置User-Agent */		
		crawler.setUseragent("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:26.0) Gecko/20100101 Firefox/26.0");
		try {
			crawler.start(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
