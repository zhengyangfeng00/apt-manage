package org.zhengyang.aptmanagement.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zhengyang.aptmanagement.server.inject.DaoModule;
import org.zhengyang.aptmanagement.server.inject.ServiceModule;
import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 9, 2012
 */
public class ApartmentDaoTest {
  
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0));
  private UserDao dao;
  private ApartmentDao apartmentDao;
  Injector injector = Guice.createInjector(new ServiceModule(), new DaoModule());
  
  @Before
  public void setUp() {
    helper.setUp();
    dao = injector.getInstance(UserDao.class);
    apartmentDao = injector.getInstance(ApartmentDao.class);
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }
  
  @Test
  public void testAddApartment() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    Apartment actual = apartmentDao.getApartment("South Ampton");
    assertEquals(apartment, actual);
  }
  
  @Test(expected = Exception.class)
  public void testAddApartmentException() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    apartmentDao.addAppartment(apartment);
  }
  
  @Test
  public void testUpdateApartment() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    apartment.address = "30 River Court";
    apartmentDao.updateApartment(apartment);
    assertEquals("30 River Court", apartmentDao.getApartment("South Ampton").address);
  }
  
  @Test(expected = Exception.class)
  public void testUpdateApartmentException() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.updateApartment(apartment);
  }
  
  @Test
  public void testExistApartment() throws Exception {
    assertFalse(apartmentDao.existApartment("South Ampton"));
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    assertTrue(apartmentDao.existApartment("South Ampton"));
  }
  
  private List<User> users; {
    users = Lists.newArrayList();
    String apt1 = "Nice apartment";
    String apt2 = "Excellent apartment";
    User xiaoming = new User("zf301", "Xiaoming", 0, "xiaoming@gmail.com", Role.ADMIN, apt1);
    User xiaohuang = new User("zf302", "Xiaohuang", 0, "xiaohuang@gmail.com", Role.RESIDENT, apt2);
    users.add(xiaoming);
    users.add(xiaohuang);
  }

  @Test
  public void testAddResidents() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    apartmentDao.addResidents(apartment, users);
    apartment = apartmentDao.getApartment("South Ampton");
    assertTrue(apartment.residentsMap.contains("Xiaoming:zf301"));
    assertTrue(apartment.residentsMap.contains("Xiaohuang:zf302"));
    assertEquals("South Ampton", dao.getUserByUsername("zf301").apartmentName);
    assertEquals("South Ampton", dao.getUserByUsername("zf302").apartmentName);
    assertEquals(Role.RESIDENT, dao.getUserByUsername("zf301").role);
    assertEquals(Role.RESIDENT, dao.getUserByUsername("zf302").role);
  }
  
  @Test
  public void testAddAdmins() throws Exception {
    Apartment apartment = new Apartment("South Ampton", "20 River Court");
    apartmentDao.addAppartment(apartment);
    apartmentDao.addAdmins(apartment, users);
    apartment = apartmentDao.getApartment("South Ampton");
    assertTrue(apartment.adminMap.contains("Xiaoming:zf301"));
    assertTrue(apartment.adminMap.contains("Xiaohuang:zf302"));
    assertEquals("South Ampton", dao.getUserByUsername("zf301").apartmentName);
    assertEquals("South Ampton", dao.getUserByUsername("zf302").apartmentName);
    assertEquals(Role.ADMIN, dao.getUserByUsername("zf301").role);
    assertEquals(Role.ADMIN, dao.getUserByUsername("zf302").role);
  }
}
