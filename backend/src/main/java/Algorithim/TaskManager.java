package Algorithim;

import Items.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
  private Map<String, Task> taskMap;

  public TaskManager() {
    taskMap = new HashMap<>();
  }

  /**
   * Adds a task to the task list
   * @param task
   */
  public void addTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }

    if (taskMap.containsKey(task.getName())) {
      throw new IllegalArgumentException("Task with name '" + task.getName() + "' already exists");
    }

    taskMap.put(task.getName(), task);
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

    taskMap.remove(name);
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

    Task task = taskMap.get(name);
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
    return taskMap;
  }



  public List<Task> sortTasks() {
    LocalDateTime now = LocalDateTime.now();
    List<Task> sortedTasks = new ArrayList<>(taskMap.values());
    sortedTasks.sort((task1, task2) -> {
      LocalDateTime dueDate1 = task1.getDueDate();
      LocalDateTime dueDate2 = task2.getDueDate();
      int compareByDueDate = dueDate1.compareTo(dueDate2);
      if (compareByDueDate == 0) {
        int compareByPriority = Integer.compare(task2.getPriority().getValue(), task1.getPriority().getValue());
        if (compareByPriority == 0) {
          return 0;
        }
        return compareByPriority;
      }
      else if (dueDate1.isBefore(now) && dueDate2.isAfter(now)) {
        return 1;
      }
      else if (dueDate1.isAfter(now) && dueDate2.isBefore(now)) {
        return -1;
      }
      else {
        return dueDate1.compareTo(dueDate2);
      }
    });
    return sortedTasks;
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