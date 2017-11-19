package com.s3s.ssm.view.component.validation;

import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class NumberInputVerifier extends InputVerifier {
  @Override
  public boolean verify(JComponent input) {
    if (input instanceof JFormattedTextField) {
      JFormattedTextField ftf = (JFormattedTextField) input;
      AbstractFormatter formatter = ftf.getFormatter();
      if (formatter != null) {
        String text = ftf.getText();
        try {
          formatter.stringToValue(text);
          return true;
        } catch (ParseException pe) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean shouldYieldFocus(JComponent input) {
    return verify(input);
  }

}
