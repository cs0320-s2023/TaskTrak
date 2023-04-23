package Items;

import Enums.Rating;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Event implements CalendarItem {
  private String name;
  private String notes;
  private Duration duration;
  private LocalDateTime startTime; // will need to consider how to manage time
  private Boolean isComplete;


  public Event(String name, String notes,
      Duration duration, LocalDateTime startTime, Boolean isComplete) {
    this.name = name;
    this.notes = notes;
    this.duration = duration;
    this.startTime = startTime;
    this.isComplete = isComplete;
  }


  /*
  Getters and Setters
   */

  public void setDuration(int hours, int minutes) {
    this.duration = Duration.ofHours(hours).plus(minutes);
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
