package com.s3s.ssm.context;

import java.util.HashMap;
import java.util.Map;

public class ContextSession {
  private String username;
  private Map<String, Boolean> rights = new HashMap<String, Boolean>();
  private static ContextSession session;

  private ContextSession() {
  }

  public static ContextSession getInstance() {
    if (session == null)
    {
      session = new ContextSession();
    }

    return session;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Map<String, Boolean> getRights() {
    return rights;
  }

  public void setRights(Map<String, Boolean> rights) {
    this.rights = rights;
  }

}
