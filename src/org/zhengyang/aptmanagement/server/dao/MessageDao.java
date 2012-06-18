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

import com.google.appengine.api.mail.MailService.Message;
import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 15, 2012
 */
public class MessageDao extends DAOBase {
  static {
    ObjectifyService.register(Message.class);
  }
  
  @Inject
  private MessageDao() { }
  
  public List<Message> fetchMessages() {
    return ofy().query(Message.class).list();
  }
  
  public void addMessage(Message msg) {
    ofy().put(msg);
  }
  
  public void deleteMessage(Long id) {
    ofy().delete(Message.class, id);
  }
}
