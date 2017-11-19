package com.s3s.ssm.view.registry;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.AbstractView.EditActionEnum;
import com.s3s.ssm.widget.HeaderDialog;

public class RegistryDialog extends HeaderDialog {
  private static final long serialVersionUID = 1L;

  public RegistryDialog() {
    super();
    String title = ControlConfigUtils.getString("label.menu.register");
    title = title.replace("...", "");
    setTitle(title);
    setHeaderTitle(title);
    setSize(410, 260);
    setHeaderIcon(IziImageUtils.getMediumIcon("/images/registry.png"));

    EditRegistryView view = new EditRegistryView(new HashMap<>(getRequest()));
    view.getActionMap().put(HeaderDialog.EXECUTE_ACTION_COMMAND, new AbstractAction() {
      private static final long serialVersionUID = 1L;

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        view.bindValue();
        RegistryDialog.this.setVisible(false);
      }
    });
    setContentPane(view);
  }

  public static Map<String, Object> getRequest() {
    Map<String, Object> request = new HashMap<>();
    request.put(AbstractView.PARAM_ACTION, EditActionEnum.EDIT);
    request.put(AbstractView.PARAM_ENTITY_ID, 1l);
    return request;
  }
}
