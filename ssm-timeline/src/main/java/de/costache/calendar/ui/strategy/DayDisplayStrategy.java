/**
 * Copyright 2013 Theodor Costache
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.costache.calendar.ui.strategy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import de.costache.calendar.Config;
import de.costache.calendar.ui.ContentPanel;
import de.costache.calendar.ui.DayPanel;
import de.costache.calendar.ui.HoursPanel;
import de.costache.calendar.util.CalendarUtil;

/**
 * 
 * @author theodorcostache
 * 
 */
class DayDisplayStrategy implements DisplayStrategy {

  private Calendar start;
  private final ContentPanel parent;
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  private DayPanel day;
  private JPanel displayPanel;
  private final Config config;

  public DayDisplayStrategy(final ContentPanel parent) {
    this.parent = parent;
    config = parent.getCalendar().getConfig();
    init();
  }

  @Override
  public void init() {
    int swidth = 10;

    if (start == null) {
      start = CalendarUtil.getCalendar(parent.getCalendar().getSelectedDay(), true);
    }

    JPanel contentsPanel = new JPanel(true);
    contentsPanel.setLayout(new GridBagLayout());
    contentsPanel.setOpaque(false);

    displayPanel = new JPanel(true);
    displayPanel.setOpaque(false);
    displayPanel.setLayout(new GridBagLayout());
    final GridBagConstraints gbc = new GridBagConstraints();

    final Calendar c = CalendarUtil.copyCalendar(start, true);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1;
    gbc.weightx = 0;
    gbc.fill = GridBagConstraints.BOTH;
    HoursPanel hoursPanel = new HoursPanel(parent.getCalendar());
    contentsPanel.add(hoursPanel, gbc);

    day = new DayPanel(parent.getCalendar(), c.getTime());
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weighty = 1;
    gbc.weightx = 1;

    hoursPanel.setPreferredSize(new Dimension(40, config.getHeigh()));
    day.getContentPanel().setPreferredSize(new Dimension(30, config.getHeigh()));
    contentsPanel.add(day.getContentPanel(), gbc);
    c.add(Calendar.DATE, 1);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.02;
    gbc.insets = new Insets(0, 40, 0, swidth);
    displayPanel.add(day.getHeaderPanel(), gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 0.88;
    gbc.insets = new Insets(0, 0, 0, 0);
    JScrollPane scrollPane = new JScrollPane(contentsPanel);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    // scrollPane.getViewport().setViewPosition(new Point(0, 500));
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    displayPanel.add(scrollPane, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weighty = 0.1;
    gbc.insets = new Insets(0, 40, 0, 0);
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
    parent.getCalendar().setSelectedDay(CalendarUtil.createInDays(currentDay, -1));
    start.setTime(CalendarUtil.createInDays(currentDay, -1));
    day.setDate(start.getTime());
    parent.validate();
    parent.repaint();
  }

  @Override
  public void moveIntervalRight() {
    Date currentDay = parent.getCalendar().getSelectedDay();
    parent.getCalendar().setSelectedDay(CalendarUtil.createInDays(currentDay, 1));
    start.setTime(CalendarUtil.createInDays(currentDay, 1));
    day.setDate(start.getTime());
    parent.validate();
    parent.repaint();
  }

  @Override
  public void setIntervalStart(Date date) {
    start = CalendarUtil.getCalendar(date, true);
    day.setDate(start.getTime());
    parent.validate();
    parent.repaint();
  }

  @Override
  public String getDisplayInterval() {
    return sdf.format(start.getTime());
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.mediamarkt.calendar.strategy.DisplayStrategy#getType()
   */
  @Override
  public Type getType() {
    return Type.DAY;
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
    c.add(Calendar.DATE, 1);
    c.add(Calendar.SECOND, -1);
    return c.getTime();
  }

}