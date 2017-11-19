package com.s3s.ssm.widget;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.commons.lang.time.DateUtils;
import org.jdesktop.swingx.calendar.CalendarUtils;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.javasoft.swing.DropDownButton;

@SuppressWarnings("unused")
public class JVCompoundDatePicker extends JPanel {
  private static final long serialVersionUID = 1L;
  private JVDatePicker fFromDatePicker;
  private JVDatePicker fToDatePicker;
  private DropDownButton fFilterButton;

  public JVCompoundDatePicker() {
    super();
    setLayout(new FlowLayout(FlowLayout.LEFT));
    initComponents();
    setUpFilter();
  }

  public Date getFromDate() {
    return fFromDatePicker.getDate();
  }

  public Date getToDate() {
    return fToDatePicker.getDate();
  }

  private void initComponents() {
    fFromDatePicker = new JVDatePicker();
    fToDatePicker = new JVDatePicker();
    fFilterButton = new DropDownButton(IziImageUtils.getSmallIcon(IziImageConstants.SUGGEST_ICON));

    add(new JLabel(ControlConfigUtils.getString("label.JVCompoundDatePicker.fromDate")));
    add(fFromDatePicker);
    add(new JLabel(ControlConfigUtils.getString("label.JVCompoundDatePicker.toDate")));
    add(fToDatePicker);
    add(fFilterButton);
  }

  private void setDateForDatePicker(int month) {
    Calendar janCalendar = Calendar.getInstance();
    janCalendar.set(Calendar.MONTH, month);
    CalendarUtils.startOfMonth(janCalendar);
    fFromDatePicker.setDate(janCalendar.getTime());
    CalendarUtils.endOfMonth(janCalendar);
    fToDatePicker.setDate(janCalendar.getTime());
  }

