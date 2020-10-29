package com.arc.cardemo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Owner {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long ownerid;
  private String firstname, lastname;
  
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "car_owner", joinColumns = { @JoinColumn(name =
   "ownerid") }, inverseJoinColumns = { @JoinColumn(name = "id") }) 
  private Set<Car> cars = new HashSet<Car>(0); 

  public Set<Car> getCars() {
    return cars;
  }

  public void setCars(Set<Car> cars) {
    this.cars = cars;
  }
  public Owner() {}

  public Owner(String firstname, String lastname) {
    super();
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public long getOwnerid() {
    return ownerid;
  }
  public void setOwnerid(long ownerid) {
    this.ownerid = ownerid;
  }
  public String getFirstname() {
    return firstname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public String getLastname() {
    return lastname;
  }
  public void setLastname(String lastname) {
    this.lastname = lastname;
  } 
}