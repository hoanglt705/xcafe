package com.s3s.ssm.view.edit;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * The class includes the components to render an attribute. Like label, component, errorIcon...
 */
public class AttributeComponent {
  private JLabel fLabel;
  private JComponent fComponent;
  private JLabel fErrorIcon;

  public AttributeComponent(JLabel label, JComponent component, JLabel errorIcon) {
    this.fLabel = label;
    this.fComponent = component;
    this.fErrorIcon = errorIcon;
  }

  public JLabel getLabel() {
    return fLabel;
  }

  public void setLabel(JLabel label) {
    this.fLabel = label;
  }

  public JComponent getComponent() {
    return fComponent;
  }

  public void setComponent(JComponent component) {
    this.fComponent = component;
  }

  public JLabel getErrorIcon() {
    return fErrorIcon;
  }

  public void setErrorIcon(JLabel errorIcon) {
    this.fErrorIcon = errorIcon;
  }
}
