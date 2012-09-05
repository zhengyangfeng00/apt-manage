package org.zhengyang.aptmanagement.client.aptmng;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JoinAptPanel extends Composite {

  private static JoinAptPanelUiBinder uiBinder = GWT.create(JoinAptPanelUiBinder.class);

  interface JoinAptPanelUiBinder extends UiBinder<Widget, JoinAptPanel> {
  }

  public JoinAptPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public JoinAptPanel(String firstName) {
    initWidget(uiBinder.createAndBindUi(this));
  }
}
