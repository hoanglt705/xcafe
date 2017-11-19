package com.s3s.ssm.pos.tableview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.pos.AbstractPosTest;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.view.controlpanel.TableView;

public class TableViewTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testDetailTable() {
    JTabbedPaneOperator areaTabbedPaneOpr1 = new JTabbedPaneOperator(mainFrameOperator);
    JPanel panel1 = (JPanel) areaTabbedPaneOpr1.getComponentAt(0);
    TableView tableView1 = (TableView) panel1.getComponent(2);
    JButtonOperator btnShoppingCartOpr1 = new JButtonOperator(new ContainerOperator(tableView1),
            new NameComponentChooser("btnShoppingCart"));
    JButtonOperator btnStopOpr1 = new JButtonOperator(new ContainerOperator(tableView1),
            new NameComponentChooser("btnStop"));
    if (btnStopOpr1.isEnabled()) {
      btnStopOpr1.clickMouse();
    }
    JButtonOperator btnDeleteOpr1 = new JButtonOperator(new ContainerOperator(tableView1),
            new NameComponentChooser("btnDelete"));
    assertNull(tableView1.getLeftDecoration());
    assertTrue(btnShoppingCartOpr1.isEnabled());
    assertFalse(btnStopOpr1.isEnabled());
    assertFalse(btnDeleteOpr1.isEnabled());

    JButtonOperator btnShoppingCartOpr3 = new JButtonOperator(new ContainerOperator(tableView1),
            new NameComponentChooser("btnShoppingCart"));
    btnShoppingCartOpr3.clickMouse();

    getButtonOpr("Tiger").clickMouse();
    getButtonOpr("Heineken").clickMouse();

    getButtonOpr("btnBack").clickMouse();

    JTabbedPaneOperator areaTabbedPaneOpr = new JTabbedPaneOperator(mainFrameOperator);
    JPanel panel = (JPanel) areaTabbedPaneOpr.getComponentAt(0);
    TableView tableView = (TableView) panel.getComponent(2);
    JTableOperator detailTableOpr = new JTableOperator(new ContainerOperator(tableView));

    assertEquals(2, detailTableOpr.getRowCount());

    assertEquals(1, (int) detailTableOpr.getValueAt(0, 0));
    assertEquals("Tiger", detailTableOpr.getValueAt(0, 1));
    assertEquals(1, (int) detailTableOpr.getValueAt(0, 2));
    assertEquals(17000, (long) detailTableOpr.getValueAt(0, 3));

    assertEquals(2, (int) detailTableOpr.getValueAt(1, 0));
    assertEquals("Heineken", detailTableOpr.getValueAt(1, 1));
    assertEquals(1, (int) detailTableOpr.getValueAt(1, 2));
    assertEquals(21000, (long) detailTableOpr.getValueAt(1, 3));

    assertEquals("38.000", StringUtils.split(getLabelOpr("lblTitleTotalAmount").getText())[2]);

    JButtonOperator btnShoppingCartOpr = new JButtonOperator(new ContainerOperator(tableView),
            new NameComponentChooser("btnShoppingCart"));
    JButtonOperator btnStopOpr = new JButtonOperator(new ContainerOperator(tableView),
            new NameComponentChooser("btnStop"));
    JButtonOperator btnDeleteOpr = new JButtonOperator(new ContainerOperator(tableView),
            new NameComponentChooser("btnDelete"));
    assertTrue(btnShoppingCartOpr.isEnabled());
    assertTrue(btnStopOpr.isEnabled());
    assertFalse(btnDeleteOpr.isEnabled());

    assertNotNull(tableView.getLeftDecoration());
    assertEquals(IziImageConstants.WAIT_ICON,
            ((ImageIcon) ((JButton) tableView.getLeftDecoration()).getIcon()).getDescription());
  }
}
