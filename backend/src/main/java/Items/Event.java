package Items;

import Enums.Rating;
import java.time.Duration;
import java.time.LocalDateTime;

public class Event implements CalendarItem {


  private String name;
  private String notes;
  private LocalDateTime endTime;
  private LocalDateTime startTime; // will need to consider how to manage time
  private Boolean isComplete;
  private int id;
  private boolean isAllDay;
  //private boolean isRepeated;




  public Event(String name, String notes,
      LocalDateTime startTime, LocalDateTime endTime, int id, boolean isAllDay) {
    this.name = name;
    this.notes = notes;
    this.endTime = endTime;
    this.startTime = startTime;
    this.isComplete = false;
    this.id = id;
    this.isAllDay = isAllDay;
  }


  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }


  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }


  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public LocalDateTime getEndTime() {
    return this.endTime;
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

  public Integer getId(){return this.id;}

  public boolean getIsAllDay() {return this.isAllDay;}

  @Override
  public void changeCompletion() {
    if (this.isComplete) { // if task is complete;
      this.isComplete = false; // set to incomplete
    } else{ // if task is incomplete
      this.isComplete = true; // // set to complete
    }
  }


}
