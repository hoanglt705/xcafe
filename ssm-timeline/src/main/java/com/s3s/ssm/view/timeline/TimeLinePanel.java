package com.s3s.ssm.view.timeline;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import org.apache.commons.lang.time.DateUtils;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXMonthView;

import com.s3s.ssm.config.TimelineContextProvider;
import com.s3s.ssm.dto.CompanyDto;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.ITimelineService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.costache.calendar.Config;
import de.costache.calendar.JCalendar;
import de.costache.calendar.model.CalendarEvent;
import de.costache.calendar.model.EventType;
import de.costache.calendar.util.CalendarUtil;

public class TimeLinePanel extends JSplitPane {
  private static final long serialVersionUID = 1L;
  private static final int DELAY = 1 * 60 * 1000;

  private final Color LIGHT_YELLOW = new Color(255, 255, 224);
  private final Color VIOLET = new Color(233, 207, 236);

  private final JCalendar fCalendar;
  private final Timer fRefreshTimer;

  private final ICompanyService fCompanyService = TimelineContextProvider.getInstance().getCompanyService();
  private final ITimelineService timelineService = TimelineContextProvider.getInstance().getTimelineService();

  private final ResourceMap fResourceMap;

  public TimeLinePanel(ApplicationContext appContext) {
    fResourceMap = appContext.getResourceManager().getResourceMap(TimeLinePanel.class);

    CompanyDto companyDto = fCompanyService.getCompany();
    Config config = generateConfig(companyDto.getOpenTime(), companyDto.getCloseTime());

    fCalendar = new JCalendar(config);
    fCalendar.setTooltipFormater(new TableViewCalendarEventFormat());
    fCalendar.setSelectedDay(new Date());

    addEvent(CalendarUtil.stripTime(new Date()), new Date());

    addMonthView();
    setRightComponent(fCalendar);
    fRefreshTimer = new Timer(DELAY, new RefreshAction());
    fRefreshTimer.start();
  }

  public void stop() {
    fRefreshTimer.stop();
  }

  public void start() {
    fRefreshTimer.start();
  }

  private void addMonthView() {
    JPanel calendarPanel = new JPanel(new TableLayout(new double[][] { {-1}, {-1, -1, -1, -1}}));
    calendarPanel.add(initMonthView(), "0, 0");
    setLeftComponent(calendarPanel);
  }

  private Config generateConfig(Date openTime, Date closeTime) {
    Calendar calendar = Calendar.getInstance();

    Config calendarConfig = new Config();
    calendar.setTime(openTime);
    calendarConfig.setWorkingHoursStart(calendar.get(Calendar.HOUR_OF_DAY));
    calendar.setTime(closeTime);
    calendarConfig.setWorkingHoursEnd(calendar.get(Calendar.HOUR_OF_DAY));
    return calendarConfig;
  }

  private JXMonthView initMonthView() {
    final JXMonthView monthView = new JXMonthView();
    monthView.setTraversable(true);
    monthView.setShowingLeadingDays(true);
    monthView.setShowingTrailingDays(true);
    monthView.setShowingWeekNumber(true);
    monthView.setDayForeground(Calendar.SATURDAY, new Color(0xE04020));
    monthView.setDayForeground(Calendar.SUNDAY, new Color(0xE04020));

    monthView.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Date selectionDate = monthView.getSelectionDate();
        fCalendar.removeCalendarEvent(selectionDate);

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(selectionDate);
        startDate.clear(Calendar.HOUR_OF_DAY);
        startDate.clear(Calendar.MINUTE);
        startDate.clear(Calendar.SECOND);

        Calendar closeDate = Calendar.getInstance();
        closeDate.setTime(selectionDate);
        closeDate.set(Calendar.HOUR_OF_DAY, 24);
        closeDate.set(Calendar.MINUTE, 59);
        closeDate.set(Calendar.SECOND, 59);

        addEvent(startDate.getTime(), closeDate.getTime());

        fCalendar.setSelectedDay(selectionDate);
      }
    });
    return monthView;
  }

  private void addEvent(Date fromDate, Date toDate) {
    List<InvoiceDto> invoiceDtos = TimelineContextProvider.getInstance().getTimelineService()
            .getInvoice(fromDate, toDate);
    for (InvoiceDto invoiceDto : invoiceDtos) {
      CalendarEvent event = new CalendarEvent();
      event.setStart(invoiceDto.getCreatedDate());
      if (!isEnded(invoiceDto)) {
        event.setEnd(DateUtils.addMinutes(invoiceDto.getCreatedDate(), 30));
      } else {
        event.setEnd(invoiceDto.getEndedDate());
      }
      event.setSummary(invoiceDto.getFoodTable().getName());
      event.setDescription(generateDescription(invoiceDto));
      event.setType(specifyEventType(invoiceDto.getInvoiceStatus()));
      fCalendar.addCalendarEvent(event);
    }
  }

  private EventType specifyEventType(InvoiceStatus status) {
    EventType eventType = new EventType();
    switch (status) {
      case SERVING:
        eventType.setBackgroundColor(Color.ORANGE);
        break;
      case PAID:
        eventType.setBackgroundColor(VIOLET);
        break;
      case BOOKING:
        eventType.setBackgroundColor(LIGHT_YELLOW);
        break;
      case DELETE:
        eventType.setBackgroundColor(Color.RED);
        break;
      case CANCEL:
        eventType.setBackgroundColor(Color.LIGHT_GRAY);
        break;

      default:
        break;
    }
    return eventType;
  }

  private boolean isEnded(InvoiceDto invoice) {
    return invoice.getEndedDate() != null;
  }

  private String generateDescription(InvoiceDto invoice) {
    String description = "<html><hr/>";
    description += "<table>";
    description += "<thead>";
    description += "<tr>";

    description += "<td align='center'>" +
            "STT" +
            "</td><td align='left'>" +
            ControlConfigUtils.getString("label.InvoiceDetail.product") +
            "</td><td align='right'>" +
            ControlConfigUtils.getString("label.InvoiceDetail.amount") +
            "</td>";
    description += "</tr>";
    description += "</thead>";
    description += "<tbody>";
    List<InvoiceDetailDto> details = invoice.getInvoiceDetails();
    for (int i = 0; i < details.size(); i++) {
      description += "<tr>";
      description += "<td>" + (i + 1) + "</td><td>"
              + details.get(i).getProduct().getName() + "</td><td>"
              + details.get(i).getAmount() + "</td>";
      description += "</tr>";
    }
    description += "</tbody>";
    description += "</table>";
    description += "</html>";
    return description;
  }

  public List<InvoiceDto> getInvoiceForToday() {
    Calendar fromCalendar = Calendar.getInstance();
    fromCalendar.clear(Calendar.HOUR_OF_DAY);
    fromCalendar.clear(Calendar.MINUTE);
    return timelineService.getInvoice(fromCalendar.getTime(), new Date());
  }

  private class RefreshAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      fCalendar.removeCalendarEvent(new Date());
      addEvent(CalendarUtil.stripTime(new Date()), new Date());
    }
  }
}
