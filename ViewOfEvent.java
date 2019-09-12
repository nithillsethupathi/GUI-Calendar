
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.SimpleDateFormat;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * EventViewOptionPanel.java
 * @author Dangerous
 */
public class ViewOfEvent extends JPanel implements ChangeListener
{
	private CalendarModel model;
	private Calendar c;
	private ArrayList<Event> elist;
	private ArrayList<Event> alist;
	
	private static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private final int numHrsPerDay = 24;
	private final int numDaysInWeek = 7;
	public enum WeekDays  
	{
		Sunday, Monday, Tuesday, Wednesday, Thurday, Friday, Saturday ;
	}

	/**
	 * Constructs the Panel with different views for day, month, year, and agenda
	 */
	public ViewOfEvent(CalendarModel model)
	{
		this.model = model;
		c = model.getCalendar();
		elist = model.getEventList();
		alist = model.getAgendasList();
		
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		add(getEventInDayView(),BorderLayout.CENTER);
	}

	/**
	 * Returns a day view with all events scheduled on the day
	 * @return a day view with scheduled events
	 */ 
	public JScrollPane getEventInDayView()
	{
		JPanel dayView = new JPanel();
		JLabel[] timeslots = new JLabel[24];
		dayView.setBackground(Color.WHITE);
		dayView.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		for(int i = 0; i < numHrsPerDay; i++)
		{
			LocalTime time = LocalTime.of(i, 0);
			
			JLabel times = new JLabel(time.toString());
			times.setPreferredSize(new Dimension(70, 50));
			times.setBackground(new Color(230, 230, 250));
			times.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
			times.setHorizontalAlignment(JLabel.CENTER);
			
			
			constraints.gridx = 0;
			constraints.gridy = i;
			
			dayView.add(times, constraints);

			timeslots[i] = new JLabel();
			timeslots[i].setPreferredSize(new Dimension(620, 50));
			timeslots[i].setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
			
			
			constraints.gridx = 1;
			constraints.gridy = i;
			
			dayView.add(timeslots[i], constraints);

			alist = model.getAgendasList();
			if(alist.size() == 0)
			{
				for (Event event : elist) 
				{
					
					if (event.getStartHr()== i && event.getMonth() == c.get(Calendar.MONTH)+1 &&
							event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setText(event.getTitle());
						timeslots[i].setForeground(Color.BLACK);
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
					if (event.getEndHr() == i  && event.getMonth() == c.get(Calendar.MONTH)+1 && 
							event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setForeground(Color.BLACK);
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
					if (event.getStartHr() < i  && i < event.getEndHr() && event.getMonth() == c.get(Calendar.MONTH)+1 && 
							event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
				}	
			}
			else
			{
				for (Event event : alist) 
				{
					if (event.getStartHr()== i && event.getMonth() == c.get(Calendar.MONTH)+1 && 
							event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setText(event.getTitle());
						timeslots[i].setForeground(Color.BLACK);
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
					if (event.getEndHr() == i  && event.getMonth() == c.get(Calendar.MONTH)+1 && 
							event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setForeground(Color.BLACK);
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
					if (event.getStartHr() < i  && i < event.getEndHr() &&
							event.getMonth() == c.get(Calendar.MONTH)+1 && event.getDay() == c.get(Calendar.DATE))
					{
						timeslots[i].setBackground(Color.YELLOW);
						timeslots[i].setOpaque(true);
					}
				}	
			}	
		}
		
		JScrollPane dayPane = new JScrollPane();
		
		dayPane = new JScrollPane(dayView);
		dayPane.setPreferredSize(new Dimension(700, 200));
		dayPane.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		
		return dayPane;
	}

	/**
	 * Returns the view of the week and all events scheduled in the week
	 * @return week view of scheduled events
	 */
	public JScrollPane getEventInWeekView()
	{
		JPanel weekView = new JPanel();
		weekView.setLayout(new BorderLayout());

		JPanel header = new JPanel();
		header.setLayout(new GridLayout(0, numDaysInWeek));
		
		Calendar firstdayofweek = (Calendar) c.clone();
		int diffromfirstday = c.get(Calendar.DAY_OF_WEEK);
		firstdayofweek.add(Calendar.DAY_OF_MONTH, -diffromfirstday);
		
		for(int i = 0 ; i < numDaysInWeek; i++)
		{
			JLabel dayinWeek = new JLabel();
			firstdayofweek.add(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat formatter = new SimpleDateFormat("EE d");
			dayinWeek.setText(formatter.format(firstdayofweek.getTime()));
			dayinWeek.setHorizontalAlignment(JLabel.CENTER);
			header.add(dayinWeek);
		}

		JPanel eventholder = new JPanel();
		eventholder.setLayout(new BorderLayout());
		
		JPanel timeofday = new JPanel();
		timeofday.setLayout(new BoxLayout(timeofday, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < numHrsPerDay; i++)	
		{	
			LocalTime time = LocalTime.of(i, 0);
			
			JLabel timeLabel = new JLabel(time.toString());
			timeLabel.setHorizontalAlignment(JLabel.CENTER);
			timeLabel.setBackground(new Color(230, 230, 250));
			timeLabel.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
			timeLabel.setOpaque(true);
			
			timeofday.add(timeLabel);
		}

		JPanel weekPanel = new JPanel();
		weekPanel.setLayout(new GridLayout(24, 7));
		
		JLabel[][] slots = new JLabel[numHrsPerDay][numDaysInWeek];
		for(int i = 0; i < slots.length; i++)
		{
			for(int j = 0; j < slots[0].length; j++)
			{
				slots[i][j]= new JLabel(" ");
				slots[i][j].setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
				slots[i][j].setBackground(Color.WHITE);
				slots[i][j].setOpaque(true);
				weekPanel.add(slots[i][j]);
			}
		
			alist = model.getAgendasList();
			if(alist.size() == 0)
			{
				for (Event event : elist) 
				{
					int temp = event.getEventDayInWeek() - 1;
					if (event.getStartHr() == i && 
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setText(event.getTitle());
						slots[i][temp].setForeground(Color.BLACK);
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					}
					if (event.getEndHr() == i && 
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setForeground(Color.BLACK);
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					} 
					if (event.getStartHr() < i  && i < event.getEndHr() &&
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					}
				}
			}
			else
			{
				
				for (Event event : alist) 
				{
					int temp = event.getEventDayInWeek() - 1;
					if (event.getStartHr() == i && 
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setText(event.getTitle());
						slots[i][temp].setForeground(Color.BLACK);
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					}
					if (event.getEndHr() == i && 
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setForeground(Color.BLACK);
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					} 
					if (event.getStartHr() < i  && i < event.getEndHr() && 
							event.getEventDay().get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR))
					{
						slots[i][temp].setBackground(Color.YELLOW);
						slots[i][temp].setOpaque(true);
					}
				}	
			}
		}

		eventholder.add(timeofday,BorderLayout.WEST);
		eventholder.add(weekPanel,BorderLayout.CENTER);

		weekView.add(header, BorderLayout.NORTH);
		weekView.add(eventholder, BorderLayout.CENTER);
		
		JScrollPane weekPane = new JScrollPane();
		weekPane = new JScrollPane(weekView);
		weekPane.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		weekPane.setPreferredSize(new Dimension(700, 300));
		
		return weekPane;	
	}	

	/**
	 * Returns a view of the month with events in the selected month
	 * @return Panel showing month view with events listed
	 */
	public JPanel getEventInMonthView()
	{
		JPanel monthView = new JPanel();
		monthView.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		monthView.setBackground(Color.WHITE);

		monthView.setLayout(new GridLayout(0,numDaysInWeek));
		
		for(int i = 0; i < DAYS_OF_WEEK.length; i++)
		{ 
			JLabel week = new JLabel(DAYS_OF_WEEK[i],JLabel.LEFT); 
			week.setFont(new Font("TimesRoman", Font.PLAIN,15));
			
			WeekDays [] days = WeekDays .values();
			week.setToolTipText(days[i].toString());
			week.setVerticalAlignment(JLabel.BOTTOM);
			
			monthView.add(week);
		}

		Calendar temp = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),1);
		int emptyDays= temp.get(Calendar.DAY_OF_WEEK)-1;

		for(int i = 0; i < emptyDays; i++)
		{
			JLabel emptyday = new JLabel();
			emptyday.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));	
			
			monthView.add(emptyday);
		}

		int curmonthLength = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		for(int i = 1; i <= curmonthLength; i++)
		{
			JTextArea day = new JTextArea(Integer.toString(i));
			day.setBackground(new Color(230, 230, 250));
			day.setOpaque(true);
			day.setFont(new Font("TimesRoman", Font.BOLD,10));
			day.setEditable(false);
			day.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
			
			Calendar today = new GregorianCalendar();
			
			if(i == today.get(Calendar.DAY_OF_MONTH) && c.get(Calendar.MONTH) == today.get(Calendar.MONTH) 
					&& c.get(Calendar.YEAR) == today.get(Calendar.YEAR))
			{
				day.setForeground(new Color(30,144,255));
			}
			
			day.append("\n");
			
			alist = model.getAgendasList();
			
			if(alist.size() == 0)
			{
				for (Event event : elist) 
				{
					if (i == event.getDay() && event.getMonth() == c.get(Calendar.MONTH) + 1)
					{
						day.append(event.format(new Formation())); // Strategy Pattern
						day.setEditable(false);
						day.setBackground(Color.YELLOW);
						day.setOpaque(true);
					}
				}
			}
			else
			{
				for (Event event : alist) 
				{
					if (i == event.getDay() && event.getMonth() == c.get(Calendar.MONTH)+1)
					{
						//Strategy Pattern
						day.append(event.format(new Formation())); 
						day.setEditable(false);
						day.setBackground(Color.YELLOW);
						day.setOpaque(true);
					}
				}
			}		
			
			day.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						JTextArea source = (JTextArea)e.getSource();
						
						String dayinfo = source.getText();
						int dateindex = dayinfo.indexOf("\n");
						c.set(Calendar.DATE, Integer.parseInt(dayinfo.substring(0,dateindex)));
						
						model.setViewType(EventViewTab.Day);
					}
				});
			
			monthView.add(day);		
		}

