package de.costache.calendar.ui;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class HeaderPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private JToggleButton dayButton;
  private JToggleButton weekButton;
  private JToggleButton monthButton;
  private JButton refreshButton;

  public HeaderPanel() {
    super(true);
    setOpaque(false);
    init();
  }

  private void init() {
    setLayout(new FlowLayout(FlowLayout.RIGHT));
    dayButton = new JToggleButton();
    weekButton = new JToggleButton();
    monthButton = new JToggleButton();

    dayButton.setToolTipText(ControlConfigUtils.getString("label.Calendar.day"));
    weekButton.setToolTipText(ControlConfigUtils.getString("label.Calendar.week"));
    monthButton.setToolTipText(ControlConfigUtils.getString("label.Calendar.month"));

    dayButton.setIcon(IziImageUtils.getSmallIcon("/images/day.png"));
    weekButton.setIcon(IziImageUtils.getSmallIcon("/images/week.png"));
    monthButton.setIcon(IziImageUtils.getSmallIcon("/images/month.png"));

    refreshButton = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.REFRESH_ICON));
    refreshButton.setToolTipText(ControlConfigUtils.getString("default.button.refresh"));

    dayButton.setOpaque(false);
    weekButton.setOpaque(false);
    monthButton.setOpaque(false);

    ButtonGroup group = new ButtonGroup();
    group.add(dayButton);
    group.add(weekButton);
    group.add(monthButton);

    JToolBar buttonBar = new JToolBar();
    buttonBar.add(dayButton);
    buttonBar.add(weekButton);
    buttonBar.add(monthButton);
    buttonBar.add(refreshButton);
    add(buttonBar);
  }

  public JToggleButton getDayButton() {
    return dayButton;
  }

  public JToggleButton getWeekButton() {
    return weekButton;
  }

  public JToggleButton getMonthButton() {
    return monthButton;
  }

}
