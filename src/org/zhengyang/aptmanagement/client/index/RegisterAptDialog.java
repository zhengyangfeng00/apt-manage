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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 10, 2012
 */
public class RegisterAptDialog extends Composite {
  private static RegisterAptDialogUiBinder uiBinder = GWT.create(RegisterAptDialogUiBinder.class);
  interface RegisterAptDialogUiBinder extends UiBinder<Widget, RegisterAptDialog> {
  }
  private BalanceServiceAsync service = GWT.create(BalanceService.class);
  private LoginServiceAsync loginService = GWT.create(LoginService.class);
  @UiField TextBox aptnameInput;
  @UiField TextBox addressInput;
  @UiField Button closeBtn;
  @UiField Button registerAptBtn;
  @UiField TextBox loginIdInput;
  @UiField PasswordTextBox passwordInput;
  
  public static final String id = "registerAptDialog";
  
  public RegisterAptDialog() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiHandler("closeBtn")
  public void closeBtnClicked(ClickEvent event) {
    aptnameInput.setText("");
    addressInput.setText("");
  }
  
  @UiHandler("registerAptBtn") 
  public void registerAptBtnClicked(ClickEvent event) {
    final String apartmentName = aptnameInput.getText().trim();
    final String address = addressInput.getText().trim();
    final String username = loginIdInput.getText().trim();
    String password = passwordInput.getText().trim();
    loginService.validate(username, password, new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(Throwable caught) {
        Index.showNotification("Error", caught.getMessage());
      }
      @Override
      public void onSuccess(Boolean result) {
        if (result) { // if username & password is correct
          Apartment apt = new Apartment(apartmentName, address);
          service.addApartment(apt, username, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
              Index.showNotification("Error", caught.getMessage());   
            }
            @Override
            public void onSuccess(Void result) {
              Index.showNotification("Success", "The apartment has been registered.");
            }     
          });
        } else {
          Index.showNotification("Error", "User name or password is not correct.");
        }
      }
    });
    Index.hideRegisterAptDialog();
  }
}
