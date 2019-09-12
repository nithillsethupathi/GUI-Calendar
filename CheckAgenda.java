
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Models an CheckAgenda
 * @author Dangerous
 */
public class CheckAgenda extends JFrame
{
	private CalendarModel calendarModel;
	private Calendar calendar;
	private ArrayList<Event> eventInterval; 

	/**
	 * create an new pop-up frame of create agenda 
	 * @param calendarModel the CalendarModel
	 */
	public  CheckAgenda(CalendarModel calendarModel)
	{
		this.calendarModel = calendarModel;
		calendar =  calendarModel.getCalendar();
		eventInterval = new ArrayList<>();

		setBackground(Color.WHITE);
		JPanel agendaPanel = new JPanel(new BorderLayout());
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new GridLayout(3, 2));

		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");  
		String timeStr = formatter.format(calendar.getTime());
		JLabel startDate = new JLabel("Start date:");
		JTextField startDateField = new JTextField();
		startDateField.setText(String.format(timeStr));

		JLabel endDate = new JLabel("End date:");
		JTextField endDateField = new JTextField();
		endDateField.setText(String.format(timeStr));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(e->{

				String[] startDateArr = startDateField.getText().split("/");
				int startYear = Integer.parseInt(startDateArr[2]);
				int startDay = Integer.parseInt(startDateArr[1]);
				int startMonth = Integer.parseInt(startDateArr[0]);

				String[] endDateArr = endDateField.getText().split("/");
				int endYear = Integer.parseInt(endDateArr[2]);
				int endDay = Integer.parseInt(endDateArr[1]);
				int endMonth = Integer.parseInt(endDateArr[0]);

				eventInterval = calendarModel.getEventInRange(startYear, startMonth, startDay, endYear, endMonth, endDay);
				calendarModel.setAgendasList(eventInterval);	
			dispose();
		});
		JButton cancelButton = new JButton("Cancel"); 
		
		cancelButton.addActionListener(e-> dispose());
		
		subpanel.add(startDate);
		subpanel.add(startDateField);
		subpanel.add(endDate);
		subpanel.add(endDateField);
		subpanel.add(saveButton);
		subpanel.add(cancelButton);
		
		agendaPanel.add(subpanel, BorderLayout.CENTER);
		add(agendaPanel);
		pack();
		setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}

}
