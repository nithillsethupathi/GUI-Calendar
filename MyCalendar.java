import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * MyCalendar class that contains the main method and assembles the GUI calendar
 * @author Dangerous
 */
public class MyCalendar {
    /**
     * main method that runs and tests the program
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Calendar");
        CalendarModel model = new CalendarModel();
        MainPanel mainPanel = new MainPanel(model);
        CalendarPanel panel = new CalendarPanel(model);
        ViewOfEvent viewOfEvent = new ViewOfEvent(model);
        model.attach(panel);
        model.attach(mainPanel);
        model.attach(viewOfEvent);
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.WEST);
        frame.add(viewOfEvent, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
