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

package org.zhengyang.aptmanagement.client.index;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.client.balance.BalanceServiceAsync;
import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 9, 2012
 */
public class RegisterUserDialog extends Composite {

  private static RegisterDialogUiBinder uiBinder = GWT.create(RegisterDialogUiBinder.class);

  interface RegisterDialogUiBinder extends UiBinder<Widget, RegisterUserDialog> {
  }
  
  private BalanceServiceAsync service = GWT.create(BalanceService.class);
  
  @UiField TextBox usernameInput;
  @UiField PasswordTextBox passwordInput;
  @UiField TextBox nameInput;
  @UiField TextBox emailInput;
  @UiField ListBox apartmentList;
  @UiField Button closeBtn;
  @UiField Button registerUserBtn;
  
  public static final String id = "registerUserDialog";

  public RegisterUserDialog() {
    initWidget(uiBinder.createAndBindUi(this));
    service.fetchApartments(new AsyncCallback<Apartment[]>() {
      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub        
      }
      @Override
      public void onSuccess(Apartment[] result) {
        apartmentList.clear();
        apartmentList.addItem("None", "");
        for (Apartment apt : result) {
          apartmentList.addItem(apt.name, apt.name);
        }
      }
    });
  }
  
  @UiHandler("closeBtn")
  public void closeBtnClicked(ClickEvent event) {
    clearForm();
  }
  
  @UiHandler("registerUserBtn")
  public void registerUserBtnClicked(ClickEvent event) {
    String username = usernameInput.getText().trim();
    String password = passwordInput.getText().trim();
    String name = nameInput.getText().trim();
    String email = emailInput.getText().trim();
    String apt = apartmentList.getValue(apartmentList.getSelectedIndex());
    User user = new User(username, password, name, email, apt);
    service.addUser(user, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        Index.showNotification("Opps..An error occured", caught.getMessage());
      }
      @Override
      public void onSuccess(Void result) {
        Index.showNotification("Success", "The user has been registered.");
      }    
    });
    Index.hideRegisterUserDialog();
    clearForm();
  }
  
  private void clearForm() {
    usernameInput.setText("");
    passwordInput.setText("");
    nameInput.setText("");
    emailInput.setText("");
    apartmentList.setSelectedIndex(0);
  }
}
