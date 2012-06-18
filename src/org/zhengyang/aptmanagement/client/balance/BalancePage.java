package org.zhengyang.aptmanagement.client.balance;

import org.zhengyang.aptmanagement.client.navigate.NavigateWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BalancePage extends Composite implements EntryPoint {
  private final NavigateWidget navigateWidget = GWT.create(NavigateWidget.class);
  
  @Override
  public void onModuleLoad() {
    RootPanel.get().add(navigateWidget);
    RootPanel.get("balance").add(new BalanceWidget());
  }
  
}
