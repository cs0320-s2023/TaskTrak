package Items;

import Enums.Rating;
import java.time.LocalDateTime;
import java.util.Date;

public class Event implements CalendarItem {
  private String name;
  private String notes;
  private Rating priority;
  private double duration;
  private LocalDateTime dueDate; // will need to consider how to manage time
  private Boolean isComplete;


  public Event(String name, String notes, Rating priority,
      double duration, LocalDateTime dueDate, Boolean isComplete) {
    this.name = name;
    this.notes = notes;
    this.priority = priority;
    this.duration = duration;
    this.dueDate = dueDate;
    this.isComplete = isComplete;
  }


  /*
  Getters and Setters
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
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

  @Override
  public Boolean getIsComplete() {
    return this.isComplete;
  }


  @Override
  public void changeCompletion() {
    if (this.isComplete) { // if task is complete;
      this.isComplete = false; // set to incomplete
    } else{ // if task is incomplete
      this.isComplete = true; // // set to complete
    }
  }


}
