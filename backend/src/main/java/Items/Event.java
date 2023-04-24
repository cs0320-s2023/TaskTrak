package Items;

import Enums.Rating;
import java.time.Duration;
import java.time.LocalDateTime;

public class Event implements CalendarItem {
  private String name;
  private String notes;
  private Duration duration;
  private LocalDateTime startTime; // will need to consider how to manage time
  //private Boolean isComplete;


  public Event(String name, String notes,
      Duration duration, LocalDateTime startTime) {
    this.name = name;
    this.notes = notes;
    this.duration = duration;
    this.startTime = startTime;
    //this.isComplete = isComplete;
  }


  /**
   * Converts an Event Object into an task object to be added to the calendar
   * @return - the new Event object
   */
  public Task eventToTask(Rating priority, Rating dread,
      Duration timeToComplete, LocalDateTime dueDate){

    String taskName = this.name != null ? new String(this.name) : "";
    String taskNotes = this.notes != null ? new String(this.notes) : "";
    Rating priorityCopy = priority != null ? priority : Rating.MEDIUM;
    Rating dreadCopy = dread != null ? dread : Rating.MEDIUM;

    Task newTask = new Task(taskName, taskNotes, priorityCopy, dreadCopy,
        timeToComplete, dueDate, false);
    return newTask;
  }



  /*
  Getters and Setters
   */

  public void setDuration(int hours, int minutes) {
    this.duration = Duration.ofHours(hours).plusMinutes(minutes);
  }

  public Duration getDuration() {
    return this.duration;
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

//  @Override
//  public Boolean getIsComplete() {
//    return this.isComplete;
//  }
//
//
//  @Override
//  public void changeCompletion() {
//    if (this.isComplete) { // if task is complete;
//      this.isComplete = false; // set to incomplete
//    } else{ // if task is incomplete
//      this.isComplete = true; // // set to complete
//    }
//  }


}
