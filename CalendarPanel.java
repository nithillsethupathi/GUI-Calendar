
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * create a Panels to display calendar attaching the listeners to the buttons
 * @author Dangerous
 */
public class CalendarPanel extends JPanel implements ChangeListener
{
	
	public enum WeekDays  
	{
		Sunday, Monday, Tuesday, Wednesday, Thurday, Friday, Saturday ;
	}
	public enum Month 
	{
		January, Feburary, March, April, May, June, July, August, September, October, November, December;
	}
	private static final String[] WEEK_DAY = {"S ", "M ","T ", "W ", "T ", "F ", "A"};
	
	private JPanel monthYear;
	private JPanel calendarContainer;
	private JPanel calendarpanel;
	private CalendarModel calendarModel;
	private Calendar calendar;

	/**
	 * create  the CalendarPanel holding CalendarModel
	 * @param cm the CalendarModel 
	 */
	public CalendarPanel(CalendarModel calendarModel)
	{
		this.calendarModel = calendarModel;
		calendar = calendarModel.getCalendar();
		setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		add(getCalendarContainer());
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 *create the Jlabel on the Jpanel of the month and year view of calendar 
	 * @return the Jlabel on the Jpanel of the month and year view of calendar 
	 */
	public JPanel getMonthAndYear()
	{
		monthYear = new JPanel();
		monthYear.setLayout(new BorderLayout());
		monthYear.setPreferredSize(new Dimension(250, 40));
		monthYear.setBackground(Color.WHITE);
		setOpaque(true);

		Month[] months = Month.values();
		JLabel monthAndYear = new JLabel("  " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));
		monthAndYear.setFont(new Font("TimesRoman", Font.BOLD, 14));

		JLabel pAndn 	= new JLabel();
		pAndn.setPreferredSize(new Dimension(80, 40));
		pAndn.setLayout(new BorderLayout());

		JLabel PreviousButton    = new JLabel("    <    ");
		PreviousButton.setFont(new Font("TimesRoman", Font.BOLD, 14));
		PreviousButton.setToolTipText("Previous month");
		PreviousButton.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				calendarModel.setPreviousMonth();
			}

			public void mouseReleased(MouseEvent e)
			{
				JLabel target = (JLabel) e.getSource();
				CircleIcon c = new CircleIcon(24,new Color(238,224,229));
				target.setIcon(c);
				target.setHorizontalTextPosition(JLabel.CENTER);
				target.setVerticalTextPosition(JLabel.CENTER);
			}

			public void mouseEntered(MouseEvent e) 
			{
				JLabel target = (JLabel) e.getSource();
				CircleIcon c = new CircleIcon(24,new Color(238,224,229));
				target.setIcon(c);
				target.setHorizontalTextPosition(JLabel.CENTER);
				target.setVerticalTextPosition(JLabel.CENTER);
				ToolTipManager.sharedInstance().setInitialDelay(0);
			}

