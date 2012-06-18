package org.zhengyang.aptmanagement.client.shared.widgets;
/**
 * <b>Note</b>
 * This widget depends on bootstrap.js bootstrap-alert.js jquery.js
 */
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Alert extends Composite {

  private static AlertUiBinder uiBinder = GWT.create(AlertUiBinder.class);
  @UiField Label text;
  interface AlertUiBinder extends UiBinder<Widget, Alert> {
  }
  private static final String ID = "alt";
  
  public Alert() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void showAlert(String text) {
    this.text.setText(text);
    show(ID);
  }
  
  public void hide() {
    hide(ID);
  }
  
  private native void show(String id) /*-{
    $wnd.$('#' + id).alert();
  }-*/;
  
  private native void hide(String id) /*-{
    $wnd.$('#' + id).alert('close');
  }-*/;
}
