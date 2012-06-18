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

package org.zhengyang.aptmanagement.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zhengyang.aptmanagement.server.inject.DaoModule;
import org.zhengyang.aptmanagement.server.inject.ServiceModule;
import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 8, 2012
 */
public class ConsumptionDaoTest {
  
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0));
  Injector injector = Guice.createInjector(new ServiceModule(), new DaoModule());
  private ConsumptionDao dao;
  private UserDao userDao;
  private Consumption consumption;
  
  @Before
  public void setUp() throws Exception {
    helper.setUp();
    dao = injector.getInstance(ConsumptionDao.class);
    userDao = injector.getInstance(UserDao.class);
    String name = "法拉盛买菜";
    Date date = new Date();
    double amount = 98;
    String note = "";
    User user = new User("zf309", "Zhengyang", 0, "zhengyang.feng2011@gmail.com", Role.SUPER_ADMIN, "apt1");
    userDao.addUser(user);
    User user2 = new User("zf301", "Xiaoming", 0, "zhengyang.feng2011@gmail.com", Role.RESIDENT, "apt1");
    User user3 = new User("zf302", "Xiaohong", 0, "zhengyang.feng2011@gmail.com", Role.ADMIN, "apt1");
    userDao.addUser(user2);
    userDao.addUser(user3);
    User paidBy = userDao.getUserByUsername("zf309");
    String type = "买菜";
    String apartmentName = "Two-Tower of Curry";
    dao.addConsumptionType(new ConsumptionType(type));
    String[] sharedBy = {user.username, user2.username, user3.username};
    consumption = new Consumption(name, date, amount, note, paidBy.username, paidBy.name, type, apartmentName, sharedBy);
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void testAddConsumption() throws Exception {
    dao.addConsumption(consumption);
    Consumption actual = dao.fetchConsumptions().get(0);
    assertEquals(consumption, actual);
  }
  
  @Test
  public void testFetchConsumptions() {
    List<Consumption> consumptions = null;
    consumptions = dao.fetchConsumptions("doesn't exist");
    assertTrue(consumptions.isEmpty());
    dao.addConsumption(consumption);
    consumptions = dao.fetchConsumptions("Two-Tower of Curry");
    assertEquals(consumption, consumptions.get(0));
  }
}
