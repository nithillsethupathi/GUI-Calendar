
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * OptionOfViewFrame class creates a pop-up in place where there is a set of options available to choose from
 * @author Dangerous
 */
public class OptionOfViewFrame extends JFrame {

    /**
     * ViewOptionPopUp constructor creates the pop-up based on JFrame
     *
     * @param calendarModel CalendarModel object being passed
     */
    public OptionOfViewFrame(CalendarModel calendarModel) {
        MouseListener listen = new MouseListener();
        setSize(95, 125);
        JPanel viewOptionContainer = new JPanel();
        viewOptionContainer.setPreferredSize(new Dimension(50, 150));
        viewOptionContainer.setBackground(Color.WHITE);
        viewOptionContainer.setLayout(new BoxLayout(viewOptionContainer, BoxLayout.Y_AXIS));

        JLabel day = new JLabel("Day");
        listen.listener(day, calendarModel, EventViewTab.Day);

        JLabel week = new JLabel("Week");
        listen.listener(week, calendarModel, EventViewTab.Week);

        JLabel month = new JLabel("Month");
        listen.listener(month, calendarModel, EventViewTab.Month);

        JLabel agenda = new JLabel("Agenda");
        listen.listener(agenda, calendarModel, EventViewTab.Agenda);

        viewOptionContainer.add(day);
        viewOptionContainer.add(week);
        viewOptionContainer.add(month);
        viewOptionContainer.add(agenda);
        add(viewOptionContainer);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * MouseListener class is used to set the view of the pop-up and action to be followed when MouseListener is added
     *
     */
    private class MouseListener {
        /**
         * listener method that sets the view and assigns the mouse actions
         * @param label JLabel object being passed
         * @param calendarModel CalendarModel object being passed
         * @param event EventViewOptions object being passed
         */
        public void listener(JLabel label, CalendarModel calendarModel, EventViewTab event) {
            label.setFont(new Font("TimesRoman", Font.PLAIN, 16));
            label.setPreferredSize(new Dimension(50, 30));
            label.setBackground(Color.WHITE);
            label.setOpaque(true);
            label.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    calendarModel.setViewType(event);
                    dispose();
                }

                public void mousePressed(MouseEvent e) {
                    label.setBackground(new Color(250,250,210));
                }

                public void mouseReleased(MouseEvent e) {
                    label.setBackground(new Color(250,250,210));
                }

                public void mouseEntered(MouseEvent e) {
                    label.setBackground(new Color(250,250,210));
                }

                public void mouseExited(MouseEvent e) {
                    label.setBackground(Color.WHITE);
                }
            });
        }
    }

}

