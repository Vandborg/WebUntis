package ucn.datamatiker.vandborg.webuntis;

public class ScheduleElement {
	private String _subject;
	private String _class;
	private String _teacher;
	private String _classroom;
	private long _from; //Unixtimestamp
	private long _to; //Unixtimestamp
	
	public ScheduleElement() {
		this._subject = null;
		this._class = null;
		this._teacher = null;
		this._classroom = "default";
		this._from = -1;
		this._to = -1;
	}
	
	public ScheduleElement(String _subject, String _class, String _teacher,
			String _classroom, long _from, long _to) {
		this._subject = _subject;
		this._class = _class;
		this._teacher = _teacher;
		this._classroom = _classroom;
		this._from = _from;
		this._to = _to;
	}

	public String get_subject() {
		return _subject;
	}

	public void set_subject(String _subject) {
		this._subject = _subject;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String get_teacher() {
		return _teacher;
	}

	public void set_teacher(String _teacher) {
		this._teacher = _teacher;
	}

	public String get_classroom() {
		return _classroom;
	}

	public void set_classroom(String _classroom) {
		this._classroom = _classroom;
	}

	public Long get_from() {
		return _from;
	}

	public void set_from(long _from) {
		this._from = _from;
	}

	public Long get_to() {
		return _to;
	}

	public void set_to(long _to) {
		this._to = _to;
	}
}
