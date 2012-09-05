package org.zhengyang.aptmanagement.shared.dto;

import java.util.Arrays;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * One user can only be in one apartment.
 * 
 * @author zhengyang.feng2011@gmail.com (Zhengyang Feng)
 * @creation May 7, 2012
 */
public class User extends Equality implements IsSerializable {
  private static final long serialVersionUID = 1L;
  @Id
  public String username;
  public String password;
  public String name;
  public double balance;
  public String email;
  public Role role;
  public String apartmentName;

  public User() {
  }

  public User(String username, String name, double balance, String email, Role role, String apartmentName) {
    this.username = username;
    this.name = name;
    this.balance = balance;
    this.email = email;
    this.role = role;
    this.apartmentName = apartmentName;
  }
  
  public User(String username, String password, String name, String email, String apartmentName) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.balance = 0.0;
    this.email = email;
    this.role = Role.ADMIN;
    this.apartmentName = apartmentName;
  }
  
  public void setApartmentname(String apartmentName) {
    this.apartmentName = apartmentName;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object getId() {
    return Arrays.asList(username, name, balance, email, role, apartmentName);
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", balance=" + balance + ", apartmentName=" + apartmentName
        + ", username=" + username + ", password=" + password + ", email=" + email + ", role="
        + role + "]";
  }
}
