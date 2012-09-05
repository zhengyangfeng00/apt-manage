package org.zhengyang.aptmanagement.server.rpc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zhengyang.aptmanagement.client.balance.BalanceService;
import org.zhengyang.aptmanagement.server.dao.UserDao;
import org.zhengyang.aptmanagement.server.inject.DaoModule;
import org.zhengyang.aptmanagement.server.inject.ServiceModule;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 23, 2012
 */
public class BalanceServiceImplTest {
  
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0));
  
  User user1 = new User("zf309", "Zhengyang", 0, "zhengyang.feng2011@gmail.com", Role.SUPER_ADMIN, "apt1");
  User user2 = new User("zf301", "Xiaoming", 0, "zhengyang.feng2011@gmail.com", Role.RESIDENT, "apt1");
  User user3 = new User("zf302", "Xiaohong", 0, "zhengyang.feng2011@gmail.com", Role.ADMIN, "apt1");
  Injector injector = Guice.createInjector(new ServiceModule(), new DaoModule());
  private UserDao userDao;
  private BalanceService srv;
  
  @Test
  public void testAddConsumption() {
    String name = "法拉盛买菜";
    Date date = new Date();
    String note = "This is the note.";
    double amount = 300;
    User paidBy = userDao.getUserByUsername("zf309");
    String type = "买菜";
    String apartmentName = "Two-Tower of Curry";
    String[] sharedBy = {user1.username, user2.username, user3.username};
    Consumption consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy);
    srv.addConsumption(consumption);
    double tolerance = 0.001;
    assertEquals(200, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(-100, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(-100, userDao.getUserByUsername("zf302").balance, tolerance);
    String[] sharedBy2 = {user2.username, user3.username};
    consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy2);
    srv.addConsumption(consumption);
    assertEquals(500, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(-250, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(-250, userDao.getUserByUsername("zf302").balance, tolerance);
    String[] sharedBy3 = {user2.username};
    consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy3);
    srv.addConsumption(consumption);
    assertEquals(800, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(-550, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(-250, userDao.getUserByUsername("zf302").balance, tolerance);
    String[] sharedBy4 = {user1.username};
    consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy4);
    srv.addConsumption(consumption);
    assertEquals(800, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(-550, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(-250, userDao.getUserByUsername("zf302").balance, tolerance);
  }
  
  @Test
  public void testRevertConsumption() {
    String name = "法拉盛买菜";
    Date date = new Date();
    String note = "This is the note.";
    double amount = 300;
    User paidBy = userDao.getUserByUsername("zf309");
    String type = "买菜";
    String apartmentName = "Two-Tower of Curry";
    String[] sharedBy = {user1.username, user2.username, user3.username};
    Consumption consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy);
    srv.addConsumption(consumption);
    double tolerance = 0.001;
    System.out.println(userDao.getUserByUsername("zf309").balance);
    System.out.println(userDao.getUserByUsername("zf301").balance);
    System.out.println(userDao.getUserByUsername("zf302").balance);
    assertEquals(200.0, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(-100.0, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(-100.0, userDao.getUserByUsername("zf302").balance, tolerance);
    
    srv.revertConsumption(consumption.id);
    System.out.println(userDao.getUserByUsername("zf309").balance);
    System.out.println(userDao.getUserByUsername("zf301").balance);
    System.out.println(userDao.getUserByUsername("zf302").balance);
    assertEquals(0.0, userDao.getUserByUsername("zf309").balance, tolerance);
    assertEquals(0.0, userDao.getUserByUsername("zf301").balance, tolerance);
    assertEquals(0.0, userDao.getUserByUsername("zf302").balance, tolerance);
  }
  
  @Before
  public void setUp() throws Exception {
    helper.setUp();
    srv = injector.getInstance(BalanceService.class);
    userDao = injector.getInstance(UserDao.class);
    userDao.addUser(user1);
    userDao.addUser(user2);
    userDao.addUser(user3);
    
  }
  
  @After
  public void tearDown() {
    helper.tearDown();
  }

}
