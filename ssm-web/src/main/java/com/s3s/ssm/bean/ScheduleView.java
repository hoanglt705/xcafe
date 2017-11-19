package com.s3s.ssm.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.s3s.ssm.config.TimelineContextProvider;
import com.s3s.ssm.dto.InvoiceDto;

@ManagedBean
@ViewScoped
public class ScheduleView implements Serializable {
  private static final long serialVersionUID = 1L;

  private ScheduleModel eventModel;

  private ScheduleEvent event = new DefaultScheduleEvent();

  @PostConstruct
  public void init() {
    eventModel = new DefaultScheduleModel();
    List<InvoiceDto> invoiceDtos = TimelineContextProvider.getInstance().getTimelineService()
            .getInvoice(stripTime(new Date()), new Date());
    for (InvoiceDto invoiceDto : invoiceDtos) {
      String time = null;
      Date createdDate = invoiceDto.getCreatedDate();
      Date endedDate = invoiceDto.getEndedDate();
      if (endedDate != null) {
        time = getTime(createdDate) + "-" + getTime(endedDate);
      } else {
        time = getTime(createdDate) + "-??";
      }
      String eventLabel = time + "[" + invoiceDto.getFoodTable().getName() + "]";

      if (endedDate != null) {
        eventModel.addEvent(new DefaultScheduleEvent(eventLabel, createdDate, endedDate));
      } else {
        eventModel.addEvent(new DefaultScheduleEvent(eventLabel, createdDate, DateUtils.addMinutes(
                createdDate, 30)));
      }
    }
  }

  public Date getRandomDate(Date base) {
    Calendar date = Calendar.getInstance();
    date.setTime(base);
    date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random day of month

    return date.getTime();
  }

  public Date getInitialDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

    return calendar.getTime();
  }

  public ScheduleModel getEventModel() {
    return eventModel;
  }

  public void onEventSelect(SelectEvent selectEvent) {
    event = (ScheduleEvent) selectEvent.getObject();
  }

  public void onDateSelect(SelectEvent selectEvent) {
    event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
  }

  private String getTime(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    return hour + ":" + minute;
  }

  private Date stripTime(final Date date) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
}
