package handlers;

import Algorithim.TaskManager;
import Items.Calendar;
import java.util.Map;

public class UserState {
  private final Map<String,Calendar> calendarMap;
  private final Map<String, TaskManager> taskManagerMap;

  public UserState(Map<String,Calendar> calendarMap, Map<String, TaskManager> taskManagerMap) {
    this.calendarMap = calendarMap;
    this.taskManagerMap = taskManagerMap;
  }

  public Calendar getUserCalendar(String userID) throws NullPointerException{
    if (this.calendarMap.containsKey(userID)) {
      return this.calendarMap.get(userID);
    } else {
      throw new NullPointerException("UserID not found when getting calendar");
    }
  }

  public TaskManager getUserTaskManager(String userID) throws NullPointerException{
    if (this.taskManagerMap.containsKey(userID)) {
      return this.taskManagerMap.get(userID);
    } else {
      throw new NullPointerException("UserID not found when getting task manager");
    }
  }

}