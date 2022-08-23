package com.groupid;

import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.*;
import java.util.HashSet;

public class Wages {

  private double hourlyWage = 166.46;

	private int weekdayAfter18 = 22;
	//private int weekdayAfter21 = 45;
	private int saturdayAfter13 = 45;
	private int saturdayAfter15 = 55;
	//private int saturdayAfter18 = 110;
	//private int sunday = 110;

  private LocalTime clockIs13 = LocalTime.of(13, 00);
  private LocalTime clockIs15 = LocalTime.of(15, 00);
  private LocalTime clockIs18 = LocalTime.of(18, 00);


  private HashSet<WorkingDay> set = new HashSet<>();

  private double dailyWage = 0;

  public LocalTime getTime(String startTime){
    String hourString = startTime.substring(0,2);
    String minString = startTime.substring(3);
    int hour = 0;
    int min = 0;
    /*if(hourString.matches("[0-9]+") || minString.matches("[0-9]+")){
      return null;
    }
    else{*/
      hour = Integer.parseInt(hourString);
      min = Integer.parseInt(minString);
      if(hour >= 24 || min >= 60){
        return null;
     // }
    }
    return LocalTime.of(hour, min);
  }

  private double getShiftLength(LocalTime startTime, LocalTime endTime){
    //How long is the shift
    long shiftLength = ChronoUnit.MINUTES.between(startTime, endTime);
    return (double)shiftLength;
  }

  private double checkForBreak(double shiftLength, boolean isSaturday){
    //Is the shift longer than 5 hours or is it a saturday? If so, remove 30 minute break
    if(shiftLength >= 300 || isSaturday){
      shiftLength -= 30;
    }
    return shiftLength;
  }

  public double addToTotal(double total, double wage){
    return total += wage;
  }

  public void writeToSaveFile(LocalDate date, LocalTime startTime, LocalTime endTime){
    SaveHandler saveHandler = new SaveHandler();
    String path = String.valueOf(Paths.get("artifactid", "src", "main", "java", "com", "groupid", "savefile.txt"));
    String textFromFile = saveHandler.readFromFile(path);
    saveHandler.writeToFile(textFromFile + "\n" + date + " " + startTime + " " +  endTime);
  }

  public HashSet<WorkingDay> calculateWage(LocalDate date, LocalTime startTime, LocalTime endTime){
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    //Check if weekday or not
    if(dayOfWeek.getValue() < 6){

      //if both startTime and endTime is before 18 on a weekday
      if(startTime.isBefore(clockIs18) && endTime.isBefore(clockIs18)){
        double shiftLength = getShiftLength(startTime, endTime);
        shiftLength = checkForBreak(shiftLength, false);
        dailyWage = (shiftLength/60) * hourlyWage;
      }

      //if endTime is after 18 on a weekday
      else if(startTime.isBefore(clockIs18) && endTime.isAfter(clockIs18)){
        double before18 = getShiftLength(startTime, clockIs18);
        before18 = checkForBreak(before18, false);
        double after18 = getShiftLength(clockIs18, endTime);
        dailyWage = (before18/60) * hourlyWage + (after18/60) * (hourlyWage + weekdayAfter18);
      }
    }
    else{
      //if startTime is before 13 and endTime is 18 or before
      if(startTime.isBefore(clockIs13) && endTime.isAfter(clockIs15)){
        double before13 = getShiftLength(startTime, clockIs13);
        double before15 = getShiftLength(clockIs13, clockIs15);
        //Assume the break is between 13 and 15
        before15 = checkForBreak(before15, true);
        double before18 = getShiftLength(clockIs15, endTime);
        dailyWage = (before13/60) * (hourlyWage) + ((before15)/60) * (hourlyWage + saturdayAfter13) + (before18/60) * (hourlyWage + saturdayAfter15);
      }
    }
    set.add(new WorkingDay(date, startTime, endTime, dailyWage));
    return set;
  }
}