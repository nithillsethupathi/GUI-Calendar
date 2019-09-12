
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * CalendarModel class models the calendar and sets up the data structures for the requirements
 * @author Dangerousgit
 */
public class CalendarModel {
    private Calendar calendar;
    private ArrayList<ChangeListener> changeListeners;
    private ArrayList<Event> events;
    private ArrayList<Event> agendas;
    private EventViewTab view;

    /**
     * Constructor that CalendarModel class
     */
    public CalendarModel() {
        calendar = new GregorianCalendar();
        changeListeners = new ArrayList<>();
        events = new ArrayList<>();
        agendas = new ArrayList<>();
        view = EventViewTab.Day;
    }

    /**
     * Getter for Calendar
     *
     * @return calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * attach method attaches the ChangeListener to the model
     *
     * @param changeListener ChangeListener object being passed
     */
    public void attach(ChangeListener changeListener) {
        changeListeners.add(changeListener);
    }

    /**
     * Sets the Date
     *
     * @param year  the calendar year
     * @param month the calendar month
     * @param day  the calendar day
     */
    public void setDate(int year, int month, int day) {
        calendar.set(year, month, day);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the previous day
     */
    public void setPreviousDay() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the next day
     */
    public void setNextDay() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the previous week
     */
    public void setPreviousWeek() {
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the next week
     */
    public void setNextWeek() {
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the previous month
     */
    public void setPreviousMonth() {
        calendar.add(Calendar.MONTH, -1);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Sets the calendar to the next month from the current date selected
     */
    public void setNextMonth() {
        calendar.add(Calendar.MONTH, 1);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }


    /**
     * Gets the list of Agendas
     *
     * @return the list of agendas
     */
    public ArrayList<Event> getAgendasList() {
        return agendas;
    }

    /**
     * Sets the agenda list using the arrayList of events
     *
     * @param events the arrayList of events
     */
    public void setAgendasList(ArrayList<Event> events) {
        TreeSet<Event> events1 = new TreeSet<>(events);
        ArrayList<Event> sortedEvents = new ArrayList<>(events1);
        agendas = sortedEvents;
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Getter for view
     *
     * @return the view
     */
    public EventViewTab getViewType() {
        return view;
    }

    /**
     * Setter for view
     *
     * @param view the view
     */
    public void setViewType(EventViewTab view) {
        this.view = view;
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Gets an array of events that in the a date range
     *
     * @param sYear start Year of the event
     * @param sMonth start Month of the event
     * @param sDay start Day of the event
     * @param eYear end Year of the event
     * @param eMonth end Month of the event
     * @param eDay end Day of the event
     * @return the array of events within the given date range
     */
    public ArrayList<Event> getEventInRange(int sYear, int sMonth, int sDay, int eYear, int eMonth, int eDay) {
        ArrayList<Event> eventInRange = new ArrayList<>();
        for (Event event : events) {
            if (event.getYear() >= sYear && event.getYear() <= eYear) {
                if ((!(event.getMonth() < sMonth && event.getYear() == sYear)) && (!(event.getMonth() > eMonth && event.getYear() == eYear))) {
                    if ((!(event.getDay() < sDay && event.getMonth() == sMonth)) && (!(event.getDay() > eDay && event.getMonth() == eMonth))) {
                        eventInRange.add(event);
                    }
                }
            }
        }
        return eventInRange;
    }

    /**
     * Gets the list of the event
     *
     * @return the list of the event
     */
    public ArrayList<Event> getEventList() {
        return events;
    }

    /**
     * Adds an event to the event list and updates the ChangeListener
     *
     * @param event Event object that is passed to be added
     */
    public void updateEventList(Event event) {
        events.add(event);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Checks if the event can be added to the list
     *
     * @param e Event object being passed
     * @return false if there is a conflict, else return true
     */
    public boolean addEvent(Event e) {
        int start = Integer.parseInt(e.getStartTime().substring(0, 2) + e.getStartTime().substring(3));
        for (Event event : events) {
            String startTime = event.getStartTime().substring(0, 2) + event.getStartTime().substring(3);
            int stTime = Integer.parseInt(startTime);
            String endTime = event.getEndTime().substring(0, 2) + event.getEndTime().substring(3);
            int enTime = Integer.parseInt(endTime);
            if (event.getYear() == event.getYear() && event.getMonth() == event.getMonth() && event.getDay() == event.getDay()) {
                if (start >= stTime && start < enTime) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * getWeekDay class returns the day of the week
     *
     * @param year year set by the calendar
     * @param month month set by the calendar
     * @param day day set by the calendar
     * @return character
     */
    public String getWeekday(int year, int month, int day) {
        calendar.set(year, month - 1, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "S";
            case 2:
                return "M";
            case 3:
                return "T";
            case 4:
                return "W";
            case 5:
                return "H";
            case 6:
                return "F";
            case 7:
                return "A";
            default:
                return null;
        }
    }

    /**
     * Sets event array list
     *
     * @param string scanned string from file
     */
    public void set(String string) {
        String s = string.substring(0, string.indexOf(";"));
        string = string.substring(string.indexOf(";") + 1);
        int parseInt = Integer.parseInt(string.substring(0, string.indexOf(";")));
        string = string.substring(string.indexOf(";") + 1);
        int parseInt1 = Integer.parseInt(string.substring(0, string.indexOf(";")));
        string = string.substring(string.indexOf(";") + 1);
        int parseInt2 = Integer.parseInt(string.substring(0, string.indexOf(";")));
        string = string.substring(string.indexOf(";") + 1);
        String s1 = string.substring(0, string.indexOf(";"));
        string = string.substring(string.indexOf(";") + 1);
        String s2 = string.substring(0, string.indexOf(";"));
        string = string.substring(string.indexOf(";") + 1);
        String s3 = string.substring(0, string.indexOf(";"));

        for (int i = parseInt1; i <= parseInt2; i++) {
            calendar.set(parseInt, i - 1, 1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int j = 1; j <= daysInMonth; j++) {
                if (s1.contains(getWeekday(parseInt, i, j))) {
                    events.add(new Event(s, parseInt, i, j, s2 + ":00", s3 + ":00"));
                }
            }
        }
        for (ChangeListener l : changeListeners) {
            l.stateChanged(new ChangeEvent(this));
        }
    }


}
