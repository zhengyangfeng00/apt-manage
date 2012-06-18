//
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// //////////////////////////////////////////////////////////////////////////////

package org.zhengyang.aptmanagement.client.shared.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <b>Note</b>
 * This widget depends on bootstrap.js bootstrap-modal.js jquery.js
 * 
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 15, 2012
 */
public class NotificationDialog extends Composite {

  private static NotificationDialogUiBinder uiBinder = GWT.create(NotificationDialogUiBinder.class);

  interface NotificationDialogUiBinder extends UiBinder<Widget, NotificationDialog> {
  }
  
  public static final String id = "notificationDialog";

  @UiField Label title;
  @UiField Label message;
  
  public NotificationDialog() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public NotificationDialog(String title, String message) {
    initWidget(uiBinder.createAndBindUi(this));
    this.title.setText(title);
    this.message.setText(message);
  }
  
  public static NotificationDialog setUpNotificationDialog() {
    NotificationDialog dialog = GWT.create(NotificationDialog.class);
    initNotificationDialog(dialog);
    return dialog;
  }
  
  private static void initNotificationDialog(NotificationDialog notificationDialog) {
    RootPanel.get().add(notificationDialog);
    hideDialog(NotificationDialog.id);
  }
  
  private static native void showDialog(String dialogId) /*-{
    $wnd.$('#' + dialogId).modal('show');
  }-*/;

  private static native void hideDialog(String dialogId) /*-{
    $wnd.$('#' + dialogId).modal('hide');
  }-*/;

  public void setTitle(String title) {
    this.title.setText(title);
  }
  
  public void setMessage(String message) {
    this.message.setText(message);
  }
  
  public void showNotification(String title, String message) {
    setTitle(title);
    setMessage(message);
    showDialog(NotificationDialog.id);
  }
}
