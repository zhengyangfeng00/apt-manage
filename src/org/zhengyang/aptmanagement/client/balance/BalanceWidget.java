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

package org.zhengyang.aptmanagement.client.balance;

import java.util.ArrayList;
import java.util.Date;

import org.zhengyang.aptmanagement.client.shared.SessionService;
import org.zhengyang.aptmanagement.client.shared.SessionServiceAsync;
import org.zhengyang.aptmanagement.client.shared.widgets.Alert;
import org.zhengyang.aptmanagement.client.shared.widgets.DatePicker;
import org.zhengyang.aptmanagement.client.shared.widgets.NotificationDialog;
import org.zhengyang.aptmanagement.client.utils.Formatter;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
public class BalanceWidget extends Composite {

  private static BalanceWidgetUiBinder uiBinder = GWT.create(BalanceWidgetUiBinder.class);
  private final BalanceServiceAsync balanceService = GWT.create(BalanceService.class);
  private final SessionServiceAsync sessionService = GWT.create(SessionService.class);
  @UiField FlexTable balanceTable;
  @UiField FlexTable consumptionTable;
  @UiField TextBox amountInput;
  @UiField ListBox categorySelect;
  @UiField ListBox paidByInput;
  @UiField Button addConsumptionButton;
  @UiField TextBox consumptionNameInput;
  @UiField TextArea noteTextArea;
  @UiField TextBox newCategoryInput;
  @UiField Button addNewCategoryButton;
  @UiField HTMLPanel sharedByContainer;
  @UiField HTMLPanel alertContainer;
  @UiField FlowPanel balanceToolbar;
  @UiField Button refreshBalanceButton;
  @UiField HTMLPanel consumptionToolbar;
  DatePicker fromDate;
//  DatePicker toDate = new DatePicker("toDatePicker");
  private final static NotificationDialog notificationDialog = NotificationDialog
      .setUpNotificationDialog();
  private Alert alert = GWT.create(Alert.class);
  private String username;
  private String apartmentName;
  private Role role;
  private ArrayList<HorizontalCheckBox> sharedByCheckBoxList = Lists.newArrayList();
  interface BalanceWidgetUiBinder extends UiBinder<Widget, BalanceWidget> {
  }
  
  // interface for the style of this widget
  interface BalanceWidgetStyle extends CssResource {
    String tableHeader();
    String table();
  }

  public BalanceWidget() {
    initWidget(uiBinder.createAndBindUi(this));    
    addHeaderToBalanceTable();
    addHeaderToConsumptionTable();
    prepareBalanceToolBar();
    prepareConsumptionToolBar();
    initializeData();
    fetchConsumptionTypes();
  }
  
  private void prepareConsumptionToolBar() {
//    fromDate = GWT.create(DatePicker.class);
//    consumptionToolbar.add(fromDate);
  }
  
