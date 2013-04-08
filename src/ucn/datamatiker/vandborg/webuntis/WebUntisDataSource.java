package ucn.datamatiker.vandborg.webuntis;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WebUntisDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private WebUntisSQLiteHelper dbHelper;
	private String[] allColumns = {
			WebUntisSQLiteHelper.COLUMN_ID,
			WebUntisSQLiteHelper.COLUMN_SUBJECT,
			WebUntisSQLiteHelper.COLUMN_CLASS,
			WebUntisSQLiteHelper.COLUMN_TEACHER,
			WebUntisSQLiteHelper.COLUMN_CLASSROOM,
			WebUntisSQLiteHelper.COLUMN_FROM,
			WebUntisSQLiteHelper.COLUMN_TO
	};
	
	public WebUntisDataSource(Context context){
		dbHelper = new WebUntisSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public ScheduleElement createScheduleElement(String subject, String scheduleClass, String teacher, String classroom, long from, long to){
		ContentValues values = new ContentValues();
		values.put(WebUntisSQLiteHelper.COLUMN_SUBJECT, subject);
		values.put(WebUntisSQLiteHelper.COLUMN_CLASS, scheduleClass);
		values.put(WebUntisSQLiteHelper.COLUMN_TEACHER, teacher);
		values.put(WebUntisSQLiteHelper.COLUMN_CLASSROOM, classroom);
		values.put(WebUntisSQLiteHelper.COLUMN_FROM, from);
		values.put(WebUntisSQLiteHelper.COLUMN_TO, to);
		
		long insertId = database.insert(WebUntisSQLiteHelper.TABLE_SCHEDULES, null, values);
		Cursor cursor = database.query(WebUntisSQLiteHelper.TABLE_SCHEDULES, allColumns, WebUntisSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ScheduleElement newScheduleElement = cursorToScheduleElement(cursor);
		cursor.close();
		return newScheduleElement;
	}
	
	public void deleteScheduleElement(ScheduleElement scheduleElement){
		long id = scheduleElement.get_id();
		Log.w(WebUntisDataSource.class.getName(), "ScheduleElement deleted with id: " + id);
		database.delete(WebUntisSQLiteHelper.TABLE_SCHEDULES, WebUntisSQLiteHelper.COLUMN_ID + " = " +id, null);
	}
	
	public List<ScheduleElement> getAllScheduleElements() {
		List<ScheduleElement> scheduleElements = new ArrayList<ScheduleElement>();
		
		Cursor cursor = database.query(WebUntisSQLiteHelper.TABLE_SCHEDULES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			ScheduleElement scheduleElement = cursorToScheduleElement(cursor);
			scheduleElements.add(scheduleElement);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return scheduleElements;
	}
	
	private ScheduleElement cursorToScheduleElement(Cursor cursor) {
		ScheduleElement scheduleElement = new ScheduleElement();
		
		scheduleElement.set_id(cursor.getLong(0));
		scheduleElement.set_subject(cursor.getString(1));
		scheduleElement.set_class(cursor.getString(2));
		scheduleElement.set_teacher(cursor.getString(3));
		scheduleElement.set_classroom(cursor.getString(4));
		scheduleElement.set_from(cursor.getLong(5));
		scheduleElement.set_to(cursor.getLong(6));
		
		return scheduleElement;
	}
}
