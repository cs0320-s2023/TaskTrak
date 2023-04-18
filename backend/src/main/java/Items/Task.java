package Items;

import Enums.Rating;
import java.time.LocalDateTime;
import java.util.Date;

public class Task implements CalendarItem{
  private String name;
  private String notes;
  private Rating priority; //
  private Rating dread;
  private double hoursToComplete;
  private LocalDateTime dueDate; // will need to consider how to manage time
  private Boolean isComplete;

  // task constructor

  public Task(String name, String notes, Rating priority, Rating dread,
      double hoursToComplete, LocalDateTime dueDate, Boolean isComplete) {
    this.name = name;
    this.notes = notes;
    this.priority = priority;
    this.dread = dread;
    this.hoursToComplete = hoursToComplete;
    this.dueDate = dueDate;
    this.isComplete = false;
  }





  /*
  Setters and Getters
   */
  /**
   * Changes the state of completion
   * If complete, changes to incomplete and vice versa
   */
  public void changeCompletion(){
    if (this.isComplete) { // if task is complete;
      this.isComplete = false; // set to incomplete
    } else{ // if task is incomplete
      this.isComplete = true; // // set to complete
    }
  }

  @Override
  public Boolean getIsComplete(){
    return this.isComplete;
  }


  public void setHoursToComplete(double hoursToComplete) {
    this.hoursToComplete = hoursToComplete;
  }


  public double getHoursToComplete() {
    return hoursToComplete;
  }


  public void setDread(Rating dread) {
    this.dread = dread;
  }

  public Rating getDread(){
    return this.dread;
  }

  public void setPriority(Rating priority) {
    this.priority = priority;
  }

  public Rating getPriority() {
    return this.priority;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setDueDate(LocalDateTime date) {
    this.dueDate = date;
  }

  @Override
  public LocalDateTime getDueDate() {
    return this.dueDate;
  }

  @Override
  public void setNotes(String notes) {
    this.notes = notes;

  }

  @Override
  public String getNotes() {
    return this.notes;
  }



}
