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

import org.zhengyang.aptmanagement.shared.dto.Consumption;
import org.zhengyang.aptmanagement.shared.dto.ConsumptionType;

import com.google.inject.Inject;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author zhengyang.feng2011@gmail.com
 * @creation May 8, 2012
 */
public class ConsumptionDao extends DAOBase {
  static {
    ObjectifyService.register(Consumption.class);
    ObjectifyService.register(ConsumptionType.class);
  }
  
  @Inject
  private ConsumptionDao() { }
  
  public List<ConsumptionType> fetchConsumptionTypes() {
    return ofy().query(ConsumptionType.class).list();
  }
  
  public void addConsumptionType(ConsumptionType type) throws Exception {
    if (existConsumptionType(type)) {
      throw new Exception("User has existed, choose another username!");
    }
    ofy().put(type);
  }
  
  protected boolean existConsumptionType(ConsumptionType type) {
    boolean exist = true;
    try {
      ofy().get(ConsumptionType.class, type.type);
    } catch (NotFoundException e) {
      exist = false;
    }
    return exist;
  }
  
  public void addConsumption(Consumption con) {
    ofy().put(con);
  }
  
  public List<Consumption> fetchConsumptions() {
    return ofy().query(Consumption.class).list();
  }
  
  public List<Consumption> fetchConsumptions(String apartmentName) {
    return ofy().query(Consumption.class).filter("apartmentName =", apartmentName).list();
  }
}
