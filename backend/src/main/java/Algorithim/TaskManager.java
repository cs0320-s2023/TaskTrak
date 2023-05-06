package Algorithim;

import static Items.timeMethods.windowDuration;

import Items.Calendar;
import Items.Day;
import Items.Task;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
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
  public void timeSuggestionAlgorithim(Calendar calendar) {
    // available time slots
    LocalDate todaysDate = LocalDate.now();
    Day todaysSchedule = calendar.getSchedule(todaysDate); // Day object for the current day
    ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();
    // list of the available windows for today

    // This for loop generates the timeSuggestions for each task
    for (Task task : this.taskMap.values()) { // for each user task
      suggestionHelper(task, todaysFreeTime);
    }
  }


  /**
   * Algorithim helper method that performs the time suggestions for a single Task
   * @param task
   * @param freeList
   */
  public static void suggestionHelper(Task task, ArrayList<int[]> freeList) {

    if (task == null) {
      throw new IllegalArgumentException("Invalid task or freeList");
    }

    ArrayList<int[]> tempList = new ArrayList<>(); // list for temporary storage
    Integer taskMinutes = (int) (task.getTimeToComplete() * 60); //length of task (min)

    for (int[] freeBlock : freeList) { // iterate the free windows
      int blockDuration = windowDuration(freeBlock); // length of the time block

      // if there is a time block of greater or equal length than the task

      if (taskMinutes - blockDuration <= 0) {
        tempList.add(freeBlock); // adds the reasonable time blocks to the temp list
      }
    }

    if (tempList.size() < 1) { // if there are no time suggestions found

      // this will sort all the free time blocks from longest to shortest
      ArrayList<int[]> sortedFreeTime = sortArrayList(freeList);

      ArrayList<LocalTime[]> finalTimeList = new ArrayList<>();

      for (int[] window : sortedFreeTime) {
        finalTimeList.add(convertToTime(window)); // converts the minutes to Time

        task.setTimeSuggestion(finalTimeList); //sets the task windows
      }

    } else { // we have reasonable time slots that work

      // creates windows of the correct length
      ArrayList<int[]> suggestedWindows = produceSuggestions(tempList,
          (int) (task.getTimeToComplete() * 60));

      ArrayList<LocalTime[]> finalTimeList = new ArrayList<>();

      for (int[] window : suggestedWindows) {
        finalTimeList.add(convertToTime(window)); // converts the minutes to Time

        task.setTimeSuggestion(finalTimeList); // sets the time windows


      }
    }
  }

  /**
   * Sorts the time windows in descending order based on the duration of the window
   * @param list
   * @return
   */
  public static ArrayList<int[]> sortArrayList(ArrayList<int[]> list) {
    Collections.sort(list, new Comparator<int[]>() {
      @Override
      public int compare(int[] a, int[] b) {
        int diff1 = Math.abs(a[1] - a[0]);
        int diff2 = Math.abs(b[1] - b[0]);
        return Integer.compare(diff2, diff1);
      }
    });
    return list;
  }


  /**
   * This method finds the final time suggestions based on the duration of the task
   * @param list
   * @param length
   * @return
   */
  public static ArrayList<int[]> produceSuggestions(ArrayList<int[]> list, int length) {
    ArrayList<int[]> ranges = new ArrayList<>();
    for (int[] arr : list) {
      int start = arr[0];
      int end = arr[1];
      int rangeCount = (end - start) / length;
      for (int i = 0; i < rangeCount; i++) {
        int rangeStart = start + i * length;
        int rangeEnd = rangeStart + length;
        ranges.add(new int[]{rangeStart, rangeEnd});
      }
    }
    return ranges;
  }


  /**
   * Converts an int[] to a LocalTime[]
   * @param minutes
   * @return
   */
  public static LocalTime[] convertToTime(int[] minutes) {
    LocalTime start = LocalTime.ofSecondOfDay(minutes[0] * 60);
    LocalTime end = LocalTime.ofSecondOfDay(minutes[1] * 60);
    return new LocalTime[] { start, end };
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





}