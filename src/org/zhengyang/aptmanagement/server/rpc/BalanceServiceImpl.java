package org.zhengyang.aptmanagement.server.rpc;

import java.util.Arrays;
import java.util.List;

import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.server.dao.ApartmentDao;
import org.zhengyang.aptmanagement.server.dao.ConsumptionDao;
import org.zhengyang.aptmanagement.server.dao.UserDao;
import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.appengine.labs.repackaged.com.googlecode.charts4j.collect.Lists;
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

  @Override
  public void revertConsumption(Long consumptionId) {
    List<User> toBeUpdated = Lists.newArrayList();
    Consumption csp = consumptionDao.getConsumption(consumptionId);
    
    // 1. Subtract money from who paid it
    User paidBy = userDao.getUserByUsername(csp.paidByUsername);
    paidBy.balance -= csp.amount;
    toBeUpdated.add(paidBy);
    // 2. Add money to those shared the consumption
    double moneyPerPerson = csp.amount / csp.sharedByUsers.length;
    for (String uid : csp.sharedByUsers) {
      User u = userDao.getUserByUsername(uid);
      if (u.username.equals(paidBy.username)) {
        u = paidBy;
      }
      u.balance += moneyPerPerson;
      toBeUpdated.add(u);
    }
    // 3. updated related users' balance
    userDao.updateUsers(toBeUpdated.toArray(new User[0]));
    
    // 4. remove the consumption record
    consumptionDao.removeConsumption(consumptionId);
  }
}
