package com.s3s.ssm.view.controlpanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.FontUIResource;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.VerticalLayout;

//
// Special panel to display multiple tables
//
public class GroupPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public GroupPanel()
  {
    setLayout(new VerticalLayout());
    setOpaque(false);
  }

  public void addComponent(String title, JComponent component)
  {
    JXCollapsiblePane collapsible = new JXCollapsiblePane();
    collapsible.add(component);
    collapsible.setAnimated(false);

    final Action toggleAction = collapsible.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);

    JXHyperlink link = new JXHyperlink(toggleAction) {
      private static final long serialVersionUID = 1L;

      @Override
      public void updateUI() {
        super.updateUI();
        setFont(new FontUIResource(getFont().deriveFont(Font.BOLD)));

        Color foreground = UIManager.getColor("Label.foreground");
        setUnclickedColor(foreground);
        setClickedColor(foreground);
        setBorder(new MatteBorder(0, 0, 1, 0, foreground));
        setBorderPainted(true);

        toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));
        toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));
      }
    };
    link.setText(title);
    link.setFocusPainted(false);

    add(link);
    add(collapsible);
  }
}