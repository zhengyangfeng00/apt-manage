package org.zhengyang.aptmanagement.shared.dto;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Id;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Note that there cannot be two residents with same name in one apartment.
 * 
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 9, 2012
 */
public class Apartment extends Equality implements IsSerializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  public String name;
  public String address;
  // String format: name:username
  public ArrayList<String> adminMap = Lists.newArrayList();
  // <name, username>
  public ArrayList<String> residentsMap = Lists.newArrayList();
  // <name, username>
  public ArrayList<String> guestsMap = Lists.newArrayList();
  
  public Apartment() { }
  
  public Apartment(String name, String address, ArrayList<String> adminMap) {
    this.name = name;
    this.address = address;
    this.adminMap = adminMap;
  }
  
  public Apartment(String name, String address) {
    this.name = name;
    this.address = address;
    this.adminMap = Lists.newArrayList();
  }
  
  @Override
  public Object getId() {
    return Arrays.asList(name, address, adminMap, residentsMap, guestsMap);
  }
}
