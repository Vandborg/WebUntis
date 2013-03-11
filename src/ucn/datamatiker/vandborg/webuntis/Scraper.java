package ucn.datamatiker.vandborg.webuntis;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	private Document doc;
	
	public Scraper(String _school, long _date, int _id) {
		this._school = _school;
		this._date = _date;
		this._id = _id;
	}
	
	@SuppressLint("SimpleDateFormat")
	public List<ScheduleElement> scrape(){
		List<ScheduleElement> result = new ArrayList<ScheduleElement>();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(new java.util.Date((long)_date*1000));
		Log.w("WebUntis",date);
		
		String url = "http://webuntis.dk/WebUntis/lessoninfodlg.do?date="+date+"&starttime=0000&endtime=2359&elemid="+_id+"&elemtype=1&school="+_school;
		Log.w("WebUntis",url);
		
		try{
			doc = Jsoup.connect(url).get();
			Elements tablerows = doc.select("table").select("tr");
			tablerows.remove(tablerows.first());

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
								scheduleElement.set_from(10);
								break;
							case 7: //Dummy time
								scheduleElement.set_to(10);
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
		}
		
		return result;
	}
}
