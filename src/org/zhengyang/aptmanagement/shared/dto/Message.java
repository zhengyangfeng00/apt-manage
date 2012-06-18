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

package org.zhengyang.aptmanagement.shared.dto;

import java.util.Date;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 15, 2012
 */
public class Message implements IsSerializable {
  @Id
  public Long id;
  public String name;
  public String content;
  public Date date;
  
  public Message() { }
  
  public Message(String name, String content, Date date) {
    this.name = name;
    this.content = content;
    this.date = date;
  }
}
