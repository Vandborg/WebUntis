package ucn.datamatiker.vandborg.webuntis;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UpdateDatabase extends AsyncTask <String, Context, Void> {
	
	private ProgressDialog progressDialog;
	private Context targetCtx;
	private WebUntisDataSource datasource;
	private ArrayList<ScheduleElement> ScheduleElements = new ArrayList<ScheduleElement>();
	
	public UpdateDatabase(Context context){
		this.targetCtx = context;
		this.progressDialog = new ProgressDialog(targetCtx);
		this.progressDialog.setCancelable(false);
		this.progressDialog.setMessage("Retrieving data...");
		this.progressDialog.setTitle("Please wait");
		this.progressDialog.setIndeterminate(true);
	}
	
	
	
	@Override
	protected void onPostExecute(Void result) {
		if(this.progressDialog != null && this.progressDialog.isShowing()){
			this.progressDialog.dismiss();
		}
	}



	@Override
	protected void onPreExecute() {
		this.progressDialog.show();
	}



	@Override
	protected Void doInBackground(String... params) {
		datasource = new WebUntisDataSource(this.targetCtx);
		datasource.open();
		datasource.emptyDatabase();
		Log.d("WebUntis", "databased emptied!");
		Scraper testscrape = new Scraper("ucn",new Date().getTime(),1570,2);
		ScheduleElements = testscrape.scrape();
		
		for(ScheduleElement scheduleElement : ScheduleElements){
			String SUBJECT = scheduleElement.get_subject();
			String CLASS = scheduleElement.get_class();
			String TEACHER = scheduleElement.get_teacher();
			String CLASSROOM = scheduleElement.get_classroom();
			long FROM = scheduleElement.get_from();
			long TO = scheduleElement.get_to();
			
			ScheduleElement newScheduleElement = datasource.createScheduleElement(SUBJECT, CLASS, TEACHER, CLASSROOM, FROM, TO);
		}
		datasource.close();
		return null;
	}
	
}
