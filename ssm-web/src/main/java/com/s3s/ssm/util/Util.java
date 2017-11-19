package com.s3s.ssm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
  public static HttpSession getSession() {
    return (HttpSession) FacesContext.
            getCurrentInstance().
            getExternalContext().
            getSession(false);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.
            getCurrentInstance().
            getExternalContext().getRequest();
  }

  public static String getUserName()
  {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
            .getSession(false);
    return session.getAttribute("username").toString();
  }

  public static String getUserId()
  {
    HttpSession session = getSession();
    if (session != null)
      return (String) session.getAttribute("userid");
    else
      return null;
  }

  public static String getHubUrl() throws IOException {
    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
            .getContext();
    Properties properties = new Properties();
    InputStream inStream = servletContext.getResourceAsStream("/WEB-INF/config.properties");
    properties.load(inStream);
    return "http://localhost:" + properties.getProperty("hubPort");
  }
}
