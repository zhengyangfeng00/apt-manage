package org.zhengyang.aptmanagement.shared.dto;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
public class Consumption extends Equality implements IsSerializable {
  private static final long serialVersionUID = 1L;
  @Id
  public Long id;
  public String name;
  public Date date;
  public double amount;
  public String note;
  public String paidByUsername;
  public String paidByUser;
  public String type;
  public String apartmentName;
  public String[] sharedByUsers;
  
  public Consumption() {
  }
  
  public Consumption(String name, Date date, double amount, String note, String paidByUsername, String paidByUser, String type, String apartmentName, String[] sharedByUsers) {
    this.name = name;
    this.date = date;
    this.amount = amount;
    this.note = note;
    this.paidByUsername = paidByUsername;
    this.paidByUser = paidByUser;
    this.type = type;
    this.apartmentName = apartmentName;
    this.sharedByUsers = sharedByUsers;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object getId() {
    return Arrays.asList(id, amount, note, paidByUsername, paidByUser, type, apartmentName);
  }
}
