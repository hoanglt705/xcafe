package com.s3s.ssm.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class EntityDialog<T> extends JDialog {
  private static final long serialVersionUID = 1L;
  private static final int WIDTH_DIALOG = 400;
  private static final int HEIGHT_DIALOG = 300;
  private JList<T> lstEntity;
  private List<T> entityList;
  private int isPressedOK = 0;
  private BeanWrapper beanWrapper;

  // Renderer
  private TwoColCellRender<T> cellRenderer;

  public EntityDialog(Window parent, Dialog.ModalityType model, List<T> values) {
    super(parent, model);
    entityList = values;
    cellRenderer = new TwoColCellRender<T>();
    initialComponent();
  }

  private void initialComponent() {
    // create title
    JPanel titlePanel = new JPanel();
    JLabel lblTitle = new JLabel();
    lblTitle.setText("Select");
    titlePanel.add(lblTitle);

    // create entity list
    JPanel entityPanel = new JPanel();
    lstEntity = createJList(entityList, cellRenderer);
    entityPanel.add(lstEntity);
    JScrollPane scrollPane = new JScrollPane(entityPanel);

    // create button
    JPanel btnPanel = new JPanel();
    JButton btnOK = new JButton("OK");
    JButton btnCancel = new JButton("Cancel");
    btnPanel.add(btnOK);
    btnPanel.add(btnCancel);

    btnOK.addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        btnOKActionPerformed();

      }
    });

    btnCancel.addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        btnCancelActionPerformed();

      }
    });

    getContentPane().add(titlePanel, BorderLayout.PAGE_START);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(btnPanel, BorderLayout.PAGE_END);

    setSize(WIDTH_DIALOG, HEIGHT_DIALOG);

  }

  private JList<T> createJList(List<T> data, TwoColCellRender<T> renderer) {
    DefaultListModel<T> listModel = new DefaultListModel<>();
    for (T d : data) {
      listModel.addElement(d);
    }
    JList<T> jList = new JList<T>(listModel);
    jList.setCellRenderer(renderer);
    jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    // close the dialog when double click to it.
    jList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          btnOKActionPerformed();
        }
      }
    });

    return jList;
  }

  public T getSelectedEntity() {
    DefaultListModel<T> sourceModel = (DefaultListModel<T>) lstEntity.getModel();
    int selectedIndex = lstEntity.getSelectedIndex();
    return sourceModel.get(selectedIndex);
  }

  private void btnOKActionPerformed() {
    if (lstEntity.isSelectionEmpty()) {
      JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), "Please select a row", "Warning",
              JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
    } else {
      isPressedOK = 1;
      dispose();
    }
  }

  private void btnCancelActionPerformed() {
    dispose();
  }

  public int isPressedOK() {
    return isPressedOK;
  }

  @SuppressWarnings({"unused", "hiding"})
  private class TwoColCellRender<T> extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
      Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (value instanceof Icon) {
        setIcon((Icon) value);
        this.setText("");
      } else {
        if (value != null) {
          setIcon(null);
          beanWrapper = new BeanWrapperImpl(value);
          Long id = (Long) beanWrapper.getPropertyValue("id");
          String name = value.toString();
          setText("   " + StringUtils.rightPad(id.toString(), 40) + StringUtils.rightPad(name, 50));
        } else {
          setText("");
        }
      }
      return component;
    }

  }
}
