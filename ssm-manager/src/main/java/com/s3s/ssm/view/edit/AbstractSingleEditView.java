/*
 * AbstractDetailView
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
package com.s3s.ssm.view.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import com.s3s.ssm.dto.ICodeObject;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.interfaces.IDirtiableObject;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.WidgetConstant;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.ASearchComponent;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.DetailDataModel.GroupInfoData;
import com.s3s.ssm.view.edit.DetailDataModel.TabInfoData;
import com.s3s.ssm.view.factory.SComponentFactory;
import com.s3s.ssm.view.helper.SComponentHelpder;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.widget.ImageChooser;
import com.s3s.ssm.widget.JVComboBox;
import com.s3s.ssm.widget.JVDoubleField;
import com.s3s.ssm.widget.JVLongField;
import com.s3s.ssm.widget.JVTextField;
import com.s3s.ssm.widget.MultiSelectionListBox;
import com.s3s.ssm.widget.VCheckBoxList;
import com.s3s.ssm.widget.VDateSpinner;
import com.s3s.ssm.widget.VNumberSpinner;

public abstract class AbstractSingleEditView<T extends ICodeObject> extends
        AbstractEditView<T> implements ChangeListener {

  public static final int DEFAULT_WIDTH = 250;

  private static final long serialVersionUID = 1L;

  private final DetailDataModel detailDataModel = new DetailDataModel();
  private final Map<String, AttributeComponent> name2AttributeComponent = new HashMap<>();

  /**
   * TODO: This method is not good. Sub-class must cast too many components.
   * 
   * @return
   */
  protected Map<String, AttributeComponent> getName2AttributeComponent() {
    return name2AttributeComponent;
  }

  protected JComponent getComponent(String property) {
    return name2AttributeComponent.get(property).getComponent();
  }

  private BeanWrapper beanWrapper;

  protected final ReferenceDataModel refDataModel = new ReferenceDataModel();

  private NotifyPanel notifyPanel;

  protected JToolBar toolbar;
  private JButton btnSave;

  private Action newAction;
  private Action saveAction;
  private Action closeAction;

  public AbstractSingleEditView() {
    this(null);
  }

  public AbstractSingleEditView(Map<String, Object> request) {
    super(request);
    if (entity != null) {
      beanWrapper = new BeanWrapperImpl(this.entity);
      contructView(this.entity);
    }
  }

  private void contructView(T entity) {
    initialPresentationView(detailDataModel);
    setReferenceDataModel(refDataModel, entity);

    newAction = new NewAction();
    saveAction = new SaveAction();
    closeAction = new CloseAction();

    initComponents();
    addKeyBindings();
  }

  protected void setVisibleToolBar(boolean visible) {
    toolbar.setVisible(visible);
  }

  protected void setVisibleSaveButton(boolean visible) {
    btnSave.setEnabled(visible);
  }

  private void addKeyBindings() {
    // Key binding
    InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);

    // Ctrl-N to add new row
    KeyStroke newShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
    KeyStroke saveShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
    KeyStroke closeShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK);

    inputMap.put(newShortkey, "newActionKey");
    inputMap.put(saveShortkey, "saveActionKey");
    inputMap.put(closeShortkey, "closeActionKey");

    ActionMap actionMap = getActionMap();
    actionMap.put("newActionKey", newAction);
    actionMap.put("saveActionKey", saveAction);
    actionMap.put("closeActionKey", closeAction);
  }

  protected abstract void initialPresentationView(DetailDataModel detailDataModel);

  protected void initComponents() {
    setLayout(new BorderLayout());
    JPanel topPanel = new JPanel(new VerticalLayout());

    toolbar = initToolbarAction();
    notifyPanel = initNotifyPanel();
    topPanel.add(toolbar);
    topPanel.add(notifyPanel);

    add(topPanel, BorderLayout.NORTH);
    initFields();
    customizeComponents();
  }

  private JToolBar initToolbarAction() {
    JToolBar toolbar = createActionToolBar();
    toolbar.setVisible(!isReadOnly());
    return toolbar;
  }

  private void initFields() {
    List<TabInfoData> tabList = detailDataModel.getTabList();
    int numOfAttributes = detailDataModel.getDetailAttributes().size();
    if (CollectionUtils.isNotEmpty(tabList)) {
      JTabbedPane tabPane = createTabPane(tabList, numOfAttributes);
      add(tabPane, BorderLayout.CENTER);
    } else {
      JPanel fieldsPanel = createFieldsPanel(0, numOfAttributes);
      add(fieldsPanel, BorderLayout.CENTER);
    }
  }

  private NotifyPanel initNotifyPanel() {
    NotifyPanel notifyPanel = new NotifyPanel();
    notifyPanel.setName("notifyPanel");
    return notifyPanel;
  }

  private JToolBar createActionToolBar() {
    JToolBar toolbar = new JToolBar();
    btnSave = initSaveButton();
    toolbar.add(btnSave);
    return toolbar;
  }

  private JButton initSaveButton() {
    JButton btnSave = new JButton();
    btnSave.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SAVE_ICON));
    btnSave.setName("btnSave");
    btnSave.setText(ControlConfigUtils.getString("default.button.save"));
    btnSave.setToolTipText(ControlConfigUtils.getString("edit.button.save.tooltip"));
    btnSave.addActionListener(saveAction);
    btnSave.setEnabled(false);
    return btnSave;
  }

  /**
   * Customize the components.
   * 
   * @param name2AttributeComponent
   */
  protected void customizeComponents() {
    // Template method
  }

  /**
   * Set data for ComboBox, MultiSelectBox.
   * 
   * @param refDataModel
   * @param entity
   */
  protected void setReferenceDataModel(ReferenceDataModel refDataModel, T entity) {
    // Template method
  }

  private JTabbedPane createTabPane(List<TabInfoData> tabList, int numOfAttributes) {
    Assert.isTrue(tabList.get(0).getStartIndex() == 0, "Tab must be added before attribute");
    JTabbedPane tabPane = new JTabbedPane();
    tabPane.setName("EditViewTabbedPane");
    for (int i = 0; i < tabList.size() - 1; i++) {
      TabInfoData tab = tabList.get(i);
      JPanel pane = createFieldsPanel(tab.getStartIndex(), tabList.get(i + 1).getStartIndex());
      tabPane.addTab(tab.getName(), tab.getIcon(), pane, tab.getTooltip());
    }
    TabInfoData endTab = tabList.get(tabList.size() - 1);
    JPanel pane = createFieldsPanel(endTab.getStartIndex(), numOfAttributes);
    tabPane.addTab(endTab.getName(), endTab.getIcon(), pane, endTab.getTooltip());
    return tabPane;
  }

  private JPanel createFieldsPanel(int beginTabIndex, int endTabIndex) {
    JPanel fieldsPanel = new JPanel(new MigLayout("ins 0"));
    fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    int i = beginTabIndex;
    for (GroupInfoData g : detailDataModel.getGroupList()) {
      if (g.getStartGroupIndex() >= beginTabIndex && g.getEndGroupIndex() <= endTabIndex) {
        addFields(fieldsPanel, i, g.getStartGroupIndex(), null);
        addFields(fieldsPanel, g.getStartGroupIndex(), g.getEndGroupIndex(), g);
        i = g.getEndGroupIndex();
      }
    }
    addFields(fieldsPanel, i, endTabIndex, null);
    return fieldsPanel;
  }

  /**
   * 
   * @param fieldsPanel
   *          the panel which fields add to.
   * @param startIndex
   *          the inclusive index in detailDataModel.getDetailAttribute()
   * @param endIndex
   *          the exclusive index in detailDataModel.getDetailAttribute()
   * @param g
   *          the group info data of panel. If not null -> The panel render on the group panel.
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  private JPanel addFields(JPanel fieldsPanel, int startIndex, int endIndex, GroupInfoData g) {
    JPanel pnlEdit = null;
    if (g != null) { // in case of group of fields.
      pnlEdit = new JXTaskPane();
      ((JXTaskPane) pnlEdit).setTitle(g.getName());
      ((JXTaskPane) pnlEdit).setIcon(g.getIcon());
      pnlEdit.setLayout(new MigLayout("ins 0"));
      fieldsPanel.add(pnlEdit, "newline, spanx");
    } else {
      pnlEdit = fieldsPanel;
    }
    for (int i = startIndex; i < endIndex; i++) {
      DetailAttribute attribute = detailDataModel.getDetailAttributes().get(i);
      String newline = attribute.isNewColumn() ? "left, gapleft 10, " : "left, newline, ";
      JLabel lblLabel = SComponentFactory.createLabel(generateTitleField(attribute));
      JComponent dataField = null;
      Object constrains = newline;

      boolean enable = attribute.isEnable();
      Object value = getValue(attribute);
      String name = attribute.getName();
      int componentWidth = getWidth(attribute);
      boolean editable = attribute.isEditable();
      boolean mandatory = attribute.isMandatory();

      switch (attribute.getType()) {
        case LABEL:
          final JLabel jLabel = SComponentFactory.createLabel(String.valueOf(value));
          jLabel.setPreferredSize(new Dimension(componentWidth, jLabel.getPreferredSize().height));
          dataField = jLabel;
          break;
        case LONG_NUMBER:
          final JVLongField longField = SComponentFactory.createLongTextField(name, enable, componentWidth,
                  editable, value);
          dataField = longField;
          break;
        case DOUBLE_NUMBER:
          final JVDoubleField doubleField = SComponentFactory.createDoubleTextField(name, enable,
                  componentWidth, editable, value);
          dataField = doubleField;
          break;
        case POSITIVE_NUMBER_SPINNER:
          int numberValue = 0;
          if (value != null) {
            numberValue = (int) value;
          }
          final VNumberSpinner posNumberSpinner = SComponentFactory.createPositiveNumberSpinner(name, enable,
                  numberValue);
          dataField = posNumberSpinner;
          break;
        case TEXTBOX:
          final JVTextField textField = SComponentFactory.createTextBox(beanWrapper, attribute,
                  componentWidth, editable, value);
          dataField = textField;
          break;
        case TEXTAREA:
          final JTextArea ta = SComponentFactory.createTextArea(value, isReadOnly());
          final JScrollPane scrollPane = new JScrollPane(ta);
          scrollPane.setPreferredSize(new Dimension(componentWidth, scrollPane.getPreferredSize().height));
          dataField = scrollPane;
          constrains = newline + "top";
          break;
        case PASSWORD:
          final JPasswordField pwField = SComponentFactory.createPasswordField(enable, componentWidth,
                  editable, value);
          dataField = pwField;
          break;
        case DROPDOWN:
          final JVComboBox comboBox = SComponentFactory.createDropdownComponent(mandatory,
                  componentWidth, value, getReferenceData(attribute).getValues());
          dataField = comboBox;
          break;
        case DROPDOWN_AUTOCOMPLETE:
          final JComboBox<?> comboBoxAc = SComponentFactory.createDropdownComponent(mandatory,
                  componentWidth, value, getReferenceData(attribute).getValues());
          AutoCompleteDecorator.decorate(comboBoxAc);
          dataField = comboBoxAc;
          break;
        case MULTI_SELECT_LIST_BOX:
          final MultiSelectionListBox<?> mulStListBox = SComponentFactory.createMultiSelectionListBox(
                  componentWidth, value, getReferenceData(attribute));
          dataField = mulStListBox;
          constrains = newline + "top";
          break;
        case CHECKBOX_LIST:
          final VCheckBoxList checkBoxList = SComponentFactory.createCheckBoxList(value,
                  getReferenceData(attribute));
          final JScrollPane scrollPane1 = new JScrollPane(checkBoxList);
          scrollPane1.setPreferredSize(new Dimension(componentWidth, scrollPane1.getPreferredSize().height));
          dataField = scrollPane1;
          constrains = newline + "top";
          break;
        case DATE:
          final VDateSpinner dp = SComponentFactory.createDateSpinner(WidgetConstant.YYYY_MM_DD_FORMAT,
                  (Date) value);
          dataField = dp;
          break;
        case RADIO_BUTTON_GROUP:
          dataField = SComponentFactory.createRadioButtonsGroup(getReferenceData(attribute), value);
          break;
        case IMAGE:
          final ImageChooser ic = SComponentFactory.createImageChooser(value);
          dataField = ic;
          constrains = newline + "top";
          break;
        case CHECKBOX:
          dataField = SComponentFactory.createCheckbox(value, editable);
          break;
        case EDITOR:
          JEditorPane editor = SComponentFactory.createEditor(value, editable);
          JScrollPane editorScrollPane = new JScrollPane(editor);
          editorScrollPane.setPreferredSize(new Dimension(600, 400));
          dataField = editorScrollPane;
          break;
        case SEARCHER:
          final ASearchComponent<? extends AbstractBaseIdObject> sc = SComponentFactory
                  .createSearchComponent(attribute, componentWidth, value);
          sc.addChangeListener(this);
          dataField = sc;
          break;
        case LIST:
          final ListComponentInfo listInfo = (ListComponentInfo) attribute.getComponentInfo();
          final AListComponent listComponent = SComponentFactory.createListComponent(
                  listInfo.getListComponent(), value, editable);
          dataField = listComponent;
          break;
        case HOUR_MIN_SEC:
          final VDateSpinner hmsc = SComponentFactory.createDateSpinner(WidgetConstant.HH_MM_SS_FORMAT,
                  (Date) value);
          hmsc._timeEditor.getTextField().setEditable(editable);
          dataField = hmsc;
          break;
        case HOUR_MIN:
          final VDateSpinner hm = SComponentFactory.createDateSpinner(WidgetConstant.HH_MM_FORMAT,
                  (Date) value);
          hm._timeEditor.getTextField().setEditable(editable);
          dataField = hm;
          break;
        case BLOCK:
          lblLabel = new JLabel();
          dataField = new JLabel();
          break;
        default:
          throw new RuntimeException("FieldType does not supported!");
      }
      dataField.setEnabled(!isReadOnly());
      dataField.setName(name);
      addChangeListenerForField(attribute, dataField);
      if (DetailFieldType.LIST.equals(attribute.getType())) {
        pnlEdit.add(dataField, "newline, spanx , width " + dataField.getWidth());
      } else {
        pnlEdit.add(lblLabel, constrains);
        pnlEdit.add(dataField, "split 2");
      }
      JLabel errorIcon = addErrorIcon(pnlEdit);
      // TODO: add listener here for listening attribute updated
      name2AttributeComponent.put(name, new AttributeComponent(lblLabel, dataField, errorIcon));
    }
    addAditionalComponent(pnlEdit);
    return pnlEdit;
  }

  @SuppressWarnings("unused")
  protected void addAditionalComponent(JPanel pnlEdit) {
    // Template method
  }

  private int getWidth(DetailAttribute attribute) {
    return attribute.getWidth() == 0 ? DEFAULT_WIDTH : attribute.getWidth();
  }

  private Object getValue(DetailAttribute attribute) {
    return attribute.isRaw() ? attribute.getValue() : beanWrapper.getPropertyValue(attribute.getName());
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private ReferenceData getReferenceData(DetailAttribute attribute) {
    return refDataModel.new ReferenceData(attribute.getDataList(), null);
  }

  private String generateTitleField(DetailAttribute attribute) {
    String titleField = "<html>";
    if (attribute.getLabel() != null) {
      titleField += attribute.getLabel();
    } else {
      titleField += ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
              + attribute.getName());
    }

    if (attribute.isMandatory()) {
      titleField += " <FONT COLOR=RED>*</FONT>";
    }
    titleField += "</html>";
    return titleField;
  }

  private void addChangeListenerForField(final DetailAttribute attribute, JComponent dataField) {
    if (dataField instanceof IDirtiableObject) {
      if (!isReadOnly()) {
        ((IDirtiableObject) dataField).addChangeListener(this);
      }
    }

    if (isTextAreaOrdCheckBoxList(attribute)) {
      JScrollPane scrollPane = (JScrollPane) dataField;
      ((IDirtiableObject) scrollPane.getViewport().getComponent(0)).addChangeListener(this);
    }
  }

  private boolean isTextAreaOrdCheckBoxList(final DetailAttribute attribute) {
    return DetailFieldType.TEXTAREA.equals(attribute.getType())
            || DetailFieldType.CHECKBOX_LIST.equals(attribute.getType());
  }

  private JLabel addErrorIcon(JPanel pnlEdit) {
    JLabel errorIcon = new JLabel(IziImageUtils.getIcon(IziImageConstants.ERROR_ICON));
    errorIcon.setVisible(false);
    pnlEdit.add(errorIcon);
    return errorIcon;
  }

  @SuppressWarnings("unused")
  @Override
  public void stateChanged(ChangeEvent e) {
    if (isDirty()) {
      btnSave.setEnabled(true);
    } else {
      btnSave.setEnabled(false);
    }
  }

  private boolean isReadOnly() {
    return Boolean.TRUE.equals(request.get(PARAM_READONLY));
  }

  protected boolean doSave() {
    notifyPanel.clearMessage();
    boolean changedCode = entity.getCode() == null ? false : !entity.getCode().equals(getCodeFromUI());
    if (changedCode && exists(getCodeFromUI())) {
      Problem notUniqueProblem = new Problem(Severity.FATAL,
              getMessage("custome.constraints.NotUnique.message"));
      notifyPanel.setProblem(notUniqueProblem);
      return false;
    }
    if (!validateForm()) {
      return false;
    }
    bindValue();

    clearErrorOnScreen();
    try {
      beforeSaveOrUpdate(entity);
      saveOrUpdate(entity);
      afterSaveOrUpdate(entity);
      btnSave.setEnabled(false);
      showInformationAfterSaving();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private boolean validateForm() {
    detailDataModel.getDetailAttributes().forEach(attribute -> {
      if (attribute.isMandatory() && getValueFromUI(attribute) == null) {
        Problem mandatoryProblem = new Problem(Severity.FATAL,
                getMessage("custome.constraints.Mandatory.message"));
        notifyPanel.setProblem(mandatoryProblem);
      }
    });
    return true;
  }

  private String getCodeFromUI() {
    AttributeComponent attributeComponent = name2AttributeComponent.get("code");
    if (attributeComponent != null) {
      JComponent component = attributeComponent.getComponent();
      return (String) SComponentHelpder.getComponentValue(component, DetailFieldType.TEXTBOX);
    }
    return null;
  }

  public void bindValue() {
    for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
      Object value = getValueFromUI(attribute);
      bindingValue(entity, attribute.getName(), value, attribute);
    }
  }

  private Object getValueFromUI(DetailAttribute attribute) {
    JComponent component = name2AttributeComponent.get(attribute.getName()).getComponent();
    return SComponentHelpder.getComponentValue(component, attribute.getType());
  }

  private void showInformationAfterSaving() {
    Problem saveSuccessProblem = new Problem(Severity.INFO, getMessage("edit.message.saveSuccess"));
    notifyPanel.setProblem(saveSuccessProblem);
    notifyPanel.setVisible(true);
  }

  private void clearErrorOnScreen() {
    for (AttributeComponent at : name2AttributeComponent.values()) {
      at.getLabel().setForeground(Color.BLACK);
      at.getErrorIcon().setVisible(false);
    }
  }

  protected abstract void saveOrUpdate(T entity);

  @SuppressWarnings("unused")
  protected void beforeSaveOrUpdate(T entity) {

  }

  protected abstract boolean exists(String code);

  @SuppressWarnings("unused")
  protected void afterSaveOrUpdate(T entity) {
    // Template method
  }

  /**
   * Binding value to the entity. Default implementation is binding value for main attributes. The child class
   * should
   * override this method to binding for the raw attributes.
   * 
   * @param entity
   *          the entity binded.
   * @param name
   *          the name of attribute
   * @param isRaw
   *          is raw attribute or not
   * @param value
   *          the value of the component.
   * @param type
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private void bindingValue(T entity, String name, Object value, DetailAttribute attribute) {
    // The child class should override this
    if (!attribute.isRaw()) {
      if (attribute.getType() == DetailFieldType.LIST) {
        Collection attributeValue = (Collection) beanWrapper.getPropertyValue(name);
        Collection componentValue = (Collection) value;
        String parentName = ((ListComponentInfo) attribute.getComponentInfo()).getParentFieldName();
        for (Object object : componentValue) {
          BeanWrapper bw = new BeanWrapperImpl(object);
          bw.setPropertyValue(parentName, entity);
        }
        // For hibernate cascade, we should not change the reference of a collection. Just remove all and the
        // re-add all value.
        attributeValue.clear();
        attributeValue.addAll(componentValue);
      } else {
        beanWrapper.setPropertyValue(name, value);
      }
    }
  }

  private void doCloseOrNewWithDirtyCheck(boolean isNew) {
    if (isDirty()) {
      int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this),
              getMessage("edit.warning.cancel.message"),
              getMessage("edit.warning.cancel.title"), JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.WARNING_MESSAGE, null);
      if (option == JOptionPane.YES_OPTION) {
        if (doSave()) {
          doCloseOrNew(isNew);
        }
      } else if (option == JOptionPane.NO_OPTION) {
        doCloseOrNew(isNew);
      }
    } else {
      doCloseOrNew(isNew);
    }
  }

  private void doNew() {
    doCloseOrNewWithDirtyCheck(true);
  }

  private void doClose() {
    doCloseOrNewWithDirtyCheck(false);
  }

  private void doCloseOrNew(boolean isNew) {
    if (isNew) {
      getListView().performNewAction();
    } else {
      JTabbedPane tabbedPane = getListView().getTabbedPane();
      tabbedPane.remove(tabbedPane.indexOfComponent(this));
    }
  }

  protected boolean isDirty() {
    for (AttributeComponent attributeComponent : getName2AttributeComponent().values()) {
      if (attributeComponent.getComponent() instanceof IDirtiableObject) {
        IDirtiableObject object = (IDirtiableObject) attributeComponent.getComponent();
        if (object.isDirty()) {
          return true;
        }
      }
      if (attributeComponent.getComponent() instanceof JScrollPane) {
        JScrollPane scrollPane = (JScrollPane) attributeComponent.getComponent();
        Component component = scrollPane.getViewport().getComponent(0);
        if (component instanceof IDirtiableObject) {
          if (((IDirtiableObject) component).isDirty()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public boolean requestFocusInWindow() {
    return name2AttributeComponent.get(detailDataModel.getDetailAttributes().get(0).getName()).getComponent()
            .requestFocusInWindow();
  }

  @Override
  protected String getDefaultTitle(T entity) {
    return entity.getCode();
  }

  public JButton getBtnSave() {
    return btnSave;
  }

  private class SaveAction extends AbstractAction {
    private static final long serialVersionUID = -5708037888530296878L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent evt) {
      if (isDirty()) {
        doSave();
      }
    }
  }

  private class NewAction extends AbstractAction {
    private static final long serialVersionUID = 274591266008792195L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent evt) {
      if (isDirty()) {
        doNew();
      }
    }
  }

  private class CloseAction extends AbstractAction {
    private static final long serialVersionUID = 274591266008792195L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent evt) {
      doClose();
    }
  }
}
