package org.zhengyang.aptmanagement.client.balance;

import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 7, 2012
 */
@RemoteServiceRelativePath("balanceService")
public interface BalanceService extends RemoteService {
  User[] fetchUsers();
  
  User[] fetchUsersByApartment(String apartmentName);
  
  User getUser(String username);
  
  void updateUserApartment(String username, String password, String apartmentName) throws Exception;
  
  ConsumptionType[] fetchConsumptionType();
  
  void addConsumptionType(String type);
  
  void addConsumption(Consumption consumption);
  
  void revertConsumption(Long consumptionId);
  
  Consumption[] fetchConsumptions();
  
  Consumption[] fetchConsumptionsOfApartment(String apartmentName);
  
  void addApartment(Apartment apt, String admin) throws Exception;
  
  void addUser(User user) throws Exception;
  
  Apartment[] fetchApartments();
  
}
