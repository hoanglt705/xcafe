package com.s3s.ssm.widget;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.jdesktop.swingx.VerticalLayout;

import com.s3s.ssm.interfaces.IDirtiableObject;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.WidgetConstant;

import de.javasoft.swing.DetailsDialog;
import de.javasoft.swing.ExtendedFileChooser;

public class ImageChooser extends JPanel implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private static final String[] IMAGE_EXTENSION = {"jpeg", "jpg", "JPG", "JPEG", "gif", "png"};
  private JLabel fImgLabel;
  private byte[] fImageData;

  private JPanel rightPane;
  private JButton btnBrowse;
  private JButton btnRemove;

  private byte[] fInitialImageData;
  private final List<ChangeListener> listeners = new ArrayList<>();

  public ImageChooser() {
    this(null);
  }

  public ImageChooser(byte[] imageData) {
    initData(imageData);
    setLayout(new BorderLayout(2, 2));
    initComponent();
  }

  private void initComponent() {
    fImgLabel = new JLabel();
    fImgLabel.setSize(WidgetConstant.AVATAR_SIZE, WidgetConstant.AVATAR_SIZE);
    fImgLabel.setBorder(BorderFactory.createLoweredBevelBorder());

    btnBrowse = new JButton(new BrowseAction());
    btnBrowse.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.FOLDER_ICON));
    btnBrowse.setName("fHyperLinkBrowseImg");

    btnRemove = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    btnRemove.setName("fBtnRemoveImg");
    btnRemove.addActionListener(new RemoveAction());

    rightPane = new JPanel(new VerticalLayout(2));
    rightPane.add(btnBrowse);

    rightPane.add(btnBrowse);
    if (ArrayUtils.isNotEmpty(fImageData)) {
      fImgLabel.setIcon(getIcon());
    } else {
      fImgLabel.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.NO_IMAGE_ICON));
      fImgLabel.setVisible(false);
      btnRemove.setVisible(false);
    }

    add(fImgLabel);
    rightPane.add(btnRemove);
    add(rightPane, BorderLayout.EAST);
  }

  private void initData(byte[] imageData) {
    byte[] imageDataClone = null;
    if (imageData != null) {
      imageDataClone = Arrays.copyOf(imageData, imageData.length);
    }
    this.fImageData = imageDataClone;
  }

  private BufferedImage readImage() {
    InputStream in = new ByteArrayInputStream(fImageData);
    try {
      return ImageIO.read(in);
    } catch (IOException e) {
      DetailsDialog.showDialog(SwingUtilities.getWindowAncestor(this), null, null, e);
    }
    return null;
  }

  private Icon getIcon() {
    BufferedImage image = readImage();
    ImageIcon imgIcon = new ImageIcon(image.getScaledInstance(WidgetConstant.AVATAR_SIZE,
            WidgetConstant.AVATAR_SIZE, Image.SCALE_SMOOTH));
    return imgIcon;
  }

  private Icon getIcon(File f) {
    try {
      fImageData = FileUtils.readFileToByteArray(f);
    } catch (IOException e) {
      DetailsDialog.showDialog(SwingUtilities.getWindowAncestor(this), null, null, e);
    }
    return getIcon();
  }

  public byte[] getImageData() {
    return fImageData;
  }

  @Override
  public void fireChangeEvent() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener cl : listeners) {
      cl.stateChanged(e);
    }
  }

  @Override
  public void setEnabled(boolean enabled) {
    btnBrowse.setEnabled(enabled);
    btnRemove.setEnabled(enabled);
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    listeners.add(listener);
  }

  @Override
  public boolean isDirty() {
    return !Arrays.equals(fImageData, fInitialImageData);
  }

  public byte[] getInitialContent() {
    return fInitialImageData;
  }

  public void setInitialContent(byte[] fInitialContent) {
    this.fInitialImageData = fInitialContent;
  }

  private class BrowseAction extends AbstractAction {
    private static final long serialVersionUID = 6808805995161914881L;

    @Override
    public void actionPerformed(ActionEvent evt) {
      FileFilter imgFileFilter = new FileNameExtensionFilter("Images", IMAGE_EXTENSION);
      Component c = (Component) evt.getSource();
      ExtendedFileChooser imageChooser = new ExtendedFileChooser();
      imageChooser.setFileFilter(imgFileFilter);
      if (imageChooser.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
        File f = imageChooser.getSelectedFile();
        fImgLabel.setVisible(true);
        btnRemove.setVisible(true);

        fImgLabel.setIcon(getIcon(f));
        fImgLabel.revalidate();
        fImgLabel.repaint();

        fireChangeEvent();
      }
    }
  }

  private class RemoveAction extends AbstractAction {
    private static final long serialVersionUID = -4681615548948629684L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent e) {
      int option = JOptionPane.showConfirmDialog(null, "Do you want to remove the image?", "Confirm",
              JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION) {
        fImageData = null;
        fImgLabel.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.NO_IMAGE_ICON));
        fImgLabel.setSize(WidgetConstant.AVATAR_SIZE, WidgetConstant.AVATAR_SIZE);
        fImgLabel.setVisible(false);
        btnRemove.setVisible(false);
        fireChangeEvent();
      }
    }
  }
}