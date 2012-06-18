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

package org.zhengyang.aptmanagement.server.rpc;

import java.util.Arrays;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.server.dao.ApartmentDao;
import org.zhengyang.aptmanagement.server.dao.ConsumptionDao;
import org.zhengyang.aptmanagement.server.dao.UserDao;
import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
@Singleton
public class BalanceServiceImpl extends RemoteServiceServlet implements BalanceService {
  private static final long serialVersionUID = 1L;
  private final UserDao userDao;
  private final ConsumptionDao consumptionDao;
  private final ApartmentDao aptDao;
  
  @Inject
  private BalanceServiceImpl(UserDao userDao, ConsumptionDao consumptionDao, ApartmentDao aptDao) {
    this.userDao = userDao;
    this.consumptionDao = consumptionDao;
    this.aptDao = aptDao;
  }

  @Override
  public User[] fetchUsers() {
    return userDao.fetchUsers();
  }

  @Override
  public ConsumptionType[] fetchConsumptionType() {
    return consumptionDao.fetchConsumptionTypes().toArray(new ConsumptionType[0]);
  }

  @Override
  public void addConsumption(Consumption consumption) {
    // 1. save the consumption into db
    consumptionDao.addConsumption(consumption);
    // 2. alter the balance of the user
    User user = userDao.getUserByUsername(consumption.paidByUsername);
    user.balance += consumption.amount;
    userDao.updateUsers(Arrays.asList(user).toArray(new User[0]));
    double onePiece = consumption.amount / consumption.sharedByUsers.length;
    User[] users = userDao.fetchUsers(consumption.sharedByUsers);
    for (User u : users) {     
      u.balance = u.balance - onePiece;
    }
    // 3. update users' balance
    userDao.updateUsers(users);
  }

  @Override
  public Consumption[] fetchConsumptions() {
    return consumptionDao.fetchConsumptions().toArray(new Consumption[0]);
  }

  @Override
  public void addConsumptionType(String type) {
    ConsumptionType consumptionType = new ConsumptionType(type);
    try {
      consumptionDao.addConsumptionType(consumptionType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addApartment(Apartment apt, String admin) throws Exception {
    User u = userDao.getUserByUsername(admin);
    // if user's balance is not 0, we don't allow him to create a new apartment
    if (!equalZero(u.balance, 0.01)) {
      throw new Exception("User's balance is not clear, you are not allowed to create a new apartment.");
    }
    aptDao.addAppartment(apt);
    u.apartmentName = apt.name;
    User[] users = { u };
    userDao.updateUsers(users);
  }
  
  private boolean equalZero(double x, double tolerance) {
    return abs(x - 0) <= tolerance; 
  }
  
  private double abs(double x) {
    return x > 0? x : -x;
  }

  @Override
  public void addUser(User user) throws Exception {
    userDao.addUser(user);
  }

  @Override
  public Apartment[] fetchApartments() {
    return aptDao.fetchApartments().toArray(new Apartment[0]);
  }

  @Override
  public User getUser(String username) {
    return userDao.getUserByUsername(username);
  }

  /**
   * Return consumptions given department name, order by date.
   */
  @Override
  public Consumption[] fetchConsumptionsOfApartment(String apartmentName) {
    return consumptionDao.fetchConsumptions(apartmentName).toArray(new Consumption[0]);
  }

  @Override
  public User[] fetchUsersByApartment(String apartmentName) {
    return userDao.fetchUserByApartmentName(apartmentName);
  }

  @Override
  public void updateUserApartment(String username, String password, String apartmentName) throws Exception {
    if (!userDao.checkPassword(username, password)) {
      throw new Exception("The username or password is not correct.");
    }
    User u = userDao.getUserByUsername(username);
    u.apartmentName = apartmentName;
    User[] users = { u };
    userDao.updateUsers(users);
  }
}
