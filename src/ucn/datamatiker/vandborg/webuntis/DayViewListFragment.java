package ucn.datamatiker.vandborg.webuntis;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class DayViewListFragment extends ListFragment {
	
	ArrayAdapter<ScheduleElement> aa;
	ArrayList<ScheduleElement> ScheduleElements = new ArrayList<ScheduleElement>();
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		int layoutId = android.R.layout.simple_list_item_1;
		Scraper testscrape = new Scraper("ucn",1362351600,1570);
		ScheduleElements = testscrape.scrape();
		
		aa = new ArrayAdapter<ScheduleElement>(getActivity(),layoutId,ScheduleElements);
		setListAdapter(aa);
	}
	
}