  private void prepareBalanceToolBar() {
    refreshBalanceButton.setHTML("<i class=\"icon-refresh\"></i>Refresh");
    refreshBalanceButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        fetchUsersOfApartment(apartmentName);
      }
      
    });
  }

  private void initializeData() {
    sessionService.getCurrentUser(new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);
      }
      @Override
      public void onSuccess(String username) {
        if (username == null) { // session time out
          notificationDialog.showNotification("Infomation", "Session is time out, will redirect to index page in 5 seconds.");
          redirectToIndexPage(5000);
          return;
        }
        BalanceWidget.this.username = username;
        // fetch users in this apartment
        fetchCurrentUser(username);       
      }      
    });
  }
 
  private void fetchCurrentUser(String username) {
    balanceService.getUser(username, new AsyncCallback<User>() {
      @Override
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);
      }
      @Override
      public void onSuccess(User user) {
        if (user.apartmentName == null || user.apartmentName.equals("")) {
          // the user doesn't associate with any apartment, go to the index page
          notificationDialog.showNotification("Infomation", "You didn't associate with any apartments, will redirect to index page in 5 seconds.");
          redirectToIndexPage(5000);
        } else {
          BalanceWidget.this.role = user.role;
          BalanceWidget.this.apartmentName = user.apartmentName;
          fetchConsumptionsOfApartment(apartmentName);
          fetchUsersOfApartment(apartmentName);
        }            
      }      
    });
  }
  
  private void redirectToIndexPage(int millisecondLater) {
    Timer t = new Timer() {
      @Override
      public void run() {
        Window.Location.assign("/index.html");
      }
    };
    t.schedule(millisecondLater);
  }  
  
  private void addHeaderToConsumptionTable() {
    Element thead = DOM.createTHead();
    Element tr = DOM.createTR();
    Element th1 = DOM.createTH();
    DOM.setInnerText(th1, "Name");
    Element th2 = DOM.createTH();
    DOM.setInnerText(th2, "Amount");
    Element th3 = DOM.createTH();
    DOM.setInnerText(th3, "Paid by");
    Element th4 = DOM.createTH();
    DOM.setInnerText(th4, "Type");
    Element th5 = DOM.createTH();
    DOM.setInnerText(th5, "Note");
    Element th6 = DOM.createTH();
    DOM.setInnerText(th6, "Date");
    DOM.insertChild(tr, th1, 0);
    DOM.insertChild(tr, th2, 1);
    DOM.insertChild(tr, th3, 2);
    DOM.insertChild(tr, th4, 3);
    DOM.insertChild(tr, th5, 4);
    DOM.insertChild(tr, th6, 5);
    DOM.insertChild(thead, tr, 0);
    DOM.insertChild(consumptionTable.getElement(), thead, 0);
  }

  private void fetchConsumptionsOfApartment(String apartmentName) {
    balanceService.fetchConsumptionsOfApartment(apartmentName, new AsyncCallback<Consumption[]>() {
      @Override
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);      
      }
      @Override
      public void onSuccess(Consumption[] result) {
        for (int i = 0; i < result.length; i++) {
          final Consumption c = result[i];      
          consumptionTable.setHTML(i + 1, 0, c.name);         
          consumptionTable.setHTML(i + 1, 1, Formatter.formatMoney(c.amount));
          consumptionTable.setHTML(i + 1, 2, c.paidByUser);
          consumptionTable.setHTML(i + 1, 3, c.type);
          consumptionTable.setHTML(i + 1, 4, c.note);
          consumptionTable.setHTML(i + 1, 5, Formatter.dateToString(c.date));
        }
        /** The following code is useful when we do paging
        // remove the rest of previous page, if any
        int lastLine = result.length + 2;
        while (consumptionTable.getRowCount() > lastLine) {
          consumptionTable.removeRow(lastLine);
        }
        **/
      }      
    });
  }

  private void fetchConsumptionTypes() {
    balanceService.fetchConsumptionType(new AsyncCallback<ConsumptionType[]>() {
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);        
      }
      public void onSuccess(ConsumptionType[] result) {
        categorySelect.clear();
        for (ConsumptionType type : result) {
          categorySelect.addItem(type.type);
        }
      }
    });
  }

  private void fetchUsersOfApartment(String apartmentName) {
    if (apartmentName == null || apartmentName.equals("")) {
      return;
    }
    balanceService.fetchUsersByApartment(apartmentName, new AsyncCallback<User[]>() {
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);
      }
      public void onSuccess(User[] result) {
        paidByInput.clear();
        sharedByCheckBoxList.clear();
        sharedByCheckBoxContainer().clear();
        for (int i = 0; i < result.length; i++) {
          final User u = result[i];      
          balanceTable.setHTML(i + 1, 0, u.name);         
          balanceTable.setHTML(i + 1, 1, Formatter.formatMoney(u.balance));
          Button editButton = new Button("View user details");
          editButton.setStyleName("btn btn-primary btn-mini");
          editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              // TODO : add handler of editing user
            }
          });
          balanceTable.setWidget(i + 1, 2, editButton);
          paidByInput.addItem(u.name, u.username);
          HorizontalCheckBox cb = createSharedByCheckBox(u);
          sharedByCheckBoxList.add(cb);
          sharedByCheckBoxContainer().add(cb);
        }
        // remove the rest of previous page, if any
        /**
        int lastLine = result.length + 1;
        while (balanceTable.getRowCount() > lastLine) {
          balanceTable.removeRow(lastLine);
        }
        **/
      }  
    });
  }
  
  private HorizontalCheckBox createSharedByCheckBox(final User u) {
    HorizontalCheckBox cb = new HorizontalCheckBox(u.name, true);
    cb.setAssociateVal(u.username);
    cb.setValue(true);
    return cb;
  }   
  
  private Panel sharedByCheckBoxContainer() {
    return sharedByContainer;
  }

  private void addHeaderToBalanceTable() {
    Element thead = DOM.createTHead();
    Element tr = DOM.createTR();
    Element th1 = DOM.createTH();
    DOM.setInnerText(th1, "Name");
    Element th2 = DOM.createTH();
    DOM.setInnerText(th2, "Balance");
    Element th3 = DOM.createTH();
    DOM.setInnerText(th3, "Operations");
    DOM.insertChild(tr, th1, 0);
    DOM.insertChild(tr, th2, 1);
    DOM.insertChild(tr, th3, 2);
    DOM.insertChild(thead, tr, 0);
    DOM.insertChild(balanceTable.getElement(), thead, 0);
  }

  @UiHandler("addConsumptionButton")
  void onAddConsumptionButtonClick(ClickEvent event) {
    String name = consumptionNameInput.getText();
    int index = paidByInput.getSelectedIndex();
    String userActualName = paidByInput.getItemText(index);
    String paidByUsername = paidByInput.getValue(index);
    String[] sharedByUsers = getSharedByUsers();
    Date date = new Date();
    double amount = Double.valueOf(amountInput.getText());
    String category = categorySelect.getItemText(categorySelect.getSelectedIndex());
    String note = noteTextArea.getText();
    Consumption consumption = new Consumption(name, date, amount, note, paidByUsername, userActualName, category, apartmentName, sharedByUsers);
    balanceService.addConsumption(consumption, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);        
      }
      @Override
      public void onSuccess(Void result) {
        // reset the form
        consumptionNameInput.setText("");
        paidByInput.clear();
        amountInput.setText("");
        noteTextArea.setText("");
        fetchConsumptionsOfApartment(apartmentName);
        fetchUsersOfApartment(apartmentName);
        // show alert 
        String text = "If the balance is not updated correctly, please refresh the page in a few seconds.";
        alertContainer.clear();
        alertContainer.add(createAlert(text));
      }      
    });
  }
  
  private Alert createAlert(String text) {
    Alert alert = GWT.create(Alert.class);
    alert.showAlert(text);
    return alert;
  }
  
  private String[] getSharedByUsers() {
    ArrayList<String> sharedByList = Lists.newArrayList();
    for (HorizontalCheckBox hcb : sharedByCheckBoxList) {
      if (hcb.getValue()) {
        sharedByList.add(hcb.getAssociateVal());
      }
    }
    return sharedByList.toArray(new String[0]);
  }

  @UiHandler("addNewCategoryButton")
  void onAddNewCategoryButtonClicked(ClickEvent event) {
    final String type = newCategoryInput.getText().trim();
    balanceService.addConsumptionType(type, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        defaultErrorHandler(caught);       
      }
      @Override
      public void onSuccess(Void result) {
        categorySelect.addItem(type);
        newCategoryInput.setText("");
      }     
    });
  }
  
  public static native void showDialog(String dialogId) /*-{
    $wnd.$('#' + dialogId).modal('show');
  }-*/;

  public static native void hideDialog(String dialogId) /*-{
    $wnd.$('#' + dialogId).modal('hide');
  }-*/;
  
  public static void showNotification(String title, String message) {
    notificationDialog.setTitle(title);
    notificationDialog.setMessage(message);
    showDialog(NotificationDialog.id);
  }
  
  private void defaultErrorHandler(Throwable caught) {
    notificationDialog.showNotification("Error", caught.getMessage());
  }
}
