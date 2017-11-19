package com.s3s.ssm.view.order;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;

import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.cashier.event.invoice.AddQuantityEvent;
import com.s3s.ssm.view.cashier.event.invoice.DeleteInvoiceDetailEvent;
import com.s3s.ssm.view.cashier.event.invoice.SubQuantityEvent;
import com.s3s.ssm.view.controlpanel.DetailTableView;

public class InvoiceDetailActionBar extends JPanel implements ListSelectionListener {
  private static final long serialVersionUID = 1L;
  private DetailTableView fInvoiceDetailTable;
  private JButton btnAddQuantity;
  private JButton btnSubQuantity;
  private JButton btnMoveDown;
  private JButton btnMoveUp;
  private List<ProductDto> fProductDtos;
  private TransactionHandler fTransactionHandler;
  private JButton btnDelete;

  public InvoiceDetailActionBar(TransactionHandler transactionHandler, List<ProductDto> productDtos) {
    this.fTransactionHandler = transactionHandler;
    fProductDtos = productDtos;
    AnnotationProcessor.process(this);
    GridLayout layout = new GridLayout(1, 5);
    layout.setHgap(3);
    setLayout(layout);

    btnMoveUp = new JButton("");
    btnMoveUp.setName("btnMoveUp");
    btnMoveUp.setIcon(IziImageUtils.getMediumIcon("/images/UpIcon.png"));
    btnMoveUp.setEnabled(existDetail());
    btnMoveUp.addActionListener(e -> moveUp());

    btnMoveDown = new JButton("");
    btnMoveDown.setName("btnMoveDown");
    btnMoveDown.setIcon(IziImageUtils.getMediumIcon("/images/DownIcon.png"));
    btnMoveDown.setEnabled(existDetail());
    btnMoveDown.addActionListener(e -> moveDown());

    btnAddQuantity = new JButton("");
    btnAddQuantity.setName("btnAddQuantity");
    btnAddQuantity.setIcon(IziImageUtils.getMediumIcon("/images/addIcon.png"));
    btnAddQuantity.setPreferredSize(new Dimension(60, 60));
    btnAddQuantity.setEnabled(existDetail());
    btnAddQuantity.addActionListener(e -> addQuantity());

    btnSubQuantity = new JButton("");
    btnSubQuantity.setName("btnSubQuantity");
    btnSubQuantity.setIcon(IziImageUtils.getMediumIcon("/images/subtractIcon.png"));
    btnSubQuantity.setEnabled(existDetail());
    btnSubQuantity.addActionListener(e -> subQuantity());

    btnDelete = new JButton();
    btnDelete.setName("btnDeleteDetail");
    btnDelete.setIcon(IziImageUtils.getMediumIcon("/images/deleteIcon.png"));
    btnDelete.setEnabled(existDetail());
    btnDelete.addActionListener(e -> deleteDetail());

    add(btnMoveUp);
    add(btnMoveDown);
    add(btnAddQuantity);
    add(btnSubQuantity);
    add(btnDelete);
  }

  private double getQuantityInStock(String productCode) {
    Optional<ProductDto> productDto = fProductDtos.stream().filter(dto -> dto.getCode().equals(productCode))
            .findFirst();
    if (productDto.isPresent()) {
      return productDto.get().getQuantityInStock();
    }
    return -1;
  }

  private void deleteDetail() {
    if (fInvoiceDetailTable.isSelected()) {
      InvoiceDetailDto selectedDetailDto = fInvoiceDetailTable.getSelectedInvoiceDetailDto();
      if (selectedDetailDto.getQuantity() > 0) {
        EventBus.publish(new DeleteInvoiceDetailEvent(selectedDetailDto));
      }
    }
  }

  private void subQuantity() {
    if (fInvoiceDetailTable.isSelected()) {
      InvoiceDetailDto selectedDetailDto = fInvoiceDetailTable.getSelectedInvoiceDetailDto();
      if (selectedDetailDto.getQuantity() > 1) {
        EventBus.publish(new SubQuantityEvent(fInvoiceDetailTable.getSelectedInvoiceDetailDto()));
      }
    }
  }

  private void addQuantity() {
    if (fInvoiceDetailTable.isSelected()) {
      InvoiceDetailDto selectedDetailDto = fInvoiceDetailTable.getSelectedInvoiceDetailDto();
      double quantityInTransaction = fTransactionHandler.getQuantity(selectedDetailDto.getProduct());
      double quantityInStock = getQuantityInStock(selectedDetailDto.getProduct().getCode());
      boolean enoughForSell = quantityInTransaction < quantityInStock
              || selectedDetailDto.getProduct().isFood();
      if (enoughForSell) {
        EventBus.publish(new AddQuantityEvent(selectedDetailDto));
      }
    }

  }

  private void moveUp() {
    if (fInvoiceDetailTable.isSelected()) {
      if (fInvoiceDetailTable.isAtFirstRow()) {
        fInvoiceDetailTable.moveToLastRow();
      } else {
        fInvoiceDetailTable.moveUp();
      }
    } else {
      fInvoiceDetailTable.moveToFirstRow();
    }
  }

  private boolean existDetail() {
    if (fInvoiceDetailTable == null) {
      return false;
    }
    return fInvoiceDetailTable.getRowCount() > 0;
  }

  private void moveDown() {
    if (fInvoiceDetailTable.isSelected()) {
      if (fInvoiceDetailTable.isLastRow()) {
        fInvoiceDetailTable.moveToFirstRow();
      } else {
        fInvoiceDetailTable.moveDown();
      }
    } else {
      fInvoiceDetailTable.moveToFirstRow();
    }
  }

  public void setDetailTable(DetailTableView invoiceDetailTable) {
    fInvoiceDetailTable = invoiceDetailTable;
    fInvoiceDetailTable.getSelectionModel().addListSelectionListener(this);
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (fInvoiceDetailTable.getSelectedRow() > -1) {
      btnMoveUp.setEnabled(true);
      btnMoveDown.setEnabled(true);
      btnAddQuantity.setEnabled(true);
      btnSubQuantity.setEnabled(true);
      btnDelete.setEnabled(true);
    } else {
      btnMoveUp.setEnabled(false);
      btnMoveDown.setEnabled(false);
      btnAddQuantity.setEnabled(false);
      btnSubQuantity.setEnabled(false);
      btnDelete.setEnabled(false);
    }
  }
}
