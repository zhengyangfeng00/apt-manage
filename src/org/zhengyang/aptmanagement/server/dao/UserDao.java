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

import org.zhengyang.aptmanagement.shared.dto.User;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
public class UserDao extends DAOBase {
  static {
    ObjectifyService.register(User.class);
  }
  
  @Inject
  private UserDao() { }
  
  public User[] fetchUsers() {
    List<User> users = Lists.newArrayList();
    for (User user : ofy().query(User.class).fetch()) {
      users.add(user);
    }
    return users.toArray(new User[0]);
  }
  
  public User[] fetchUsers(String[] usersId) {   
    return ofy().get(User.class, usersId).values().toArray(new User[0]);
  }
  
  public User[] fetchUserByApartmentName(String apartmentName) {
    return ofy().query(User.class).filter("apartmentName =", apartmentName).list().toArray(new User[0]);
  }
  
  public void addUser(User user) throws Exception {
    if (exist(user.username)) {
      throw new Exception("User has existed, choose another username!");
    }
    ofy().put(user);
  }
  
  protected boolean exist(String username) {
    boolean exist = true;
    try {
      ofy().get(User.class, username);
    } catch (NotFoundException e) {
      exist = false;
    }
    return exist;
  }
  
  public void updateUsers(User[] users) {
    ofy().put(users);
  }
  
  public User getUserByUsername(String username) throws NotFoundException {
    return ofy().get(User.class, username);
  }
  
  public boolean checkPassword(String username, String password) {
    if (!exist(username)) {
      return false;
    } 
    User u = ofy().get(User.class, username);
    return u != null && u.password.equals(password);
  }
}
