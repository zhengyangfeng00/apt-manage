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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zhengyang.aptmanagement.server.inject.DaoModule;
import org.zhengyang.aptmanagement.server.inject.ServiceModule;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
public class UserDaoTest {
  
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0));
  private UserDao dao;
  Injector injector = Guice.createInjector(new ServiceModule(), new DaoModule());
  
  @Before
  public void setUp() {
    helper.setUp();
    dao = injector.getInstance(UserDao.class);
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void testAddUser() throws Exception {
    User user = new User("zf309", "Zhengyang", 0, "zhengyang.feng2011@gmail.com", Role.SUPER_ADMIN, "apt1");
    dao.addUser(user);
    assertEquals(user, dao.getUserByUsername("zf309"));
  }
  
  @Test(expected = Exception.class)
  public void testAddUserException() throws Exception {
    dao.addUser(xiaoming);
    dao.addUser(xiaoming);
  }
  
  User xiaoming = new User("zf301", "Xiaoming", 0, "xiaoming@gmail.com", Role.ADMIN, "apt1");
  User xiaohuang = new User("zf302", "Xiaohuang", 0, "xiaohuang@gmail.com", Role.RESIDENT, "apt2");

  @Test
  public void testFetchUsers() throws Exception {
    dao.addUser(xiaoming);
    dao.addUser(xiaohuang);
    User[] actual = dao.fetchUsers();
    assertEquals(xiaoming, actual[0]);
    assertEquals(xiaohuang, actual[1]);
  }
  
  @Test
  public void testFetchUsersWithParameter() throws Exception {
    dao.addUser(xiaoming);
    dao.addUser(xiaohuang);
    String[] usersId = {xiaoming.username, xiaohuang.username};
    assertEquals(2, dao.fetchUsers(usersId).length);
  }
  
  @Test
  public void testExist() throws Exception {
    dao.addUser(xiaoming);
    assertTrue(dao.exist(xiaoming.username));
    assertFalse(dao.exist(xiaohuang.username));
  }
  
  @Test
  public void testFetchUserByApartmentName() throws Exception {
    User[] residents = dao.fetchUserByApartmentName("not exist");
    assertEquals(0, residents.length);
    dao.addUser(xiaoming);
    assertEquals(xiaoming, dao.fetchUserByApartmentName("apt1")[0]);
  }
}
