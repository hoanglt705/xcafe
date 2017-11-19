package com.s3s.ssm.pos.tableview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.pos.AbstractPosTest;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.view.controlpanel.TableView.TableViewStatus;

public class LeftIconTableViewTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testLeftIcon() {
    moveToOrderPanel();
    getButtonOpr("Tiger").clickMouse();
    moveToPosScreen();

    assertNotNull(getTableView().getLeftDecoration());
    JButton statusButton = (JButton) getTableView().getLeftDecoration();
    assertEquals(IziImageConstants.WAIT_ICON, ((ImageIcon) statusButton.getIcon()).getDescription());
    assertEquals(TableViewStatus.BOOKING, getTableView().getStatus());

    JButtonOperator statusButtonOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStatus"));
    statusButtonOpr.clickMouse();

    assertEquals(TableViewStatus.SERVING, getTableView().getStatus());
    assertEquals(IziImageConstants.ACTIVATE_ICON, ((ImageIcon) statusButton.getIcon()).getDescription());
  }
}
