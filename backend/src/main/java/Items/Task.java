package Items;

import Enums.Rating;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Task implements CalendarItem{
  private String name;
  private String notes;
  private Rating priority; //
  private Rating dread;
  private Duration timeToComplete;
  private LocalDateTime dueDate; // will need to consider how to manage time
  private Boolean isComplete;

  // task constructor

  public Task(String name, String notes, Rating priority, Rating dread,
      Duration timeToComplete, LocalDateTime dueDate, Boolean isComplete) {
    this.name = name;
    this.notes = notes;
    this.priority = priority;
    this.dread = dread;
    this.timeToComplete = timeToComplete;
    this.dueDate = dueDate;
    this.isComplete = false;
  }


  /**
   * Converts a Task Object into an Event object to be added to the calendar
   * @return - the new Event object
   */
  public Event taskToEvent(LocalDateTime startTime, Duration duration){
    String eventName = this.name;
    String eventNote = this.notes;

    // Created defensive copies of startTime and duration
    LocalDateTime startTimeCopy = LocalDateTime.from(startTime);
    Duration durationCopy = Duration.ofMillis(duration.toMillis());

    Event newEvent = new Event(eventName, eventNote, durationCopy, startTimeCopy);

    return newEvent;
  }




  //---------------------------------

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

  public Boolean getIsComplete(){
    return this.isComplete;
  }

  public void setTimeToComplete(Duration timeToComplete) {
    this.timeToComplete = timeToComplete;
  }


  public Duration getTimeToComplete() {
    return timeToComplete;
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

  public void setDueDate(LocalDateTime date) {
    this.dueDate = date;
  }

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
