package ucn.datamatiker.vandborg.webuntis;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.util.Log;

public class Scraper {
	private String _school;
	private long _date;
	private int _id;
	private int _howManyDaysToScrape;
	private Document doc;
	
	public Scraper(String _school, long _date, int _id, int _howManyDaysToScrape) {
		this._school = _school;
		this._date = _date;
		this._id = _id;
		this._howManyDaysToScrape = _howManyDaysToScrape;
	}
	
	@SuppressLint("SimpleDateFormat")
	public ArrayList<ScheduleElement> scrape(){
		ArrayList<ScheduleElement> result = new ArrayList<ScheduleElement>();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		Log.d("WebUntis", Long.toString(new Date().getTime())+" test");
		
		for(int i=0;i<_howManyDaysToScrape;i++){
			long scrapeDate = _date+(86400000*i);
			String date = df.format(new java.util.Date((long)scrapeDate));
			Log.w("WebUntis",Long.toString(scrapeDate));
			Log.w("WebUntis",date);
			
			String url = "http://webuntis.dk/WebUntis/lessoninfodlg.do?starttime=0000&endtime=2359&elemtype=1&date="+date+"&elemid="+_id+"&school="+_school;
			Log.w("WebUntis",url);
			
			try{
				doc = Jsoup.connect(url).get();
				Elements tablerows = doc.select("table").select("tr");
				tablerows.remove(tablerows.first());
				DateFormat Dateformat = new SimpleDateFormat("yyyyMMdd HH:mm");
	
				for(Element tableRow : tablerows){
					int counting = 0;
					ScheduleElement scheduleElement = new ScheduleElement();
					Elements tableFields = tableRow.select("td");
					for(Element tableField : tableFields){
						if(tableField.hasText()){
							switch(counting) {
								case 0:
									scheduleElement.set_subject(tableField.text());
									break;
								case 1:
									scheduleElement.set_class(tableField.select("span").text());
									break;
								case 3:
									scheduleElement.set_teacher(tableField.select("span").text());
									break;
								case 4:
									scheduleElement.set_classroom(tableField.text());
									break;
								case 6: //Dummy time
									Date fromTime = Dateformat.parse(date+" "+tableField.text());
									scheduleElement.set_from(fromTime.getTime());
									//scheduleElement.set_from(10);
									break;
								case 7: //Dummy time
									Date toTime = Dateformat.parse(date+" "+tableField.text());
									scheduleElement.set_to(toTime.getTime());
									//scheduleElement.set_to(10);
									break;
								default:
									break;
							}
						}
						counting++;
					}
					
					if(scheduleElement != null){
						
						result.add(scheduleElement);
					}
				}
			} catch (IOException e){
				Log.w("WebUntis", e.toString());
				Log.w("WebUntis", e.getMessage());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("WebUntis", Long.toString(new Date().getTime())+" test");
		return result;
	}
}
