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

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 7, 2012
 */
public class ConsumptionType extends Equality implements IsSerializable {
  private static final long serialVersionUID = 1L;
  @Id
  public String type;
  
  public ConsumptionType() { }
  
  public ConsumptionType(String type) {
    this.type = type;
  }

  @Override
  public Object getId() {
    return type;
  }
}
