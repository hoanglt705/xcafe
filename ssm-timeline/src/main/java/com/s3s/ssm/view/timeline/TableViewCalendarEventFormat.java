package com.s3s.ssm.view.timeline;

import de.costache.calendar.format.CalendarEventFormat;
import de.costache.calendar.model.CalendarEvent;

public class TableViewCalendarEventFormat implements CalendarEventFormat {

  @Override
  public String format(CalendarEvent calendarEvent) {
    StringBuilder formated = new StringBuilder();
    formated.append("<html>");
    formated.append("<table>");

    formated.append("<tr>");
    formated.append("<td>").append(calendarEvent.getSummary()).append("</td>");
    formated.append("</tr>");

    if (calendarEvent.getDescription() != null) {
      formated.append("<tr>");
      formated.append("<td>").append(calendarEvent.getDescription()).append("</td>");
      formated.append("</tr>");
    }

    formated.append("</table>");
    formated.append("</html>");
    return formated.toString();
  }

}
