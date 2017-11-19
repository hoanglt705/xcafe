package com.s3s.ssm.view.factory;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.springframework.beans.BeanWrapper;
import org.springframework.util.Assert;

import com.jidesoft.swing.CheckBoxList;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.renderer.NullListCellRenderer;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.view.component.ASearchComponent;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.component.validation.NumberInputVerifier;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.SearchComponentInfo;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.widget.ImageChooser;
import com.s3s.ssm.widget.JVCheckBox;
import com.s3s.ssm.widget.JVComboBox;
import com.s3s.ssm.widget.JVDoubleField;
import com.s3s.ssm.widget.JVEditorPane;
import com.s3s.ssm.widget.JVFormattedTextField;
import com.s3s.ssm.widget.JVLongField;
import com.s3s.ssm.widget.JVTextArea;
import com.s3s.ssm.widget.JVTextField;
import com.s3s.ssm.widget.MultiSelectionListBox;
import com.s3s.ssm.widget.VCheckBoxList;
import com.s3s.ssm.widget.VDateSpinner;
import com.s3s.ssm.widget.VNumberSpinner;

public final class SComponentFactory {
  private final static Log logger = LogFactory.getLog(SComponentFactory.class);

  public static JButton createButton(String text) {
    return new JButton(text);
  }

  public static VDateSpinner createDateSpinner(String format, Date value) {
    VDateSpinner dateSpiner = null;
    if (value != null) {
      dateSpiner = new VDateSpinner(format, value);
    } else {
      dateSpiner = new VDateSpinner(format);
    }
    dateSpiner.setInitialContent(value);
    return dateSpiner;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static AListComponent<?> createListComponent(final AListComponent<?> aListComponent, Object value,
          boolean editable) {
    if (aListComponent == null) {
      throw new IllegalArgumentException("aListComponent should not be null");
    }
    AListComponent<?> listComponent = aListComponent;
    if (value != null) {
      Collection value2 = (Collection) value;
      if (!value2.isEmpty()) {
        listComponent.setData(value2);
      }
    }
    listComponent.setEditable(editable);
    listComponent.packAll();
    return listComponent;
  }

  @SuppressWarnings("unchecked")
  public static ASearchComponent<? extends AbstractBaseIdObject> createSearchComponent(
          DetailAttribute attribute, int width, Object value) {
    SearchComponentInfo info = (SearchComponentInfo) attribute.getComponentInfo();
    Assert.isTrue(info != null, "Search component need the info to initialize");
    @SuppressWarnings({"rawtypes", "null"})
    ASearchComponent sc = info.getSearchComponent();
    if (value != null) {
      sc.setSelectedEntity((AbstractBaseIdObject) value);
      sc.setInitialContent((AbstractBaseIdObject) value);
    }
    sc.setPreferredSize(new Dimension(width, sc.getPreferredSize().height));
    return sc;
  }

  public static JVCheckBox createCheckbox(Object value, boolean editable) {
    boolean isSelected = BooleanUtils.isTrue((Boolean) value);
    JVCheckBox cb = new JVCheckBox();
    cb.setEnabled(editable);
    cb.setSelected(isSelected);
    cb.setInitialContent(isSelected);
    return cb;
  }

  public static JEditorPane createEditor(Object value, boolean editable) {
    JVEditorPane editor = new JVEditorPane((String) value);
    editor.setEditable(editable);
    editor.setInitialContent((String) value);
    return editor;
  }

  public static ImageChooser createImageChooser(Object value) {
    byte[] bytes = (byte[]) value;
    ImageChooser ic = new ImageChooser(bytes);
    ic.setInitialContent(bytes);
    return ic;
  }

  public static JXDatePicker createDatePicker() {
    return new JXDatePicker();
  }

  public static JXDatePicker createDatePicker(String name) {
    JXDatePicker datePicker = new JXDatePicker();
    datePicker.getEditor().setName(name);
    return datePicker;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static MultiSelectionListBox<?> createMultiSelectionListBox(int width, Object value,
          ReferenceData referenceData) {
    List desValues = value != null ? new ArrayList((Collection<?>) value) : Collections.EMPTY_LIST;
    List scrValues = new ArrayList<>(ListUtils.removeAll(referenceData.getValues(), desValues));
    MultiSelectionListBox mulStListBox = new MultiSelectionListBox<>(scrValues, desValues,
            referenceData.getRenderer());
    mulStListBox.setPreferredSize(new Dimension(width, mulStListBox.getPreferredSize().height));
    mulStListBox.setInitialContent(desValues);
    return mulStListBox;
  }

  public static JPasswordField createPasswordField(boolean isEnable, int width, boolean editable, Object value) {
    JPasswordField pwField = new JPasswordField();
    pwField.setPreferredSize(new Dimension(width, pwField.getPreferredSize().height));
    pwField.setEditable(editable);
    pwField.setEnabled(isEnable);
    pwField.setText(ObjectUtils.toString(value));
    return pwField;
  }

  public static JVTextArea createTextArea(Object value, boolean isReadOnly) {
    JVTextArea ta = new JVTextArea();
    ta.setRows(UIConstants.DEFAUL_TEXTAREA_ROWS);
    ta.setLineWrap(true);
    String txtValue = value != null ? StringUtils.trimToEmpty(String.valueOf(value)) : null;
    ta.setText(txtValue);
    ta.setInitialContent(txtValue);
    ta.setEditable(!isReadOnly);
    return ta;
  }

  public static JVFormattedTextField createNumberTextBox(BeanWrapper beanWrapper, String name,
          boolean isEnable, int width, boolean isRaw, boolean editable, Object value) {
    final Class<?> propertyReturnType = getReturnType(beanWrapper, name, isRaw, value);
    return createNumberTextBox(propertyReturnType, isEnable, width, editable, value);
  }

  public static JVLongField createLongTextField(String name, boolean isEnable, int width, boolean editable,
          Object value) {
    JVLongField longField = (value != null) ? new JVLongField((long) value) : new JVLongField();
    longField.setName(name);
    longField.setEnabled(isEnable);
    longField.setEditable(editable);
    longField.setPreferredSize(new Dimension(width, longField.getHeight()));
    return longField;
  }

  public static JVDoubleField createDoubleTextField(String name, boolean isEnable, int width,
          boolean editable, Object value) {
    JVDoubleField doubleField = (value != null) ? new JVDoubleField((double) value) : new JVDoubleField();
    doubleField.setName(name);
    doubleField.setEnabled(isEnable);
    doubleField.setEditable(editable);
    doubleField.setPreferredSize(new Dimension(width, doubleField.getHeight()));
    return doubleField;
  }

  public static VNumberSpinner createPositiveNumberSpinner(String name, boolean editable, Object value) {
    VNumberSpinner numberSpinner = new VNumberSpinner((int) value, 0);
    numberSpinner.setEnabled(editable);
    numberSpinner.setName(name);
    numberSpinner.setPreferredSize(new Dimension(20, numberSpinner.getHeight()));
    return numberSpinner;
  }

  public static VNumberSpinner createNumberSpinner(String name, boolean editable, Object value) {
    VNumberSpinner numberSpinner = new VNumberSpinner((int) value);
    numberSpinner.setEnabled(editable);
    numberSpinner.setName(name);
    numberSpinner.setPreferredSize(new Dimension(20, numberSpinner.getHeight()));
    return numberSpinner;
  }

  public static JVFormattedTextField createNumberTextBox(Class<?> propertyReturnType, boolean isEnable,
          int width, boolean editable, Object value) {
    NumberFormatter numFormatter = new NumberFormatter();
    numFormatter.setValueClass(propertyReturnType);

    final JVFormattedTextField numberTextField = new JVFormattedTextField(numFormatter);
    numberTextField.setHorizontalAlignment(SwingConstants.RIGHT);
    numberTextField.setEditable(editable);
    if (width > 0) {
      numberTextField.setPreferredSize(new Dimension(width, numberTextField.getHeight()));
    }
    numberTextField.setEnabled(isEnable);
    if (value != null) {
      numberTextField.setValue(value);
      numberTextField.setInitialContent(value);
    }
    numberTextField.setInputVerifier(new NumberInputVerifier());
    numberTextField.addFocusListener(new FocusAdapter() {

      @Override
      public void focusLost(FocusEvent e) {
        try {
          numberTextField.commitEdit();
        } catch (ParseException e1) {
          e1.printStackTrace();
          logger.error(e1.getMessage());
        }
      }

    });
    return numberTextField;
  }

  public static JVFormattedTextField createNumberTextBox(Class<?> propertyReturnType) {
    return createNumberTextBox(propertyReturnType, true, -1, true, null);
  }

  public static JVTextField createTextBox(BeanWrapper beanWrapper, DetailAttribute attribute,
          int width, boolean editable, Object value) {
    JVTextField textField = new JVTextField();
    String strValue = value != null ? StringUtils.trimToEmpty(String.valueOf(value)) : "";
    textField.setText(strValue);
    textField.setInitialContent(strValue);
    textField.setEditable(editable);
    textField.setPreferredSize(new Dimension(width, textField.getHeight()));
    textField.setEnabled(attribute.isEnable());
    return textField;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static JVComboBox createDropdownComponent(boolean isMandatory, int width, Object selectedValue,
          List referenceData) {
    List modifiedList = new ArrayList<>(referenceData);
    if (!isMandatory) {
      modifiedList.add(0, null);
    }
    JVComboBox comboBox = createDropdown(modifiedList.toArray());
    if (width > 0) {
      comboBox.setPreferredSize(new Dimension(width, comboBox.getPreferredSize().height));
    }
    comboBox.setRenderer(new NullListCellRenderer());
    if (selectedValue != null) {
      comboBox.setSelectedItem(selectedValue);
      comboBox.setInitialContent(selectedValue);
    }
    return comboBox;
  }

  public static JVComboBox createDropdownComponent(List referenceData) {
    return createDropdownComponent(false, -1, null, referenceData);
  }

  public static JVComboBox createDropdown(Object[] data) {
    return new JVComboBox(data);
  }

  public static JLabel createLabel(String label) {
    JLabel lb = new JLabel(label);
    Dimension preferredSize = lb.getPreferredSize();
    // Use JXLabel to wrap the label, if just using the JXLable --> It is not work for Group panel, so I work
    // around
    // by using the JLabel to measure and set the min width for JXTable.
    if (preferredSize.width > UIConstants.MAX_LABEL_WIDTH) {
      JXLabel jxlbl = new JXLabel(label);
      jxlbl.setLineWrap(true);
      jxlbl.setMinimumSize(new Dimension(UIConstants.MAX_LABEL_WIDTH, preferredSize.height));
      jxlbl.setMaxLineSpan(UIConstants.MAX_LABEL_WIDTH);
      return jxlbl;
    }
    return lb;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static JComponent createRadioButtonsGroup(ReferenceData referenceData, Object value) {
    return new RadioButtonsGroup<>(referenceData.getValue2LabelMap(), value);
  }

  private static Class<?> getReturnType(BeanWrapper beanWrapper, String name, boolean isRaw, Object value) {
    Class<?> propertyReturnType = null;
    if (isRaw) {
      propertyReturnType = value.getClass();
    } else {
      propertyReturnType = beanWrapper.getPropertyType(name);
    }
    return propertyReturnType;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static VCheckBoxList createCheckBoxList(Object checkedValues, ReferenceData referenceData) {
    List selectedValues = checkedValues != null ? new ArrayList((Collection<?>) checkedValues)
            : Collections.EMPTY_LIST;
    DefaultListModel listModel = new DefaultListModel<>();
    for (Object object : referenceData.getValues()) {
      listModel.addElement(object);
    }
    listModel.add(0, CheckBoxList.ALL_ENTRY);
    VCheckBoxList checkBoxList = new VCheckBoxList(listModel);
    checkBoxList.setSelectedObjects(selectedValues.toArray());
    return checkBoxList;
  }
}
