package org.zhengyang.aptmanagement.client.index;


import java.util.HashMap;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.client.balance.BalanceServiceAsync;
import org.zhengyang.aptmanagement.client.navigate.NavigateWidget;
import org.zhengyang.aptmanagement.client.shared.SessionService;
import org.zhengyang.aptmanagement.client.shared.SessionServiceAsync;
import org.zhengyang.aptmanagement.client.shared.widgets.NotificationDialog;

import com.google.common.collect.Maps;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Index implements EntryPoint {
  private final NavigateWidget navigateWidget = GWT.create(NavigateWidget.class);
  private final TextBox usernameInput = GWT.create(TextBox.class);
  private final PasswordTextBox passwordInput = GWT.create(PasswordTextBox.class);
  private final Button loginButton = GWT.create(Button.class);
  private final Button registerButton = GWT.create(Button.class);
  private final Button addUserToAptButton = GWT.create(Button.class);
  private final Anchor registerAptButton = GWT.create(Anchor.class);
  private final RegisterUserDialog registerUserDialog = GWT.create(RegisterUserDialog.class);
  private final RegisterAptDialog registerAptDialog = GWT.create(RegisterAptDialog.class);
  private final AddUserToAptDialog addUserToAptDialog = GWT.create(AddUserToAptDialog.class);
  private final static NotificationDialog notificationDialog = NotificationDialog
      .setUpNotificationDialog();
  private BalanceServiceAsync service = GWT.create(BalanceService.class);
  private LoginServiceAsync loginService = GWT.create(LoginService.class);
  private SessionServiceAsync sessionService = GWT.create(SessionService.class);
  private Storage store = null;

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(navigateWidget);
    RootPanel.get("usernameInputContainer").add(usernameInput);
    RootPanel.get("passwordInputContainer").add(passwordInput);
    
    /* Code for Login Button */
    loginButton.setText("Sign in!");
    loginButton.setStyleName("btn btn-success");
    loginButton.addClickHandler(new LoginClickHandler());
    RootPanel.get("loginButtonContainer").add(loginButton);
    
    /* Code for Register User Button*/
    registerButton.setText("Register");
    registerButton.setStyleName("btn btn-primary btn-large");
    registerButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        RootPanel.get().add(registerUserDialog);
        showDialog(RegisterUserDialog.id);
      }
    });
    RootPanel.get("registerButtonContainer").add(registerButton);
    
    /* Code for Register apartment button */
    registerAptButton.setText("Register Apartment");
    registerAptButton.setStyleName("btn");
    registerAptButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        RootPanel.get().add(registerAptDialog);
        showDialog(RegisterAptDialog.id);
      }
    });
    RootPanel.get("registerAppartmentButtonContainer").add(registerAptButton);
    
    /* Code for Add User to Apt button*/
    addUserToAptButton.setText("Add to Apartment");
    addUserToAptButton.setStyleName("btn");
    addUserToAptButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        RootPanel.get().add(addUserToAptDialog);
        showDialog(AddUserToAptDialog.id);
      }      
    });
    RootPanel.get("addUserToAptButtonContainer").add(addUserToAptButton);
  }

  public static native void showDialog(String dialogId) /*-{
		$wnd.$('#' + dialogId).modal('show');
  }-*/;

  public static native void hideDialog(String dialogId) /*-{
		$wnd.$('#' + dialogId).modal('hide');
  }-*/;

  public static void hideRegisterAptDialog() {
    hideDialog(RegisterAptDialog.id);
  }

  public static void hideRegisterUserDialog() {
    hideDialog(RegisterUserDialog.id);
  }
  
  public static void hideAddUserToAptDialog() {
    hideDialog(AddUserToAptDialog.id);
  }

  public static void showNotification(String title, String message) {
    notificationDialog.setTitle(title);
    notificationDialog.setMessage(message);
    showDialog(NotificationDialog.id);
  }

  class LoginClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      final String username = usernameInput.getText();
      String password = passwordInput.getText();
      loginService.validate(username, password, new AsyncCallback<Boolean>() {
        @Override
        public void onFailure(Throwable caught) {
          showNotification("Error", caught.getMessage());
        }

        @Override
        public void onSuccess(Boolean correct) {
//          saveUserInfoWithLocalStorage(username, correct);
          // TODO : should we make sure the username is a safe string?
          if (correct) {           
            HashMap<String, String> data = Maps.newHashMap();
            data.put("username", username);
            sessionService.createSession(data, new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable caught) {
                showNotification("Error", "Something is wrong... Try again.");
              }
              @Override
              public void onSuccess(Void result) {
                Window.Location.assign("/balance.html");
              }
            });           
          } else {
            showNotification("Error", "Username or password is not correct.");
          }
        }
      });
    }
  }
}
