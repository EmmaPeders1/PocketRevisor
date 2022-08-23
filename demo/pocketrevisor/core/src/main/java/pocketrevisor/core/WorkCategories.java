package com.project;

public class WorkCategories {

  private double hourly_wage;

  public WorkCategories(double hourly_wage){
    this.hourly_wage = hourly_wage;
  }
  
  public void setHourlyWage(double hourly_wage){
    this.hourly_wage = hourly_wage;
  }

  private double getHourlyWage(){
    return hourly_wage;
  }
}
