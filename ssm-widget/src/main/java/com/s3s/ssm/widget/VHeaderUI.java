package com.s3s.ssm.widget;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSeparator;

import org.jdesktop.swingx.JXHeader;

import de.javasoft.plaf.synthetica.HeaderUI;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

public class VHeaderUI extends HeaderUI {
  private JXHeader header;

  @Override
  protected void installComponents(JXHeader paramJXHeader) {
    this.titleLabel = new JLabel();
    this.descriptionPane = new DescriptionPane() {
      private static final long serialVersionUID = -7766509177322885731L;

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(100, super.getPreferredSize().height);
      }

    };
    this.descriptionPane.setOpaque(false);
    this.descriptionPane.putClientProperty("Synthetica.opaque", Boolean.valueOf(false));

    this.imagePanel = new JLabel();
    installComponentDefaults(paramJXHeader);

    Insets localInsets = SyntheticaLookAndFeel.getInsets("Synthetica.header.insets", paramJXHeader);
    int i = 8;
    int j = 8;
    int k = 8;
    int l = 8;
    if (localInsets != null) {
      i = localInsets.top;
      j = localInsets.left;
      k = localInsets.bottom;
      l = localInsets.right;
    }
    int i1 = SyntheticaLookAndFeel.getInt("Synthetica.header.description.topGap", paramJXHeader, 8);
    int i2 = SyntheticaLookAndFeel.getInt("Synthetica.header.description.leftGap", paramJXHeader, j);
    paramJXHeader.setLayout(new GridBagLayout());
    paramJXHeader.add(this.titleLabel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 21, 2,
            new Insets(i, j, 0, l), 0, 0));
    paramJXHeader.add(this.descriptionPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 23, 2,
            new Insets(i1, i2, k, l), 0, 0));
    paramJXHeader.add(this.imagePanel, new GridBagConstraints(1, 0, 1, 2, 0.0D, 1.0D, 24, 0,
            new Insets(i, 0, k, l), 0, 0));
    if (!(SyntheticaLookAndFeel.getBoolean("Synthetica.header.separator.enabled", this.header, true)))
      return;
    JSeparator localJSeparator = new JSeparator(0);
    localJSeparator.setName("JXHeader.separator");
    paramJXHeader.add(localJSeparator, new GridBagConstraints(0, 2, 2, 1, 1.0D, 0.0D, 20, 2,
            new Insets(0, 0, 0, 0), 0, 0));
  }
}
