package com.s3s.ssm.view.helper;

import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.softsmithy.lib.swing.JDoubleField;
import org.softsmithy.lib.swing.JLongField;

import com.jidesoft.spinner.DateSpinner;
import com.s3s.ssm.view.component.ASearchComponent;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.widget.ImageChooser;
import com.s3s.ssm.widget.MultiSelectionListBox;
import com.s3s.ssm.widget.VCheckBoxList;
import com.s3s.ssm.widget.VDateSpinner;
import com.s3s.ssm.widget.VNumberSpinner;

public class SComponentHelpder {
  private SComponentHelpder() {
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static Object getComponentValue(JComponent component, DetailFieldType type) {
    switch (type) {
      case LABEL:
        JLabel lbl = (JLabel) component;
        return lbl.getText();
      case TEXTBOX:
        return ((JTextField) component).getText();
      case LONG_NUMBER:
        return ((JLongField) component).getLongValue();
      case DOUBLE_NUMBER:
        return ((JDoubleField) component).getDoubleValue();
      case POSITIVE_NUMBER_SPINNER:
        VNumberSpinner numberSpinner = (VNumberSpinner) component;
        return numberSpinner.getValue();
      case TEXTAREA:
        JScrollPane scrollPane = (JScrollPane) component;
        JTextArea rtxtField = (JTextArea) scrollPane.getViewport().getComponent(0);
        return StringUtils.trimToNull(rtxtField.getText());
      case PASSWORD:
        JPasswordField pwdField = (JPasswordField) component;
        return new String(pwdField.getPassword());
      case CHECKBOX:
        JCheckBox chkField = (JCheckBox) component;
        return chkField.isSelected();
      case DATE:
        VDateSpinner dateField = (VDateSpinner) component;
        return dateField.getDate();
      case DROPDOWN:
      case DROPDOWN_AUTOCOMPLETE:
        JComboBox<?> comboBox = (JComboBox<?>) component;
        return comboBox.getSelectedItem();
      case MULTI_SELECT_LIST_BOX:
        MultiSelectionListBox<?> multiBox = (MultiSelectionListBox<?>) component;
        // TODO Phuc: test this case
        return multiBox.getDestinationValues();
      case CHECKBOX_LIST:
        JScrollPane scrollPane1 = (JScrollPane) component;
        VCheckBoxList checkboxList = (VCheckBoxList) scrollPane1.getViewport().getComponent(0);
        Object[] selectedValues = checkboxList.getCheckBoxListSelectedValues();
        return Arrays.asList(selectedValues);
      case RADIO_BUTTON_GROUP:
        RadioButtonsGroup<?> radioBtnGroupField = (RadioButtonsGroup<?>) component;
        return radioBtnGroupField.getSelectedValue();
      case IMAGE:
        ImageChooser imageField = (ImageChooser) component;
        return imageField.getImageData();
      case SEARCHER:
        ASearchComponent searchComponent = (ASearchComponent) component;
        return searchComponent.getSelectedEntity();
      case LIST:
        AListComponent listComponent = (AListComponent) component;
        return listComponent.getData();
      case HOUR_MIN_SEC:
      case HOUR_MIN:
        DateSpinner hmc = (DateSpinner) component;
        return hmc.getValue();
      case BLOCK:
        return "";
      default:
        throw new RuntimeException("Do not support FieldTypeEnum " + type);
    }
  }
}