		int restEmptyDay = 42 - curmonthLength - emptyDays;
		for(int i = 0; i < restEmptyDay; i++)
		{
			JLabel empty = new JLabel();	
			empty.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));	
			monthView.add(empty);
		}
		
		return monthView;
	}

	/**
	 * Gets all events listed in chronological order
	 * @return JScrollPane with all events listed
	 */ 
	public JScrollPane getEventInAgendaView()
	{
		JPanel agendaView = new JPanel();
		agendaView.setLayout(new BorderLayout());
		
		JLabel aheader = new JLabel("AGENDA", JLabel.CENTER);
		aheader.setFont(new Font("TimesRoman", Font.BOLD, 18));
		aheader.setToolTipText("Click for Agenda Durration");
		aheader.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e)
				{
					new  CheckAgenda(model);
				}
			});
		aheader.setBackground(new Color(230, 230, 250));
		aheader.setOpaque(true);

		JPanel holder =  new JPanel();
		holder.setBackground(Color.WHITE);
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));

		alist = model.getAgendasList();
		for(Event e : alist) 
		{
			JLabel eventtitle = new JLabel( e.toString());
			eventtitle.setHorizontalAlignment(JLabel.CENTER);
			eventtitle.setVerticalAlignment(JLabel.CENTER);
			eventtitle.setBackground(Color.WHITE);
			eventtitle.setOpaque(true);
			eventtitle.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			
			holder.add(eventtitle);
		}
		
		if(alist.size()== 0)
		{
			JLabel noevent = new JLabel("There are no events for the selected period.");
			noevent.setBackground(Color.WHITE);
			noevent.setOpaque(true);
			noevent.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			
			holder.add(noevent);
		}
			
		agendaView.add(aheader, BorderLayout.NORTH);
		agendaView.add(holder, BorderLayout.CENTER);

		JScrollPane agendaPane = new JScrollPane();
		agendaPane = new JScrollPane(agendaView);
		agendaPane.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		agendaPane.setPreferredSize(new Dimension(700, 200));
		
		
		return agendaPane;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		removeAll();
		
		setVisible(false);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		EventViewTab viewtype = model.getViewType();
		if(viewtype == EventViewTab.Day)
		{
			add(getEventInDayView(),BorderLayout.CENTER);
		}
		else if(viewtype == EventViewTab.Week)
		{
			add(getEventInWeekView(),BorderLayout.CENTER);
		}
		else if(viewtype == EventViewTab.Month)
		{
			add(getEventInMonthView(),BorderLayout.CENTER);
		}
		else if(viewtype == EventViewTab.Agenda)
		{
			add(getEventInAgendaView(),BorderLayout.CENTER);
		}
		
		setVisible(true);
	}
}
