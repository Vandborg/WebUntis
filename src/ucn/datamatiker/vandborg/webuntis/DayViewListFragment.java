package ucn.datamatiker.vandborg.webuntis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DayViewListFragment extends ListFragment {
	
	private WebUntisDataSource datasource;
	private ArrayAdapter<ScheduleElement> aa;
	private List<ScheduleElement> result = new ArrayList<ScheduleElement>();
	private long date;
	private Handler handler = new Handler();
	private Boolean refreshThreadRun = true;
	private UpdateDatabase task = null; 
	ArrayList<ScheduleElement> ScheduleElements = new ArrayList<ScheduleElement>();
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		task = new UpdateDatabase(getActivity());
		if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
			task.execute();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		aa = new ArrayAdapter<ScheduleElement>(getActivity(),android.R.layout.simple_list_item_1,result);
		setListAdapter(aa);
		datasource = new WebUntisDataSource(getActivity());
		datasource.open();
		date = new Date().getTime();
		Thread t = new Thread(new Runnable(){
			public void run(){
				while(refreshThreadRun){
					try {
						if(task.getStatus() == AsyncTask.Status.FINISHED || task.getStatus() == AsyncTask.Status.PENDING){
							refreshDay();
							refreshThreadRun = false;
						}
						Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(1));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		t.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		datasource.close();
	}

	private void refreshDay() {
		result.clear();
		List<ScheduleElement> dbResult = datasource.getAllScheduleElementThisDate(date);
		if(!dbResult.isEmpty()){
			for(ScheduleElement element : dbResult){
				result.add(element);
			}
		} else {
			ScheduleElement noDataElement = new ScheduleElement();
			noDataElement.set_subject("No data, Refresh");
			result.add(noDataElement);
		}
		Log.w("WebUntis", "test: "+Long.toString(date));
		handler.post(new Runnable(){
			public void run(){
				Log.d("WebUntis", "Runned!");
				aa.notifyDataSetChanged();
			}
		});
	}
	
}
