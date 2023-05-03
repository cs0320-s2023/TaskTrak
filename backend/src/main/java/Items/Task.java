package Items;

import Enums.Rating;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task implements CalendarItem{
  private String name;
  private String notes;
  private Rating priority; //
  private Rating dread;
  private Duration timeToComplete;
  private LocalDateTime dueDate; // will need to consider how to manage time
  private Boolean isComplete;
  private ArrayList<int[]> timeSuggestions;

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
    this.timeSuggestions = new ArrayList<>();
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

  /**
   * This method gathers all the tasks within the task list that have a high dread rating
   * into one list
   * @param taskList -- The overall list of a users tasks
   * @return -- dreadfulTasks = list of tasks with high dread
   */
  public List<Task> gatherDread(List<Task> taskList) {
    List<Task> dreadfulTasks = new ArrayList<>();
    for (Task task : taskList) {
      if(task.getDread() == Rating.HIGH){
        dreadfulTasks.add(task);
      }
    }
    return dreadfulTasks;
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


  /**
   * Adds a time block to the time suggestions list
   * @param timeBlock - an integer array representing a time suggestion
   */
  public void setTimeSuggestion(int[] timeBlock) {
    this.getTimeSuggestions().add(timeBlock);
  }


  public ArrayList<int[]> getTimeSuggestions(){
    return this.getTimeSuggestions();
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
