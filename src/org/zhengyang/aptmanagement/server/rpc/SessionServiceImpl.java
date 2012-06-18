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

import java.util.HashMap;

import org.zhengyang.aptmanagement.client.shared.SessionService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 21, 2012
 */
@Singleton
public class SessionServiceImpl extends RemoteServiceServlet implements SessionService {
  private static final long serialVersionUID = 1L;

  @Override
  public void createSession(HashMap<String, String> data) {
    getThreadLocalRequest().getSession().setAttribute("session", data);
  }

  @Override
  public boolean validateSession() {
    @SuppressWarnings("unchecked")
    HashMap<String, String> session = (HashMap<String, String>) getThreadLocalRequest().getSession().getAttribute("session");
    if (session == null) return false;
    String username = session.get("username");
    if (username != null) {
      return true;
    }
    return false;
  }

  @Override
  public String getCurrentUser() {
    if (!validateSession()) return null;
    @SuppressWarnings("unchecked")
    HashMap<String, String> session = (HashMap<String, String>) getThreadLocalRequest().getSession().getAttribute("session");
    return session.get("username");
  }
}
