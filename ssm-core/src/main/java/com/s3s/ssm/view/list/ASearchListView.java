package com.s3s.ssm.view.list;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

import de.javasoft.swing.JYTaskPane;

public abstract class ASearchListView<T> extends AListView<T> {
  private static final long serialVersionUID = 1L;
  private JComponent chartPane;

  public ASearchListView() {
    super();
  }

  public ASearchListView(Map<String, Object> request, Icon icon, String label) {
    super(request, icon, label);
  }

  @Override
  protected void addComponents() {
    addSearchPanel();
    super.addComponents();
  }

  protected void addSearchPanel() {
    if (createSearchPanel() == null) {
      return;
    }
    JYTaskPane taskPane = new JYTaskPane();
    taskPane.setTitle(getMessage("label.search.searchTitle"));
    taskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SEARCH_FRAME_ICON));
    taskPane.setCollapsed(false);
    JComponent searchPanel = createSearchPanel();
    taskPane.add(searchPanel);
    JPanel searchButtonsPanel = createSearchButtonsPanel();
    taskPane.add(searchButtonsPanel);
    contentPane.add(taskPane, BorderLayout.NORTH);
  }

  protected void addChart(JComponent chartPanel) {
    if (chartPane != null) {
      contentPane.remove(chartPane);
    }
    this.chartPane = chartPanel;
    contentPane.add(chartPanel, BorderLayout.EAST);
  }

  protected JPanel createSearchButtonsPanel() {
    JButton btnSearch = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.SEARCH_ICON));
    btnSearch.setText(getMessage("button.text.search"));
    btnSearch.setName("btnSearch");
    btnSearch.addActionListener(e -> {
      refreshAndBackToFirstPage();
    });
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    btnSearch.setCursor(handCursor);

    JButton btnClear = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.CLEAR_ICON));
    btnClear.setName("btnClear");
    btnClear.setText(getMessage("button.text.clearCriteria"));
    btnClear.setToolTipText(getMessage("tooltip.clearCriteria"));
    btnClear.addActionListener(e -> {
      clearCriteria();
    });
    btnSearch.setCursor(handCursor);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.add(btnSearch);
    panel.add(btnClear);
    return panel;
  }

  protected abstract JComponent createSearchPanel();

  protected abstract void clearCriteria();
}
