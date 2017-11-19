/*
 * Copyright 2012-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.s3s.ssm.config;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.jdesktop.application.SingleFrameApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.s3s.ssm.view.LookAndFeelUtil;
import com.s3s.ssm.view.LookAndFeelUtil.LookAndFeel;
import com.s3s.ssm.view.ServerView;

@Configuration
@EnableAutoConfiguration
@Import({PosServiceConfig.class, ManageServiceConfig.class, ReportServiceConfig.class,
    DashboardServiceConfig.class, TimelineServiceConfig.class, ServiceCacheConfig.class})
@EnableJpaRepositories(basePackages = "com.s3s.ssm.repo")
public class ApplicationConfig extends SingleFrameApplication {
  public final static Image APP_IMAGE = Toolkit.getDefaultToolkit().getImage(
          ApplicationConfig.class.getResource("/server.png"));

  public static void main(String[] args) {
    launch(ApplicationConfig.class, args);
  }

  @Override
  protected void initialize(String[] args) {
    setLooknFeel();
  }

  private void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("TabbedPane.background", UIManager.get("Panel.background"));
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
    UIManager.put("Synthetica.dialog.icon.enabled", true);

  }

  @Override
  protected void startup() {
    show(new ServerView(this));
    getMainFrame().setTitle("Server");
    getMainFrame().setIconImage(APP_IMAGE);
    getMainFrame().toFront();
  }
}
