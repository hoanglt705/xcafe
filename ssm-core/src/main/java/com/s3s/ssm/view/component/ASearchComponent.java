/*
 * SearchComponent
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.interfaces.IDirtiableObject;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since Apr 18, 2012
 */
public abstract class ASearchComponent<T extends AbstractBaseIdObject> extends JPanel implements
        DocumentListener, IDirtiableObject {
  private static final int NUM_VISIBLE_ROWS = 10;
  private static final long serialVersionUID = -869806032147504253L;
  protected JTextField textField;
  private JWindow popup;
  private JScrollPane tablePane;
  private JXTable table;
  private List<T> entities = new ArrayList<>();
  private String[] attributeColumns;
  protected String[] searchOnAttributes;
  private String[] displayAttribute;
  private T selectedEntity;
  private T fInitialContent;
  private Class<T> entityClass;
  private JPanel suggestPanel;
  private Action enterAction;
  private boolean isTableShown = false;
  private String cacheId;
  private final List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  /**
   * Default constructor.
   */
  public ASearchComponent() {
    this.displayAttribute = getDisplayAttributes();
    this.attributeColumns = getAttributeColumns();
    this.searchOnAttributes = getSearchedOnAttributes();
    this.entityClass = (Class<T>) IziClassUtils.getArgumentClass(getClass());
    initSuggestPanel();
    initTable();
    initTextField();
    initPromptSupport();
    initPopup();
  }

  @Override
  public void fireChangeEvent() {

  }

  @Override
  public boolean isDirty() {
     return !selectedEntity.equals(fInitialContent);
  }

  public T getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(T fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  private void initPopup() {
    popup = new JWindow();
    popup.add(tablePane);
  }

  private void initPromptSupport() {
    String promptText = createPromptText();
    PromptSupport.setPrompt(promptText, textField);
    PromptSupport.setFontStyle(Font.ITALIC, textField);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, textField);

    addKeyMapping();
    add(textField);
  }

  private void initSuggestPanel() {
    suggestPanel = new JPanel();
    suggestPanel.setBackground(UIConstants.INFO_COLOR);
    suggestPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    JLabel tipLabel = new JLabel(ControlConfigUtils.getString("SearchComponent.suggestion"));
    tipLabel.setFont(UIConstants.DEFAULT_ITALIC_FONT);
    suggestPanel.add(tipLabel);
    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    enterAction = new EnterAction();
  }

  private void initTable() {
    TableModel model = new SearchResultTable();
    table = new JXTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setVisibleRowCount(NUM_VISIBLE_ROWS);
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          enterAction.actionPerformed(null);
        }
      }

    });

    tablePane = new JScrollPane(table);
  }

  private void initTextField() {
    textField = new JTextField(20);
    textField.getDocument().addDocumentListener(this);
    textField.addFocusListener(new FocusListener() {

      @Override
      public void focusLost(FocusEvent e) {
        // worker.cancel(true);
        if (selectedEntity == null) {
          textField.setText(null);
        }
        popup.setVisible(false);
        isTableShown = false;

      }

      @Override
      public void focusGained(FocusEvent e) {
        if (StringUtils.isNotBlank(textField.getText())) {
          showTipPanel();
        }
      }
    });
  }

  public void addKeyMapping() {
    // ////////// Key listener for textField
    InputMap inputMap = textField.getInputMap(WHEN_FOCUSED);
    KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
    KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    inputMap.put(downKey, "downAction");
    inputMap.put(upKey, "upAction");
    inputMap.put(enterKey, "enterAction");
    ActionMap actionMap = textField.getActionMap();
    actionMap.put("downAction", new DownAction());
    actionMap.put("upAction", new UpAction());
    actionMap.put("enterAction", enterAction);
  }

  /**
   * Get the attribute to display on the text box after selecting a value. Should be unique constraint.
   * 
   * @return
   */
  protected abstract String[] getDisplayAttributes();

  /**
   * Get the attributes to show information about the entity. Each attribute is shown on a column of the
   * dropdown
   * table.
   */
  protected abstract String[] getAttributeColumns();

  /**
   * The attributes to search on when user type in textbox. </br> <b>Pay attention:</b> The attributes must be
   * String
   * type, or you need to override the {@link #createSearchCriteria()}.
   */
  protected abstract String[] getSearchedOnAttributes();

  /**
   * @return
   */
  private String createPromptText() {
    StringBuilder sb = new StringBuilder();
    sb.append(ControlConfigUtils.getString("SearchComponent.input")).append(UIConstants.BLANK);
    List<String> attributes = new ArrayList<>(searchOnAttributes.length);
    for (String attribute : searchOnAttributes) {
      attributes.add(ControlConfigUtils.getString("label." + entityClass.getSimpleName() + '.' + attribute));
    }
    sb.append(StringUtils.join(attributes, ", "));
    return sb.toString();
  }

  private class DownAction extends AbstractAction {
    private static final long serialVersionUID = 3977021150275942850L;

    @Override
    public void actionPerformed(ActionEvent e) {

      if (!isTableShown) {
        popup.setLocation(textField.getLocationOnScreen().x,
                textField.getLocationOnScreen().y + textField.getPreferredSize().height);
        popup.remove(suggestPanel);
        popup.add(tablePane);
        popup.setVisible(true);
        isTableShown = true;
        doChangeText();
      }
      if (!entities.isEmpty()) {
        int currentRow = table.getSelectedRow();
        if (currentRow == table.getRowCount() - 1) {
          currentRow = 0;
        } else {
          currentRow++;
        }
        table.setRowSelectionInterval(currentRow, currentRow);
      }
    }
  }

  private class UpAction extends AbstractAction {
    private static final long serialVersionUID = -6870345786236419863L;

    @Override
    public void actionPerformed(ActionEvent e) {

      if (!popup.isVisible()) {
        return;
      }
      if (!entities.isEmpty()) {
        int currentRow = table.getSelectedRow();
        if (currentRow == 0) {
          currentRow = table.getRowCount() - 1;
        } else {
          currentRow--;
        }
        table.setRowSelectionInterval(currentRow, currentRow);
      }
    }
  }

  private class EnterAction extends AbstractAction {
    private static final long serialVersionUID = 4596446039663232049L;

    @Override
    public void actionPerformed(ActionEvent e) {

      if (!popup.isVisible()) {
        return;
      }
      if (!entities.isEmpty()) {
        int currentRow = table.getSelectedRow();
        setSelectedEntity(entities.get(currentRow));
        popup.remove(tablePane);
        popup.add(suggestPanel);
        popup.pack();
        isTableShown = false;
      }
    }

  }

  private String buildDisplayString(String[] displayAttribute, BeanWrapper wrapper) {
    List<String> s = new ArrayList<>(displayAttribute.length);
    for (String atb : displayAttribute) {
      s.add((String) wrapper.getPropertyValue(atb));
    }
    return StringUtils.join(s, " - ");
  }

  @Override
  public boolean requestFocusInWindow() {
    return textField.requestFocusInWindow();
  }

  private class SearchResultTable extends AbstractTableModel {
    private static final long serialVersionUID = -7789476798175848307L;

    @Override
    public int getRowCount() {
      return entities.size();
    }

    @Override
    public String getColumnName(int column) {
      return ControlConfigUtils
              .getString("label." + entityClass.getSimpleName() + '.' + attributeColumns[column]);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return IziClassUtils.getClassOfField(attributeColumns[columnIndex], entityClass);
    }

    @Override
    public int getColumnCount() {
      return attributeColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      BeanWrapper wrapper = new BeanWrapperImpl(entities.get(rowIndex));
      return wrapper.getPropertyValue(attributeColumns[columnIndex]);
    }
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    doChangeText();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    doChangeText();
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    doChangeText();
  }

  /**
   * Perform when insert, remove or change the text in textField
   */
  @SuppressWarnings({"unchecked"})
  protected void doChangeText() {
    if (textField.hasFocus()) {
      if (popup.isVisible() && isTableShown) {
        entities.clear();
        entities.addAll((List<T>) ConfigProvider.getReferenceDataList(cacheId));
        ((SearchResultTable) table.getModel()).fireTableDataChanged();
        table.packAll();
        popup.pack();
      } else {
        showTipPanel();
      }
    }
  }

  private void showTipPanel() {
    popup.remove(tablePane);
    isTableShown = false;
    popup.setLocation(textField.getLocationOnScreen().x,
            textField.getLocationOnScreen().y + textField.getPreferredSize().height);
    popup.add(suggestPanel);
    popup.setVisible(true);
    popup.pack();
  }

  public void setCacheId(String cacheId) {
    this.cacheId = cacheId;
  }

  public void setSelectedEntity(T entity) {
    selectedEntity = entity;
    if (entity != null) {
      BeanWrapper wrapper = new BeanWrapperImpl(selectedEntity);
      textField.setText(buildDisplayString(displayAttribute, wrapper));
    } else {
      textField.setText("");
    }
    fireValueChanged();
  }

  public T getSelectedEntity() {
    return selectedEntity;
  }

  // ///////// Fire event ///////////////
  private void fireValueChanged() {
    for (Object l : listenerList.getListenerList()) {
      ChangeEvent ce = new ChangeEvent(this);
      if (l instanceof ChangeListener) {
        ((ChangeListener) l).stateChanged(ce);
      }
    }
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    listenerList.add(ChangeListener.class, listener);
    changeListeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener) {
    listenerList.remove(ChangeListener.class, listener);
    changeListeners.remove(listener);
  }

}
