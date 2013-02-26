package ucn.datamatiker.vandborg.webuntis;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Document doc;
		try {
			doc = Jsoup.connect("https://82.103.142.37/WebUntis/Timetable.do?school=ucn&onlyTimetable=true&ajaxCommand=renderTimetable&date=20130304&type=1&id=1570&formatId=1&departmentId=0&buildingId=-1&dojo.preventCache=1361793330275").userAgent("Mozilla").get();
			Log.w("WebUntis", doc.html().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.w("WebUntis", e.toString());
			Log.w("WebUntis", e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
