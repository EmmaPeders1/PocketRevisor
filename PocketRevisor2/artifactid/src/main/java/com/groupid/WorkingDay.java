package com.groupid;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkingDay {

  LocalDate date;
  LocalTime startTime, endTime;
  double wage;

  public WorkingDay(LocalDate date, LocalTime startTime, LocalTime endTime, double wage) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.wage = wage;
  }
}
