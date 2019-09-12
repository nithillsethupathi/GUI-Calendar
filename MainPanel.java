
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MainPanel class
 * @author Dangerous
 */
public class MainPanel extends JPanel implements ChangeListener {
    private CalendarModel calendarModel;
    private Calendar calendar;
    private JPanel calendarTitle;
    private JPanel timeAndDate;
    private JPanel shortcut;
    private JPanel dayView;
    private JPanel weekView;
    private JPanel monthView;
    private JPanel agendaView;
    private JPanel createPanel;
    public enum WeekDays 
    {
    	Sunday, Monday, Tuesday, Wednesday, Thurday, Friday, Saturday ;
    }
    public enum Month 
	{
		January, Feburary, March, April, May, June, July, August, September, October, November, December;
	}

    /**
     * Models a shortcut panel with a CalendarModel
     *
     * @param calendarModel calendarModel
     */
    public MainPanel(CalendarModel calendarModel) {
        this.setCalendarModel(calendarModel);
        setCalendar(calendarModel.getCalendar());
        panelLayout();
    }

    /**
     * Use a JPanel with calendar title
     *
     * @return the JPanel with calendar title
     */
    public JPanel getCalendarTitle() {
        calendarTitle = new JPanel();
        calendarTitle.setLayout(new BorderLayout());
        calendarTitle.setBackground(Color.WHITE);
        JLabel space = new JLabel("     ");
        JLabel imageLabel = new JLabel(new ImageIcon("./src/mycalendar.png"));
        JPanel titleAndBtn = new JPanel();
        titleAndBtn.setBackground(Color.WHITE);
        titleAndBtn.setLayout(new BorderLayout());
        JLabel title = new JLabel("  Load Calendar       ");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        title.setForeground(new Color(30, 144, 255));
        title.setToolTipText("Load my calendar from file");
        title.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser inputFile = new JFileChooser();
                inputFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (inputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = inputFile.getSelectedFile();
                    try {
                        Scanner in = new Scanner(file);
                        while (in.hasNextLine()) {
                            getCalendarModel().set(in.nextLine());
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                title.setForeground(new Color(30, 144, 255));
            }

            public void mouseEntered(MouseEvent e) {
                title.setForeground(new Color(190, 190, 190));
            }

            public void mouseExited(MouseEvent e) {
                title.setForeground(new Color(30, 144, 255));
            }
        });
        titleAndBtn.add(title, BorderLayout.WEST);

        calendarTitle.add(space, BorderLayout.WEST);
        calendarTitle.add(imageLabel, BorderLayout.CENTER);
        calendarTitle.add(titleAndBtn, BorderLayout.EAST);
        return calendarTitle;
    }

    /**
     *Use a JPanel with current time and date
     *
     * @return the JPanel with current time and date
     */
    public JPanel timeAndDateContainer() {
        setTimeAndDate(new JPanel());
        getTimeAndDate().setPreferredSize(new Dimension(200, 70));
        getTimeAndDate().setBackground(Color.WHITE);
        getTimeAndDate().setLayout(new BoxLayout(getTimeAndDate(), BoxLayout.Y_AXIS));

        JLabel currentTime = new JLabel(String.format("%tT", new Date()));

        currentTime.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        currentTime.setForeground((new Color(105, 105, 105)));
        Timer t = new Timer(1000, e -> currentTime.setText(String.format("%tT", new Date())));
        t.start();

        Month[] months = Month.values();
        WeekDays [] days = WeekDays .values();

        JLabel currentDate = new JLabel(" " + days[getCalendar().get(Calendar.DAY_OF_WEEK) - 1].toString() + ", " + months[getCalendar().get(Calendar.MONTH)]
                + " " + getCalendar().get(Calendar.DATE) + ", " + getCalendar().get(Calendar.YEAR));
        currentDate.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        currentDate.setForeground(new Color(30, 144, 255));
        currentDate.addMouseListener(new MouseAdapter() {
            Calendar cal = new GregorianCalendar();

            public void mousePressed(MouseEvent e) {
                getCalendarModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                currentDate.setForeground(Color.GRAY);
            }

            public void mouseReleased(MouseEvent e) {
                currentDate.setForeground(new Color(190, 190, 190));
            }

            public void mouseEntered(MouseEvent e) {
                currentDate.setForeground(new Color(190, 190, 190));
            }

            public void mouseExited(MouseEvent e) {
                currentDate.setForeground(new Color(30, 144, 255));
            }
        });

        getTimeAndDate().add(currentTime);
        getTimeAndDate().add(currentDate);
        return getTimeAndDate();
    }

