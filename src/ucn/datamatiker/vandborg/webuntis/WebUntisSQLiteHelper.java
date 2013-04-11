package ucn.datamatiker.vandborg.webuntis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WebUntisSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_SCHEDULES = "schedules";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SUBJECT = "subject";
	public static final String COLUMN_CLASS = "class";
	public static final String COLUMN_TEACHER = "teacher";
	public static final String COLUMN_CLASSROOM = "classroom";
	public static final String COLUMN_FROM = "WU_from";
	public static final String COLUMN_TO = "WU_to";
	
	private static final String DATABASE_NAME = "webuntis.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SCHEDULES + "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_SUBJECT + " text, "
			+ COLUMN_CLASS + " text, "
			+ COLUMN_TEACHER + " text, "
			+ COLUMN_CLASSROOM + " text, "
			+ COLUMN_FROM + " integer not null, "
			+ COLUMN_TO + " integer not null);";
	
	public WebUntisSQLiteHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.w(WebUntisSQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		                + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
		onCreate(db);
	}

}
