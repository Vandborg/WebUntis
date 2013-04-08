package ucn.datamatiker.vandborg.webuntis;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Scraper testscrape = new Scraper("ucn",1362351600,1570);
		Scraper testscrape = new Scraper("ucn",1362351600,1512);
		List<ScheduleElement> result = testscrape.scrape();
		ScheduleElement schduleElement = result.get(0);
		Log.w("WebUntis", "Subject: "+schduleElement.get_subject());
		Log.w("WebUntis", "Class: "+schduleElement.get_class());
		Log.w("WebUntis", "Teacher: "+schduleElement.get_teacher());
		Log.w("WebUntis", "Classroom: "+schduleElement.get_classroom());
		Log.w("WebUntis", "From: "+schduleElement.get_from().toString());
		Log.w("WebUntis", "To: "+schduleElement.get_to().toString());
		
		/*for(ScheduleElement schduleElement : result){
			Log.w("WebUntis", "Subject: "+schduleElement.get_subject());
			Log.w("WebUntis", "Class: "+schduleElement.get_class());
			Log.w("WebUntis", "Teacher: "+schduleElement.get_teacher());
			Log.w("WebUntis", "Classroom: "+schduleElement.get_classroom());
			Log.w("WebUntis", "From: "+schduleElement.get_from().toString());
			Log.w("WebUntis", "To: "+schduleElement.get_to().toString());
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
