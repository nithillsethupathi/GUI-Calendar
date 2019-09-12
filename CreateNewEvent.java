
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * CreateNewEvent to create new event 
 * @author Dangerous
 */
public class CreateNewEvent extends JFrame
{
	private CalendarModel model;
	private JTextField titleField; 
	private JPanel detailPanel;
	private Calendar c;
	/**
	 * Constructs an frame for the user to create a new event
	 * @param model CalendarModel to update model
	 */
	public CreateNewEvent(CalendarModel model)
	{
		this.model = model;
		c =  model.getCalendar();
		
		setTitle("Create Event");

		titleField = new JTextField("Untitled event");
		add(titleField, BorderLayout.NORTH);
		
		detailPanel = new JPanel();
		getEventDetail();
		add(detailPanel, BorderLayout.CENTER);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates the body of the Panel for getting event details
	 */
	public void getEventDetail()
	{
		detailPanel.setLayout(new BorderLayout());
		
		JPanel eventInfoPanel = new JPanel();
		eventInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");  
		String timeStr[] = formatter.format(c.getTime()).split("/");
		JTextField monthfield = new JTextField(timeStr[0]);
		monthfield.setHorizontalAlignment(JTextField.CENTER);
		monthfield.setPreferredSize(new Dimension(25, 25));
		
		JTextField dayfield= new JTextField(timeStr[1]);
		dayfield.setHorizontalAlignment(JTextField.CENTER);
		dayfield.setPreferredSize(new Dimension(25, 25));
		
		JTextField yearfield = new JTextField(timeStr[2]);
		yearfield.setHorizontalAlignment(JTextField.CENTER);
		yearfield.setPreferredSize(new Dimension(50, 25));
		
		JTextField start = new JTextField(String.format("%tH:00",c.getTime()));
		start.setHorizontalAlignment(JTextField.CENTER);
		start.setPreferredSize(new Dimension(75, 25));
		JLabel labelTo = new JLabel("to");
		JTextField end = new JTextField(String.format("%tH:00",c.getTime()));
		end.setHorizontalAlignment(JTextField.CENTER);
		end.setPreferredSize(new Dimension(75, 25));
		
		eventInfoPanel.add(monthfield);
		eventInfoPanel.add(new JLabel("/"));
		eventInfoPanel.add(dayfield);
		eventInfoPanel.add(new JLabel("/"));
		eventInfoPanel.add(yearfield);
		eventInfoPanel.add(start);
		eventInfoPanel.add(labelTo);
		eventInfoPanel.add(end);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
		
			});
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					int month = Integer.parseInt(monthfield.getText());
					int day = Integer.parseInt(dayfield.getText());
					int year  = Integer.parseInt(yearfield.getText());
					Event event= new Event(titleField.getText(), year, month, day, start.getText(), end.getText());
					if(!model.addEvent(event))
					{
						JOptionPane.showMessageDialog(CreateNewEvent.this, 
								"Fail to save, your event has a time conflict with another event",
								"Time Conflict",
								JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						model.updateEventList(event);
					}
					dispose();
				}
			});
		
		buttonPanel.add(save);
		buttonPanel.add(cancel);
		
		detailPanel.add(eventInfoPanel,BorderLayout.NORTH);
		detailPanel.add(buttonPanel, BorderLayout.CENTER);
		
	}
}