  private void setUpFilter() {

    JMenuItem allDayItem = new JMenuItem(ControlConfigUtils.getString("label.JVCompoundDatePicker.allDay"));
    allDayItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fFromDatePicker.setDate(null);
        fToDatePicker.setDate(null);
      }
    });

    JMenuItem yesterdayItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.yesterday"));
    yesterdayItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Date yesterday = DateUtils.addDays(new Date(), -1);
        fFromDatePicker.setDate(yesterday);
        fToDatePicker.setDate(yesterday);
      }
    });

    JMenuItem todayItem = new JMenuItem(ControlConfigUtils.getString("label.JVCompoundDatePicker.today"));
    todayItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        fFromDatePicker.setDate(new Date());
        fToDatePicker.setDate(new Date());
      }
    });

    JMenuItem thisWeekItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.thisWeek"));
    thisWeekItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Date startOfWeek = CalendarUtils.startOfWeek(Calendar.getInstance(), new Date());
        fFromDatePicker.setDate(startOfWeek);
        fToDatePicker.setDate(new Date());
      }
    });

    JMenuItem lastWeekItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.lastWeek"));
    lastWeekItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Date startOfWeek = CalendarUtils.startOfWeek(Calendar.getInstance(), new Date());
        Date startOfLastWeek = DateUtils.addDays(startOfWeek, -7);
        Date endOfLastWeek = CalendarUtils.endOfWeek(Calendar.getInstance(), startOfLastWeek);
        fFromDatePicker.setDate(startOfLastWeek);
        fToDatePicker.setDate(endOfLastWeek);
      }
    });

    JMenuItem thisMonthItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.thisMonth"));
    thisMonthItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Calendar currentCalendar = Calendar.getInstance();
        CalendarUtils.startOfMonth(currentCalendar);
        fFromDatePicker.setDate(currentCalendar.getTime());
        fToDatePicker.setDate(new Date());
      }
    });

    JMenuItem lastMonthItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.lastMonth"));
    lastMonthItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONDAY, -1);
        CalendarUtils.startOfMonth(calendar);
        fFromDatePicker.setDate(calendar.getTime());
        CalendarUtils.endOfMonth(calendar);
        fToDatePicker.setDate(calendar.getTime());
      }
    });
    String monthLabel = ControlConfigUtils.getString("label.JVCompoundDatePicker.month");
    JMenu monthMenu = new JMenu(monthLabel);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    for (int i = 0; i <= currentMonth; i++) {
      final int month = i;
      JMenuItem monthItem = new JMenuItem(monthLabel + " " + (i + 1));
      monthItem.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          setDateForDatePicker(month);
        }

      });
      monthMenu.add(monthItem);
    }

    JMenuItem thisQuaterItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.thisQuater"));
    thisQuaterItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.MARCH) {
          currentCalendar.set(currentYear, Calendar.JANUARY, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.MARCH, 31);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE) {
          currentCalendar.set(currentYear, Calendar.APRIL, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.JUNE, 30);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER) {
          currentCalendar.set(currentYear, Calendar.JULY, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.SEPTEMBER, 30);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else {
          currentCalendar.set(currentYear, Calendar.OCTOBER, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.DECEMBER, 31);
          fToDatePicker.setDate(currentCalendar.getTime());
        }
      }
    });

    JMenuItem lastQuaterItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.lastQuater"));
    lastQuaterItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int lastMonth = currentCalendar.get(Calendar.MONTH) - 3;
        if (lastMonth >= Calendar.JANUARY && lastMonth <= Calendar.MARCH) {
          currentCalendar.set(currentYear, Calendar.JANUARY, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.MARCH, 31);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else if (lastMonth >= Calendar.APRIL && lastMonth <= Calendar.JUNE) {
          currentCalendar.set(currentYear, Calendar.APRIL, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.JUNE, 30);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else if (lastMonth >= Calendar.JULY && lastMonth <= Calendar.SEPTEMBER) {
          currentCalendar.set(currentYear, Calendar.JULY, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.SEPTEMBER, 30);
          fToDatePicker.setDate(currentCalendar.getTime());
        } else {
          currentCalendar.set(currentYear, Calendar.OCTOBER, 1);
          fFromDatePicker.setDate(currentCalendar.getTime());
          currentCalendar.set(currentYear, Calendar.DECEMBER, 31);
          fToDatePicker.setDate(currentCalendar.getTime());
        }
      }
    });

    JMenuItem thisYearItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.thisYear"));
    thisYearItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Date startOfYear = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.YEAR);
        fFromDatePicker.setDate(startOfYear);
        fToDatePicker.setDate(new Date());
      }
    });

    JMenuItem lastYearItem = new JMenuItem(
            ControlConfigUtils.getString("label.JVCompoundDatePicker.lastYear"));
    lastYearItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        currentCalendar.set(currentYear - 1, Calendar.JANUARY, 1);
        fFromDatePicker.setDate(currentCalendar.getTime());
        currentCalendar.set(currentYear - 1, Calendar.DECEMBER, 31);
        fToDatePicker.setDate(currentCalendar.getTime());
      }
    });

    fFilterButton.getPopupMenu().add(todayItem);
    fFilterButton.getPopupMenu().add(yesterdayItem);
    fFilterButton.getPopupMenu().addSeparator();
    fFilterButton.getPopupMenu().add(thisWeekItem);
    fFilterButton.getPopupMenu().add(lastWeekItem);
    fFilterButton.getPopupMenu().addSeparator();
    fFilterButton.getPopupMenu().add(thisMonthItem);
    fFilterButton.getPopupMenu().add(lastMonthItem);
    fFilterButton.getPopupMenu().add(monthMenu);
    fFilterButton.getPopupMenu().addSeparator();
    fFilterButton.getPopupMenu().add(thisQuaterItem);
    fFilterButton.getPopupMenu().add(lastQuaterItem);
    fFilterButton.getPopupMenu().addSeparator();
    fFilterButton.getPopupMenu().add(thisYearItem);
    fFilterButton.getPopupMenu().add(lastYearItem);
    fFilterButton.getPopupMenu().addSeparator();
    fFilterButton.getPopupMenu().add(allDayItem);
  }
}
