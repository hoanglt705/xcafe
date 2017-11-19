package com.s3s.ssm.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ManagedBean
public class LoginView implements Serializable {
  private static final long serialVersionUID = -7694507921658018675L;

  public void doLogin() throws ServletException, IOException {
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
            .getRequestDispatcher("/j_spring_security_check");
    dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
    FacesContext.getCurrentInstance().responseComplete();
  }
}
