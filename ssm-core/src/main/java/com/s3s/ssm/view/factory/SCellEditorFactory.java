package com.s3s.ssm.view.factory;

import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

import com.jidesoft.swing.AutoCompletionComboBox;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;

import de.javasoft.swing.table.CheckBoxTableCellEditor;
import de.javasoft.swing.table.DateComboBoxTableCellEditor;
import de.javasoft.swing.table.NumberTableCellEditor;
import de.javasoft.swing.table.SpinnerTableCellEditor;
import de.javasoft.swing.table.TextFieldTableCellEditor;

public final class SCellEditorFactory {
  private SCellEditorFactory() {
  }

  public static TableCellEditor createCellEditor(JTable table, ListEditorType editorType,
          Object selectedValue, Object[] listData, String name) {
    TableCellEditor defaultEditor = table.getDefaultEditor(Object.class);
    switch (editorType) {
      case TEXTFIELD:
        TextFieldTableCellEditor textFieldTableCellEditor = new TextFieldTableCellEditor(defaultEditor);
        textFieldTableCellEditor.getEditorComponent().setName(name + "_TABLE");
        if (selectedValue != null) {
          textFieldTableCellEditor.getEditorComponent().setText((String) selectedValue);
        }
        return textFieldTableCellEditor;
      case CHECKBOX:
        CheckBoxTableCellEditor checkBoxTableCellEditor = new CheckBoxTableCellEditor(defaultEditor);
        checkBoxTableCellEditor.getEditorComponent().setName(name + "_TABLE");
        checkBoxTableCellEditor.getEditorComponent().setHorizontalAlignment(SwingConstants.CENTER);
        return checkBoxTableCellEditor;
      case DATE_PICKER:
        DateComboBoxTableCellEditor dateComboBoxTableCellEditor = new DateComboBoxTableCellEditor(
                defaultEditor);
        dateComboBoxTableCellEditor.getEditorComponent().setDate((Date) selectedValue);
        return dateComboBoxTableCellEditor;
      case COMBOBOX:
        AutoCompletionComboBox comboBox = new AutoCompletionComboBox(listData);
        comboBox.setName(name + "_TABLE");
        comboBox.setStrict(false);
        if (selectedValue != null) {
          comboBox.setSelectedItem(selectedValue);
        }
        return new DefaultCellEditor(comboBox);
      case POSITIVE_NUMBER_SPINNER:
        SpinnerTableCellEditor spinnerTableCellEditor = new SpinnerTableCellEditor(defaultEditor);
        int spinnerValue;
        if (selectedValue == null) {
          spinnerValue = 1;
        } else {
          spinnerValue = (int) selectedValue;
        }
        spinnerTableCellEditor.getEditorComponent().setModel(
                new SpinnerNumberModel(spinnerValue, 1, Integer.MAX_VALUE, 1));
        return spinnerTableCellEditor;
      case LONG:
        return new NumberTableCellEditor(defaultEditor, Long.class, 0, 0);
      default:
        break;
    }
    return null;
  }
}
