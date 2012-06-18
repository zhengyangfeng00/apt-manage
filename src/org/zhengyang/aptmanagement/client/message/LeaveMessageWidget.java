package org.zhengyang.aptmanagement.client.message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LeaveMessageWidget extends Composite {

  private static LeaveMessageWidgetUiBinder uiBinder = GWT.create(LeaveMessageWidgetUiBinder.class);

  interface LeaveMessageWidgetUiBinder extends UiBinder<Widget, LeaveMessageWidget> {
  }

  public LeaveMessageWidget() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
