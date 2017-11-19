package de.costache.calendar.ui.strategy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.costache.calendar.ui.ContentPanel;
import de.costache.calendar.ui.DayPanel;
import de.costache.calendar.ui.HoursPanel;
import de.costache.calendar.util.CalendarUtil;

class WeekDisplayStrategy implements DisplayStrategy {

  private Calendar start;
  private final ContentPanel parent;
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  private final DayPanel[] days = new DayPanel[7];

  private JPanel displayPanel;

  public WeekDisplayStrategy(final ContentPanel parent) {
    this.parent = parent;
    init();
  }

  @Override
  public void init() {

    UIDefaults uidef = UIManager.getDefaults();
    UIManager.put("ScrollBar.width", 10);
    int swidth = Integer.parseInt(uidef.get("ScrollBar.width").toString());

    if (start == null) {
      start = CalendarUtil.getCalendar(parent.getCalendar().getSelectedDay(), true);
    }
    start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());

    JPanel headersPanel = new JPanel(true);
    headersPanel.setLayout(new GridLayout());
    headersPanel.setOpaque(false);

    JPanel contentsPanel = new JPanel(true);
    contentsPanel.setLayout(new GridBagLayout());
    contentsPanel.setOpaque(false);

    JPanel allDayPanel = new JPanel(true);
    allDayPanel.setLayout(new GridLayout());
    allDayPanel.setOpaque(false);

    displayPanel = new JPanel(true);
    displayPanel.setOpaque(false);
    displayPanel.setLayout(new GridBagLayout());
    final GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1;
    gbc.weightx = 0;
    gbc.fill = GridBagConstraints.BOTH;
    HoursPanel hoursPanel = new HoursPanel(parent.getCalendar());

    hoursPanel.setPreferredSize(new Dimension(40, parent.getCalendar().getConfig().getHeigh()));
    contentsPanel.add(hoursPanel, gbc);

    final Calendar c = CalendarUtil.copyCalendar(start, true);
    for (int i = 0; i < 7; i++) {
      days[i] = new DayPanel(parent.getCalendar(), c.getTime(), 0.02f);
      headersPanel.add(days[i].getHeaderPanel());
      gbc.gridx = i + 1;
      gbc.gridy = 0;
      gbc.weighty = 1;
      gbc.weightx = 1;
      days[i].getContentPanel().setPreferredSize(
              new Dimension(30, parent.getCalendar().getConfig().getHeigh()));
      contentsPanel.add(days[i].getContentPanel(), gbc);
      allDayPanel.add(days[i].getCompleteDayPanel());
      c.add(Calendar.DATE, 1);
    }

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.02;
    gbc.weightx = 1;
    gbc.insets = new Insets(0, 41, 0, swidth);
    displayPanel.add(headersPanel, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 0.88;
    gbc.insets = new Insets(0, 0, 0, 0);

    JScrollPane content = new JScrollPane(contentsPanel);
    content.setOpaque(false);
    content.getViewport().setOpaque(false);
    content.setBorder(new EmptyBorder(0, 0, 0, 0));
    content.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    content.getViewport().setViewPosition(new Point(0, 500));

    displayPanel.add(content, gbc);
  }

  @Override
  public void display() {
    parent.removeAll();
    parent.setLayout(new BorderLayout());
    parent.add(displayPanel, BorderLayout.CENTER);
    parent.validate();
    parent.repaint();

  }

  @Override
  public void moveIntervalLeft() {
    Date currentDay = parent.getCalendar().getSelectedDay();
    parent.getCalendar().setSelectedDay(CalendarUtil.createInDays(currentDay, -7));
    start.setTime(CalendarUtil.createInDays(currentDay, -7));
    start.set(Calendar.DAY_OF_WEEK, 1);
    final Calendar c = CalendarUtil.copyCalendar(start, true);
    for (int i = 0; i < 7; i++) {
      days[i].setDate(c.getTime());
      c.add(Calendar.DATE, 1);
    }

    parent.validate();
    parent.repaint();
  }

  @Override
  public void moveIntervalRight() {
    Date currentDay = parent.getCalendar().getSelectedDay();
    parent.getCalendar().setSelectedDay(CalendarUtil.createInDays(currentDay, 7));
    start.setTime(CalendarUtil.createInDays(currentDay, 7));
    start.set(Calendar.DAY_OF_WEEK, 1);
    final Calendar c = CalendarUtil.copyCalendar(start, true);
    for (int i = 0; i < 7; i++) {
      days[i].setDate(c.getTime());
      c.add(Calendar.DATE, 1);
    }

    parent.validate();
    parent.repaint();
  }

  @Override
  public String getDisplayInterval() {
    final Calendar end = Calendar.getInstance();
    end.setTime(start.getTime());
    end.add(Calendar.DATE, 7);
    return sdf.format(start.getTime()) + " - " + sdf.format(end.getTime());
  }

  @Override
  public void setIntervalStart(Date date) {
    start = CalendarUtil.getCalendar(date, true);
    start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
    final Calendar c = CalendarUtil.copyCalendar(start, true);
    for (int i = 0; i < 7; i++) {
      days[i].setDate(c.getTime());
      c.add(Calendar.DATE, 1);
    }

    parent.validate();
    parent.repaint();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.mediamarkt.calendar.strategy.DisplayStrategy#getType()
   */
  @Override
  public Type getType() {
    return Type.WEEK;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.mediamarkt.calendar.strategy.DisplayStrategy#getIntervalStart()
   */
  @Override
  public Date getIntervalStart() {
    return start.getTime();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.mediamarkt.calendar.strategy.DisplayStrategy#getIntervalEnd()
   */
  @Override
  public Date getIntervalEnd() {
    final Calendar c = CalendarUtil.copyCalendar(start, true);
    c.add(Calendar.DATE, 7);
    c.add(Calendar.SECOND, -1);
    return c.getTime();
  }
}