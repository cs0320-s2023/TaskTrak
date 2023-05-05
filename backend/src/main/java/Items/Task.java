package Items;

import Enums.Rating;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task implements CalendarItem{
  private String name;
  private String notes;
  private Rating priority; //
  private Double timeToComplete;
  private LocalDateTime dueDate; // will need to consider how to manage time
  private Boolean isComplete;
  private ArrayList<Time[]> timeSuggestions;

  // task constructor

  public Task(String name, String notes, Rating priority,
      Double timeToComplete, LocalDateTime dueDate, Boolean isComplete) {
    this.name = name;
    this.notes = notes;
    this.priority = priority;
    this.timeToComplete = timeToComplete;
    this.dueDate = dueDate;
    this.isComplete = false;
    this.timeSuggestions = new ArrayList<>();
  }



  /**
   * Converts a Task Object into an Event object to be added to the calendar
   * @return - the new Event object
   */
//  public Event taskToEvent(LocalDateTime startTime, Duration duration){
//    String eventName = this.name;
//    String eventNote = this.notes;
//
//    // Created defensive copies of startTime and duration
//    LocalDateTime startTimeCopy = LocalDateTime.from(startTime);
//    Duration durationCopy = Duration.ofMillis(duration.toMillis());
//
//    Event newEvent = new Event(eventName, eventNote, durationCopy, startTimeCopy);
//
//    return newEvent;
//  }




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


  /**
   * Adds a time block to the time suggestions list
   * @param timeBlock - an integer array representing a time suggestion
   */
  public void addTimeSuggestion(int[] timeBlock) {
    this.getTimeSuggestions().add(timeBlock);
  }

  public void setTimeSuggestion(ArrayList<Time[]> newList) {
    this.timeSuggestions = newList;
  }


  public ArrayList<int[]> getTimeSuggestions(){
    return this.getTimeSuggestions();
  }

  public Boolean getIsComplete(){
    return this.isComplete;
  }

  public void setTimeToComplete(Double timeToComplete) {
    this.timeToComplete = timeToComplete;
  }


  public Double getTimeToComplete() {
    return timeToComplete;
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
