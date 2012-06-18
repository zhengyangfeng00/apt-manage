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
  
  Consumption[] fetchConsumptions();
  
  Consumption[] fetchConsumptionsOfApartment(String apartmentName);
  
  void addApartment(Apartment apt, String admin) throws Exception;
  
  void addUser(User user) throws Exception;
  
  Apartment[] fetchApartments();
  
}