			public void mouseExited(MouseEvent e)
			{
				JLabel target = (JLabel) e.getSource();
				target.setIcon(null);
			}
		});

		JLabel nextButton  = new JLabel("    >   ");
		nextButton.setFont(new Font("TimesRoman", Font.BOLD, 14));
		nextButton.setToolTipText("Next month");
		nextButton.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e)
			{
				calendarModel.setNextMonth();	
			}

			public void mouseReleased(MouseEvent e)
			{
				JLabel target = (JLabel) e.getSource();
				CircleIcon c = new CircleIcon(24,new Color(238,224,229));
				target.setIcon(c);
				target.setHorizontalTextPosition(JLabel.CENTER);
				target.setVerticalTextPosition(JLabel.CENTER);
			}

			public void mouseEntered(MouseEvent e) 
			{
				JLabel target = (JLabel) e.getSource();
				CircleIcon c = new CircleIcon(24,new Color(238,224,229));
				target.setIcon(c);
				target.setHorizontalTextPosition(JLabel.CENTER);
				target.setVerticalTextPosition(JLabel.CENTER);
				ToolTipManager.sharedInstance().setInitialDelay(0);
			}

			public void mouseExited(MouseEvent e)
			{
				JLabel target = (JLabel) e.getSource();
				target.setIcon(null);
			}
		});

		pAndn.add(PreviousButton,BorderLayout.WEST);
		pAndn.add(nextButton,BorderLayout.EAST);
		monthYear.add(monthAndYear,BorderLayout.WEST);
		monthYear.add(pAndn,BorderLayout.EAST);
		return monthYear;
	}

	/**
	 * create a JPanel to display the calendar
	 * @return a JPanel of calendar
	 */
	public JPanel getCal()
	{
		calendarpanel = new JPanel();
		calendarpanel.setPreferredSize(new Dimension(250, 200));
		calendarpanel.setBackground(Color.WHITE);
		setOpaque(true);
		calendarpanel.setLayout(new GridLayout(0,7));
		for(int i = 0; i< WEEK_DAY.length; i++)
		{ 
			JLabel week = new JLabel(WEEK_DAY[i],JLabel.CENTER); 
			week.setFont(new Font("TimesRoman", Font.BOLD,14));
			WeekDays [] days = WeekDays .values();
			week.setToolTipText(days[i].toString());
			week.addMouseListener(new MouseAdapter() 
			{
				public void mouseEntered(MouseEvent e) 
				{	
					ToolTipManager.sharedInstance().setInitialDelay(0);
				}
			});	
			calendarpanel.add(week);
		}

		final int labelSize = 42;
		int[] dayLabel = new int[labelSize];
		Calendar temp = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
		int vacantDay = temp.get(Calendar.DAY_OF_WEEK)-1;
		for(int i =1; i <= vacantDay; i++)
		{
			JLabel preVacancy = new JLabel();
			calendarpanel.add(preVacancy);
		}

		int curmonthLength = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i = 1; i <= curmonthLength; i++)
		{
			JLabel day = new JLabel(Integer.toString(i),JLabel.CENTER);
			day.setFont(new Font("TimesRoman", Font.BOLD,14));
			day.addMouseListener(new MouseAdapter() 
			{	
				public void mouseClicked(MouseEvent e)
				{
					JLabel target = (JLabel) e.getSource();
					calendarModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(target.getText()));
				}

				public void mouseEntered(MouseEvent e) 
				{
					JLabel target = (JLabel) e.getSource();
					CircleIcon c = new CircleIcon(24,new Color(207,207,207));
					target.setIcon(c);
					target.setHorizontalTextPosition(JLabel.CENTER);
					target.setVerticalTextPosition(JLabel.CENTER);
				}

				public void mouseExited(MouseEvent e)
				{
					JLabel target = (JLabel) e.getSource();
					target.setIcon(null);
				}
			});
		
			Calendar temp2 = new GregorianCalendar();
			if(i == temp2.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.MONTH) == temp2.get(Calendar.MONTH) && calendar.get(Calendar.YEAR) == temp2.get(Calendar.YEAR))
			{
				CircleIcon cir = new CircleIcon(24,new Color(30,144,255));
				day = new JLabel(cir);
				day.setText(Integer.toString(i));
				day.setFont(new Font("TimesRoman", Font.BOLD, 14));
				day.setForeground(Color.WHITE);
				day.setHorizontalTextPosition(JLabel.CENTER);
				day.setVerticalTextPosition(JLabel.CENTER);
				day.addMouseListener(new MouseAdapter() 
				{	
					public void mouseClicked(MouseEvent e)
					{
						JLabel target = (JLabel) e.getSource();
						calendarModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(target.getText()));
					}
				});
			}
			else if(i == calendar.get(Calendar.DAY_OF_MONTH))
			{
				CircleIcon cir = new CircleIcon(24,new Color(207,207,207));
				day = new JLabel(cir);
				day.setText(Integer.toString(i));
				day.setFont(new Font("TimesRoman", Font.BOLD, 14));
				day.setHorizontalTextPosition(JLabel.CENTER);
			}	
			calendarpanel.add(day);
		}

		int restVacantDay = labelSize - curmonthLength - vacantDay;
		for(int i = 0; i < restVacantDay; i++)
		{
			JLabel postVacancy = new JLabel();;
			calendarpanel.add(postVacancy);
		}
		return calendarpanel;
	}

	/**
	 * use JPanel that contains all components of Year And Month view and calendar panel
	 * @return JPanel 
	 */
	public JPanel getCalendarContainer()
	{
		calendarContainer = new JPanel();
		calendarContainer.setLayout(new BoxLayout(calendarContainer, BoxLayout.Y_AXIS));
		calendarContainer.add(getMonthAndYear());
		calendarContainer.add(getCal());
		return calendarContainer;
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		removeAll();
		setVisible(false);
		add(getCalendarContainer());
		setBackground(Color.WHITE);
		setOpaque(true);
		setVisible(true);
	}
}
