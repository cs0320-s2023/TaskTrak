package Algorithim;

import static Items.timeMethods.windowDuration;

import Items.Calendar;
import Items.Day;
import Items.Task;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Items.timeMethods;
public class TaskManager {
  private Map<String, Task> taskMap;

  public TaskManager() {
    this.taskMap = new HashMap<>();
  }


  /**
   * Main step of the algorithim for gathering time suggestions
   * More complexity can be added
   * @param calendar
   */
  public void gatherTimeSuggestions(Calendar calendar) {
    // available time slots
    LocalDate todaysDate = LocalDate.now();
    Day todaysSchedule = calendar.getSchedule(todaysDate); // Day object for the current day
    ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();

    for (Task task : this.taskMap.values()) { // for each user task
      Integer taskMinutes = (int) Math.floorDiv(task.getTimeToComplete().getSeconds(),
          60); //length of task in minutes
      for (int[] freeBlock : todaysFreeTime) {
        int blockDuration = windowDuration(freeBlock); // length of the time block

        // if the time of the block and the task are pretty close
        if (taskMinutes - blockDuration < 30) {
          task.addTimeSuggestion(freeBlock);
        }
      }
      if (task.getTimeSuggestions().size() < 1) { // if there are no time suggestions found
        task.setTimeSuggestion(todaysFreeTime); // add all the available time slots to the
        // suggestions

      }
    }
  }


  /**
   * Adds a task to the task list
   * @param task
   */
  public void addTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }

    if (this.taskMap.containsKey(task.getName())) {
      throw new IllegalArgumentException("Task with name '" + task.getName() + "' already exists");
    }

    this.taskMap.put(task.getName(), task);
  }

  /**
   * removes a task to the task list
   */
  public void removeTask(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    if (!taskMap.containsKey(name)) {
      throw new IllegalArgumentException("Task with name '" + name + "' does not exist");
    }

    this.taskMap.remove(name);
  }


  /**
   * Returns the specific task you are searching for by name
   * @param name
   * @return
   */
  public Task getTask(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    Task task = this.taskMap.get(name);
    if (task == null) {
      throw new IllegalArgumentException("Task with name '" + name + "' does not exist");
    }

    return task;
  }


  /**
   * Returns the entire taskList
   * @return
   */
  public Map<String, Task> getTaskMap() {
    return this.taskMap;
  }








  /**
   * For loop for looping through the task list -- to be modified later
   */
  public void taskLoop(){
    Map<String, Task> tasks = this.getTaskMap();
    for (Map.Entry<String, Task> entry : tasks.entrySet()) {
      String taskName = entry.getKey();
      Task task = entry.getValue();
      // do something with the task
    }
  }


}