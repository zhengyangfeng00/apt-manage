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

import java.util.List;

import org.zhengyang.aptmanagement.shared.dto.Apartment;
import org.zhengyang.aptmanagement.shared.dto.Role;
import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.inject.Inject;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 9, 2012
 */
public class ApartmentDao extends DAOBase {
  static {
    ObjectifyService.register(Apartment.class);
  }
  
  private final UserDao userDao;
  
  @Inject
  private ApartmentDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public void addAppartment(Apartment appartment) throws Exception {
    if (existApartment(appartment.name)) {
      throw new Exception("Apartment " + appartment.name + " has already exist.");
    }
    ofy().put(appartment);
  }
  
  public List<Apartment> fetchApartments() {
    return ofy().query(Apartment.class).list();
  }
  
  public void updateApartment(Apartment apartment) throws Exception {
    if (!existApartment(apartment.name)) {
      throw new Exception("Apartment " + apartment.name + " doesn't exist.");
    }
    ofy().put(apartment);
  }
  
  public Apartment getApartment(String name) {
    return ofy().get(Apartment.class, name);
  }
  
  protected boolean existApartment(String name) {
    boolean exist = true;
    try {
      ofy().get(Apartment.class, name);
    } catch (NotFoundException e) {
      exist = false;
    }
    return exist;
  }
  
  public void addResidents(Apartment apartment, List<User> users) throws Exception {
    List<String> residentMap = apartment.residentsMap;
    for (User user : users) {
      // if user himself is the admin of this apartment
      // do not change his infomation
      if (user.apartmentName.equals(apartment.name) && (user.role == Role.ADMIN) || user.role == Role.SUPER_ADMIN) {
        continue;
      }
      user.apartmentName = apartment.name;
      user.role = Role.RESIDENT;
      residentMap.add(getUserAndUsernameStr(user));
    }
    userDao.updateUsers(users.toArray(new User[0]));
    updateApartment(apartment);
  }
  
  private String getUserAndUsernameStr(User user) {
    return user.name + ":" + user.username;
  }
  
  public void addAdmins(Apartment apartment, List<User> users) throws Exception {
    List<String> adminMap = apartment.adminMap;
    for (User user : users) {
      // if user is a resident in this apartment
      // remove him from residents map      
      if (apartment.residentsMap.contains(getUserAndUsernameStr(user))) {
        apartment.residentsMap.remove(getUserAndUsernameStr(user));
      }
      user.apartmentName = apartment.name;
      user.role = Role.ADMIN;
      adminMap.add(getUserAndUsernameStr(user));
    }
    userDao.updateUsers(users.toArray(new User[0]));
    updateApartment(apartment);
  }
}
