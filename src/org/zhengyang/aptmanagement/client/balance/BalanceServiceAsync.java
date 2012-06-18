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

}
