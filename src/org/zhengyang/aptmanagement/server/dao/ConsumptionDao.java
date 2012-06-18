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
  
  /**
   * Return consumptions given department name, order by date.
   */
  public List<Consumption> fetchConsumptions(String apartmentName) {
    return ofy().query(Consumption.class).filter("apartmentName =", apartmentName).order("-date").list();
  }
}
