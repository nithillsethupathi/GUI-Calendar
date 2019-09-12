
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * Event calss to store events
 * @author Dangerous
 */
public class Event implements Comparable<Event>
{
	private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private String title;
	
	private int month;
	private int day;
	private int year;
	
	private String startTime;
	private String endTime;
	
	private Calendar c;
	
/**
 * Constructs an event with a title, year, month, day, starting time, and ending time
 */
	public Event(String title, int year, int month, int day, String startTime, String endTime) 
	{
		this.title = title;
		this.day = day;
		this.month = month;
		this.year = year;
		this.startTime = startTime;
		this.endTime = endTime;
		c = new GregorianCalendar();
		c.set(year, month-1, day);
	}
	
	/**
	 * Returns the title of the event
	 * @return title of the event
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Returns the numeric day of the event date 
	 * @return day of event date
	 */
	public int getDay()
	{
		return day;
	}
	
	/**
	 * Returns the numeric month of the event date
	 * @return month of the event date
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * Returns the year of the event date
	 * @return year of the event date
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * Returns start time of the event
	 * @return event start time
	 */
	public String getStartTime()
	{
		return startTime;
	}

	/**
	 * Returns end time of the event
	 * @return the end time of the event
	 */
	public String getEndTime()
	{
		return endTime;
	}
	
	/**
	 * Returns the hour of the start time
	 * @return integer of the starting time hour
	 */
	public int getStartHr()
	{
		return Integer.parseInt(startTime.substring(0,2));
	}

	/**
	 * Returns the hour of the end time
	 * @return integer of the ending time hour
	 */
	public int getEndHr()
	{
		return Integer.parseInt(endTime.substring(0,2));
	}

	/**
	 * Returns an integer representing the week of the month the event is on
	 * @return week of the month of the event
	 */
	public int getEventWeek()
	{	
		return c.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * Returns an integer representing the day of the week the event is on
	 * @return day of the week of the event 
	 */
	public int getEventDayInWeek()
	{
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Returns a Calendar on the event date
	 * @return Calendar object of the event
	 */
	public Calendar getEventDay()
	{
		return c;
	}
	
	/**
	 * Returns the short form of title of event
	 * @return short form of title of event
	 */
	public String format(TitleFormation format)
	{
		return format.titleFormat() + title.substring(0, 4) + " ";
	}
	
	@Override
	public boolean equals(Object x)
	{
		if(x == null)
			return false;
		if(this == x)
			return true;
		if(this.getClass() == x.getClass())
		{
			Event that = (Event) x;
			return this.compareTo(that) == 0;	
		}
		return false;
	}

	@Override
	public int compareTo(Event other) 
	{
		int compyear = this.getYear() - other.getYear();
		if(compyear == 0)
		{
			return this.dateWeight() - other.dateWeight();
		}
		return compyear;
	}
	/**
	 * Helper method to compare 2 dates
	 * @return integer representing "weight" of date for compareTo method
	 */
	private int dateWeight()
	{
		int monthweight = getMonth() * 1000000;
		int dayweight = getDay() * 10000;
		int startweight = getStartHr() * 100;
		
		return monthweight + dayweight + startweight + getEndHr();
	}
	
	@Override
	public String toString ()
	{
		return "   " + title + "  " + startTime + "-"+ endTime + ", " + (MONTHS[month-1]) +" " + day + ", " + year;
	}
	

}