    /**
     * Use a JPanel with today button, Previous and next button
     *
     * @return the JPanel with today button, Previous and next button
     */
    public JPanel shortcutContainer() {
        setShortcut(new JPanel());
        getShortcut().setPreferredSize(new Dimension(480, 100));
        getShortcut().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        getShortcut().setBackground(Color.WHITE);

       
        JButton today = new JButton("TODAY");
        today.setPreferredSize(new Dimension(70,50)); 
        today.setBorder(BorderFactory.createRaisedBevelBorder()); 
        Calendar cal = new GregorianCalendar();
        Month[] months = Month.values();
        WeekDays [] days = WeekDays .values();
        today.setToolTipText(days[cal.get(Calendar.DAY_OF_WEEK) - 1].toString() + ", " + months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DATE));
        today.setFont(new Font("TimesRoman", Font.BOLD, 14));
        today.setBackground(new Color(225,255,255));
        today.setOpaque(true);

        today.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                getCalendarModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            }

            public void mouseReleased(MouseEvent e) {
                today.setBackground(new Color(225,255,255));
            }

            public void mouseEntered(MouseEvent e) {
                today.setBackground(new Color(240,255,255));
                ToolTipManager.sharedInstance().setInitialDelay(0);
            }

            public void mouseExited(MouseEvent e) {
                today.setBackground(new Color(225,255,255));

            }
        });


        JLabel preAndNext = new JLabel();
        preAndNext.setPreferredSize(new Dimension(100, 70));
        preAndNext.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel previousBtn = new JLabel("   <   ");
        previousBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        if (getCalendarModel().getViewType() == EventViewTab.Day || getCalendarModel().getViewType() == EventViewTab.Agenda) {
            previousBtn.setToolTipText("Previous day");
            previousBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setPreviousDay();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });
        } else if (getCalendarModel().getViewType() == EventViewTab.Week) {
            previousBtn.setToolTipText("Previous week");
            previousBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setPreviousWeek();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });
        } else if (getCalendarModel().getViewType() == EventViewTab.Month) {
            previousBtn.setToolTipText("Previous month");
            previousBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setPreviousMonth();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });
        }

        JLabel nextBtn = new JLabel("  > ");
        nextBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        if (getCalendarModel().getViewType() == EventViewTab.Day || getCalendarModel().getViewType() == EventViewTab.Agenda) {
            nextBtn.setToolTipText("Next day");
            nextBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setNextDay();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });

        } else if (getCalendarModel().getViewType() == EventViewTab.Week) {
            nextBtn.setToolTipText("Next week");
            nextBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setNextWeek();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });
        } else if (getCalendarModel().getViewType() == EventViewTab.Month) {
            nextBtn.setToolTipText("Next month");
            nextBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    getCalendarModel().setNextMonth();
                }

                public void mouseReleased(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseEntered(MouseEvent e) {
                	MainPanel.this.mouseReleased(e);
                }

                public void mouseExited(MouseEvent e) {
                    JLabel target = (JLabel) e.getSource();
                    target.setIcon(null);
                }
            });
        }

        preAndNext.add(previousBtn);
        preAndNext.add(nextBtn);
        getShortcut().add(today);
        getShortcut().add(preAndNext);
        if (getCalendarModel().getViewType() == EventViewTab.Day) {
            getShortcut().add(getDayView());
        } else if (getCalendarModel().getViewType() == EventViewTab.Week) {
            getShortcut().add(getWeekView());
        } else if (getCalendarModel().getViewType() == EventViewTab.Month) {
            getShortcut().add(getMonthView());
        } else {
            getShortcut().add(getAgendaView());
        }

        getShortcut().add(getCreateButton());
        return getShortcut();
    }

    /**
     * Use a JPanel to show the day option
     *
     * @return the JPanel to show the day option
     */
    public JPanel getDayView() {
        dayView = new JPanel();
        viewOption(dayView, "Day");
        return dayView;
    }

    /**
     * Use a JPanel to show the week option
     *
     * @return the JPanel to show the week option
     */
    public JPanel getWeekView() {
        weekView = new JPanel();
        viewOption(weekView, "Week");
        return weekView;
    }

    /**
     * uses a JPanel to show the month option
     *
     * @return the JPanel to show the month option
     */
    public JPanel getMonthView() {
        monthView = new JPanel();
        viewOption(monthView, "Month");
        return monthView;
    }

    /**
     * uses a JPanel to show the agenda 
     *
     * @return the JPanel to show the agenda option
     */
    public JPanel getAgendaView() {
        agendaView = new JPanel();
        viewOption(agendaView, "Agenda");
        return agendaView;
    }

    public void viewOption(JPanel panel, String text){
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setBackground(new Color(225,255,255));
        panel.setPreferredSize(new Dimension(70, 50));
        JLabel view = new JLabel(text, JLabel.CENTER);
        view.setFont(new Font("TimesRoman", Font.BOLD, 20));
        view.setForeground(new Color(105, 105, 105));
        

        JPanel finalPanel = panel;
        panel.setBorder(BorderFactory.createRaisedBevelBorder()); 
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	OptionOfViewFrame vof = new OptionOfViewFrame(getCalendarModel());
                vof.setLocationRelativeTo(getCreateButton());
            }

            public void mousePressed(MouseEvent e) {
                finalPanel.setBackground(new Color(225,255,255));
            }

            public void mouseReleased(MouseEvent e) {
                finalPanel.setBackground(new Color(240,255,255));
            }

            public void mouseEntered(MouseEvent e) {
                finalPanel.setBackground(new Color(240,255,255));
            }

            public void mouseExited(MouseEvent e) {
                finalPanel.setBackground(new Color(225,255,255));
            }
        });

        finalPanel.add(view);
    }

    /**
     * use the JPanel to create button
     *
     * @return the JPanel with the create button
     */
    public JPanel getCreateButton() {
        createPanel = new JPanel();
        createPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        createPanel.setBackground(Color.WHITE);
        JButton createButton = new JButton("new");
        createButton.setBorder(BorderFactory.createRaisedBevelBorder()); 
        createButton.setFont(new Font("TimesRoman", Font.BOLD, 20));
        createButton.setPreferredSize(new Dimension(70, 50));
        createButton.setBackground(new Color(225,255,255));
        createButton.setOpaque(true);
        createButton.setToolTipText("Create new Event");
        createButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	CreateNewEvent cne = new CreateNewEvent(getCalendarModel());
            }
        });
        createPanel.add(createButton);
        return createPanel;
    }

    public void panelLayout(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 70));
        add(getCalendarTitle(), BorderLayout.WEST);
        add(timeAndDateContainer(), BorderLayout.CENTER);
        add(shortcutContainer(), BorderLayout.EAST);
    }

    public void mouseReleased(MouseEvent e){
        JLabel target = (JLabel) e.getSource();
        CircleIcon cir = new CircleIcon(29, new Color(238, 224, 229));
        target.setIcon(cir);
        target.setHorizontalTextPosition(JLabel.CENTER);
        target.setVerticalTextPosition(JLabel.CENTER);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        removeAll();
        setVisible(false);
        panelLayout();
        setVisible(true);
    }

    /**
     * get Calendar Model
     * @return
     */
    public CalendarModel getCalendarModel() {
        return calendarModel;
    }

    /**
     * Setting Calendar Model
     * @param calendarModel
     */
    public void setCalendarModel(CalendarModel calendarModel) {
        this.calendarModel = calendarModel;
    }

    /**
     * display Calendar
     * @return
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * setting Calendar
     * @param calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * get Time And Date
     * @return
     */
    public JPanel getTimeAndDate() {
        return timeAndDate;
    }

    /**
     *setting Time And Date
     * @param timeAndDate
     */
    public void setTimeAndDate(JPanel timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    /**
     * Getter for Shortcut
     * @return
     */
    public JPanel getShortcut() {
        return shortcut;
    }

    /**
     * setting Main Panel
     * @param shortcut
     */
    public void setShortcut(JPanel shortcut) {
        this.shortcut = shortcut;
    }
}
