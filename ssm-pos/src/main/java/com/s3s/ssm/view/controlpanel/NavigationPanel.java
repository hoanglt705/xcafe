package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.s3s.ssm.view.controlpanel.LookAndFeelUtil.LookAndFeel;

public class NavigationPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private JPanel contentPanel;
  private List<JComponent> fComponents;
  private int fColumn;
  private int fRow;
  private int fCurrentPage;
  private int pageTotal;
  private JButton btnBack;
  private JButton btnNext;

  public NavigationPanel(List<JComponent> components, int column, int row) {
    fComponents = components;
    fColumn = column;
    fRow = row;
    fCurrentPage = 0;
    setLayout(new BorderLayout());
    calculatePageTotal(components, column, row);
    layoutComponent(fCurrentPage);
    add(contentPanel, BorderLayout.CENTER);
    if (pageTotal > 1) {
      addPaginationButtons();
    }
  }

  private void addPaginationButtons() {
    ImageIcon backIcon = new ImageIcon(NavigationPanel.class.getResource("/images/back.png"));
    ImageIcon nextIcon = new ImageIcon(NavigationPanel.class.getResource("/images/next.png"));
    btnBack = new JButton(backIcon);
    btnBack.setContentAreaFilled(false);
    btnBack.setPreferredSize(new Dimension(24, 10));
    btnBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (fCurrentPage > 0) {
          fCurrentPage--;
          layoutComponent(fCurrentPage);
          updateNavigateButton();
        }
      }
    });
    btnNext = new JButton(nextIcon);
    btnNext.setContentAreaFilled(false);
    btnNext.setPreferredSize(new Dimension(24, 10));
    btnNext.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (fCurrentPage < pageTotal - 1) {
          fCurrentPage++;
          layoutComponent(fCurrentPage);
          updateNavigateButton();
        }
      }
    });
    add(btnBack, BorderLayout.WEST);
    add(btnNext, BorderLayout.EAST);
    updateNavigateButton();
  }

  private void calculatePageTotal(List<JComponent> components, int column, int row) {
    pageTotal = components.size() / (row * column);
    if (components.size() % (row * column) > 0) {
      pageTotal++;
    }
  }

  private void updateNavigateButton() {
    if (fCurrentPage == 0) {
      btnBack.setVisible(false);
    } else {
      btnBack.setVisible(true);
    }
    if (fCurrentPage == pageTotal - 1) {
      btnNext.setVisible(false);
    } else {
      btnNext.setVisible(true);
    }
  }

  private void layoutComponent(int page) {
    int startIndex = page * fColumn * fRow;
    int lastIndex = startIndex + (fColumn * fRow) > fComponents.size() ? fComponents.size() : startIndex
            + (fColumn * fRow);
    List<JComponent> showedComponents = fComponents.subList(startIndex, lastIndex);
    if (contentPanel != null) {
      contentPanel.removeAll();
    } else {
      contentPanel = new JPanel(new GridLayout(fRow, fColumn));
    }
    int i = 0;
    for (JComponent component : showedComponents) {
      contentPanel.add(component);
      i++;
    }
    while (i < fColumn * fRow) {
      contentPanel.add(new Box.Filler(new Dimension(10, 10), new Dimension(10, 10), new Dimension(10, 10)));
      i++;
    }
    contentPanel.revalidate();
    contentPanel.repaint();
  }

  public static void main(String[] args) {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    JFrame frame = new JFrame();
    frame.setSize(new Dimension(300, 300));
    List<JComponent> components = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      components.add(new JButton("Hello" + i));
    }
    NavigationPanel navPanel = new NavigationPanel(components, 4, 2);
    frame.getContentPane().add(navPanel);
    frame.setVisible(true);
  }
}
