package org.zhengyang.aptmanagement.client.balance;

import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 7, 2012
 */
public interface BalanceServiceAsync {

  void fetchUsers(AsyncCallback<User[]> callback);

  void fetchConsumptionType(AsyncCallback<ConsumptionType[]> callback);

  void addConsumption(Consumption consumption, AsyncCallback<Void> callback);

  void fetchConsumptions(AsyncCallback<Consumption[]> callback);

  void addConsumptionType(String type, AsyncCallback<Void> callback);

  void addApartment(Apartment apt, String admin, AsyncCallback<Void> callback);

  void addUser(User user, AsyncCallback<Void> callback);

  void fetchApartments(AsyncCallback<Apartment[]> callback);

  void getUser(String username, AsyncCallback<User> callback);

  void fetchConsumptionsOfApartment(String apartmentName, AsyncCallback<Consumption[]> callback);

  void fetchUsersByApartment(String apartmentName, AsyncCallback<User[]> callback);

  void updateUserApartment(String username, String password, String apartmentName, AsyncCallback<Void> callback);

  void revertConsumption(Long consumptionId, AsyncCallback<Void> callback);

}
