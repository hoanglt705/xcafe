package com.s3s.ssm.view.report.finalprocess;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.FinalPeriodSaleProcessDto;
import com.s3s.ssm.service.IReportService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class FinalPeriodProceedPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private ListFinalPeriodProductProcessView listFinalPeriodProductProcessView;
  private ListFinalPeriodFoodTableProcessView listFinalPeriodFoodTableProcessView;
  private FinalPeriodSaleProcessDto finalPeriodSaleProcessDto;
  private JLabel lblSale;
  private JList<Date> finalPeriodList;
  private JXDatePicker datePickerFrom;
  private JXDatePicker datePickerTo;
  private IReportService reportService = ReportContextProvider.getInstance().getReportService();
  private DecimalFormat decimalFormat = new DecimalFormat();

  public FinalPeriodProceedPanel() {
    setLayout(new BorderLayout());
    addSearchPanel();
    listFinalPeriodProductProcessView = new ListFinalPeriodProductProcessView();
    listFinalPeriodProductProcessView.setPreferredSize(new Dimension(100, 300));
    listFinalPeriodFoodTableProcessView = new ListFinalPeriodFoodTableProcessView();
    listFinalPeriodFoodTableProcessView.setPreferredSize(new Dimension(100, 300));

    JPanel resultPanel = new JPanel(new BorderLayout());
    lblSale = new JLabel();
    resultPanel.add(lblSale, BorderLayout.NORTH);
    JPanel tempPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    tempPanel.add(listFinalPeriodProductProcessView);
    tempPanel.add(listFinalPeriodFoodTableProcessView);
    resultPanel.add(tempPanel);
    add(resultPanel);

    finalPeriodList = new JList<>();
    finalPeriodList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent event) {
        Map<String, List<?>> finalPeriod = reportService.findProcessFinalPeriod(finalPeriodList
                .getSelectedValue());
        updateResult(finalPeriod);
      }
    });
    JScrollPane scrollPane = new JScrollPane(finalPeriodList);
    scrollPane.setSize(100, 30);
    add(new JScrollPane(finalPeriodList), BorderLayout.WEST);
  }

  private void addSearchPanel() {
    JPanel searchPanel = new JPanel(new BorderLayout());
    JPanel contentPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    searchPanel.add(buttonPanel, BorderLayout.SOUTH);
    searchPanel.add(contentPanel, BorderLayout.CENTER);

    JButton btnProceed = new JButton(
            ControlConfigUtils.getString("label.FinalPeriodProcess.searchPanel.proceed"));
    JLabel lblFromDate = new JLabel(
            ControlConfigUtils.getString("label.ProductIncomeDTO.searchPanel.fromDate"));
    JLabel lblToDate = new JLabel(
            ControlConfigUtils.getString("label.FinalPeriodProcess.searchPanel.toDate"));
    datePickerFrom = new JXDatePicker();
    datePickerTo = new JXDatePicker();
    JButton btnList = new JButton(ControlConfigUtils.getString("label.FinalPeriodProcess.searchPanel.list"));

    contentPanel.add(lblFromDate);
    contentPanel.add(datePickerFrom);
    contentPanel.add(lblToDate);
    contentPanel.add(datePickerTo);
    contentPanel.add(btnList);
    contentPanel.add(btnProceed);

    btnList.addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent arg0) {
        DefaultListModel<Date> listModel = new DefaultListModel<Date>();
        List<Date> finalPeriods = reportService.findAllProcessFinalPeriod(datePickerFrom.getDate(),
                datePickerTo.getDate());
        for (Date date : finalPeriods) {
          listModel.addElement(date);
        }
        finalPeriodList.setModel(listModel);
      }
    });

    btnProceed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        Map<String, List<?>> processFinalPeriod = reportService.processFinalPeriod();
        updateResult(processFinalPeriod);
      }
    });
    add(searchPanel, BorderLayout.NORTH);
  }

  private void updateResult(Map<String, List<?>> processFinalPeriod) {
    listFinalPeriodProductProcessView.setData(processFinalPeriod.get(IReportService.FINAL_MATERIAL_PERIOD));
    listFinalPeriodProductProcessView.refreshAndBackToFirstPage();

    listFinalPeriodFoodTableProcessView.setData(processFinalPeriod
            .get(IReportService.FINAL_FOOD_TABLE_PERIOD));
    listFinalPeriodFoodTableProcessView.refreshAndBackToFirstPage();

    finalPeriodSaleProcessDto = (FinalPeriodSaleProcessDto) processFinalPeriod
            .get(IReportService.PROCESS_FINAL_SALE_PERIOD).get(0);

    lblSale.setText("<html>" + ControlConfigUtils.getString("label.FinalPeriodSaleProcessDto.totalAmount")
            + ": " + decimalFormat.format(finalPeriodSaleProcessDto.getSaleTotal())
            + "<br>  " + ControlConfigUtils.getString("label.FinalPeriodSaleProcessDto.totalInvoice") + ": "
            + decimalFormat.format(finalPeriodSaleProcessDto.getInvoiceTotal()) + "<br></html>");
  }

}
