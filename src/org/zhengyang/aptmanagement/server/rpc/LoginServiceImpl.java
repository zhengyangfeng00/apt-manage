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

import org.zhengyang.aptmanagement.client.index.LoginService;
import org.zhengyang.aptmanagement.server.dao.UserDao;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 21, 2012
 */
@Singleton
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
  private static final long serialVersionUID = 1L;
  private final UserDao userDao;
  
  @Inject
  private LoginServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public boolean validate(String username, String password) {
    return userDao.checkPassword(username, password);
  }
}
