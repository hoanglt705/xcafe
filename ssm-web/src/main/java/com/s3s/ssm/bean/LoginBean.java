package com.s3s.ssm.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.s3s.ssm.util.Util;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {
  private static final long serialVersionUID = 1L;
  private String password;
  private String message, username;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @SuppressWarnings("unused")
  public String loginProject() {
    // boolean hasAuthority = DashboardContextProvider.getInstance().getDashboardService()
    // .hasAuthority(username, password);
    boolean result = true;
    if (true) {
      HttpSession session = Util.getSession();
      session.setAttribute("username", getUsername());
      return "dashboard";
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
              "Invalid Login!", "Please Try Again!"));
      return "login";
    }
  }

  public String logout() {
    HttpSession session = Util.getSession();
    session.invalidate();
    return "login";
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
