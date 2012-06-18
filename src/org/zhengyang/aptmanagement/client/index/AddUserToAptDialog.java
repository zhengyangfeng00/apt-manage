package org.zhengyang.aptmanagement.client.index;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.client.balance.BalanceServiceAsync;
import org.zhengyang.aptmanagement.shared.dto.Apartment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 23, 2012
 */
public class AddUserToAptDialog extends Composite {

  private static AddUserToAptDialogUiBinder uiBinder = GWT.create(AddUserToAptDialogUiBinder.class);
  @UiField TextBox loginIdInput;
  @UiField PasswordTextBox passwordInput;
  @UiField ListBox apartmentList;
  interface AddUserToAptDialogUiBinder extends UiBinder<Widget, AddUserToAptDialog> {
  }
  public static final String id = "addUserToAptDialog";
  private BalanceServiceAsync service = GWT.create(BalanceService.class);
  
  public AddUserToAptDialog() {
    initWidget(uiBinder.createAndBindUi(this));
    service.fetchApartments(new AsyncCallback<Apartment[]>() {
      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub        
      }
      @Override
      public void onSuccess(Apartment[] result) {
        apartmentList.clear();
        for (Apartment apt : result) {
          apartmentList.addItem(apt.name, apt.name);
        }
      }
    });
  }
  
  @UiHandler("addAptBtn")
  public void addAptButtonClicked(ClickEvent event) {
    final String username = loginIdInput.getText().trim();
    String password = passwordInput.getText();
    final String apartmentName = apartmentList.getValue(apartmentList.getSelectedIndex());
    service.updateUserApartment(username, password, apartmentName, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        Index.showNotification("Error", caught.getMessage());
      }
      @Override
      public void onSuccess(Void result) {
        Index.showNotification("Success", "User " + username + " has been added to " + apartmentName + "!");
      }
    });
    Index.hideAddUserToAptDialog();
  }

  @UiHandler("closeBtn")
  public void closeBtnClicked(ClickEvent event) {
    clearForm();
  }
  
  private void clearForm() {
    loginIdInput.setText("");
    passwordInput.setText("");
    apartmentList.setSelectedIndex(0);
  }
}
