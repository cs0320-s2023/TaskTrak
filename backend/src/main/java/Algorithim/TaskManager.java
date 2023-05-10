package Algorithim;

import static Items.timeMethods.windowDuration;

import Items.Calendar;
import Items.Day;
import Items.Task;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TaskManager {
  private Map<Integer, Task> taskMap;

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

  public Map<Integer, ArrayList<List<LocalTime>>> getAllTimeSuggestions() {
    Map<Integer, ArrayList<List<LocalTime>>> updatedTimeSuggestions = new HashMap<>();

    for (Integer key : this.getTaskMap().keySet()) {
      ArrayList<List<LocalTime>> updatedTaskSuggestions = // updated task suggestions
          this.getTask(key).getTimeSuggestions();

      // maps the task ID to the new time suggestions
      updatedTimeSuggestions.put(key, updatedTaskSuggestions);
    }
    return updatedTimeSuggestions;
  }


  /**
   * Algorithim helper method that performs the time suggestions for a single Task
   * @param task
   * @param freeList
   */
  public static void suggestionHelper(Task task, ArrayList<int[]> freeList) {

    if (task == null) {
      throw new IllegalArgumentException("Invalid task object");
    }

    if (task.getTimeToComplete().equals(0)){
      task.setTimeSuggestion(new ArrayList<>());
      return;
    }

    ArrayList<int[]> tempList = new ArrayList<>(); // list for temporary storage
    Integer taskMinutes = (int) (task.getTimeToComplete() * 60); //length of task (min)

    for (int[] freeBlock : freeList) { // iterate the free windows
      int blockDuration = windowDuration(freeBlock); // length of the time block

      // if there is a time block of greater or equal length than the task
      if (taskMinutes - blockDuration <= -15) {
        tempList.add(freeBlock); // adds the reasonable time blocks to the temp list
      }
    }

    if (tempList.size() < 1) { // if there are no time suggestions found

      // this will sort all the free time blocks from longest to shortest
      ArrayList<int[]> sortedFreeTime = sortArrayList(freeList);

      ArrayList<List<LocalTime>> finalTimeList = new ArrayList<>();

      for (int[] window : sortedFreeTime) {

        finalTimeList.add(convertToTime(window)); // converts the minutes to Time

        task.setTimeSuggestion(finalTimeList); //sets the task windows
      }

    } else { // we have > 1 time slots that work

      // creates windows of the correct length
      ArrayList<int[]> suggestedWindows = produceSuggestions(tempList,
          (int) (task.getTimeToComplete() * 60));

      ArrayList<List<LocalTime>> finalTimeList = new ArrayList<>();

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
  public static List<LocalTime> convertToTime(int[] minutes) {
    LocalTime start = LocalTime.ofSecondOfDay(minutes[0] * 60);
    LocalTime end = LocalTime.ofSecondOfDay(minutes[1] * 60);
    return List.of(start, end );
  }

  /**
   * Adds a task to the task list, checks if task already exists
   * @param task
   */
  public void addTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }

    if (this.taskMap.containsKey(task.getTaskID())) {
      throw new IllegalArgumentException("Task with ID '" + task.getTaskID() + "' already exists");
    }

    this.taskMap.put(task.getTaskID(), task);
  }

  /**
   * removes a task to the task list
   */
  public void removeTask(int taskID) {


    if (!taskMap.containsKey(taskID)) {
      throw new IllegalArgumentException("Task with ID '" + taskID + "' does not exist");
    }

    this.taskMap.remove(taskID);
  }


  /**
   * Returns the specific task you are searching for by name
   * @param id
   * @return
   */
  public Task getTask(int id) {


    Task task = this.taskMap.get(id);
    if (task == null) {
      throw new IllegalArgumentException("Task with id '" + String.valueOf(id) + "' does not "
          + "exist");
    }

    return task;
  }


  /**
   * Returns the entire taskList
   * @return
   */
  public Map<Integer, Task> getTaskMap() {
    return this.taskMap;
  }





}