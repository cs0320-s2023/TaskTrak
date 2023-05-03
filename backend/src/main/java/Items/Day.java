package Items;

import Algorithim.TaskManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.print.attribute.IntegerSyntax;

public class Day {
  private boolean[][] timeSlots;
  private TaskManager tm;


  /**
   * initializes all the time blocks to false (free)
    */
  public Day() {
    this.tm = new TaskManager();
    this.timeSlots = new boolean[24][4]; // 24 rows down, 4 columns across
    for (int i = 0; i < 24; i++) {
      for (int j = 0; j < 4; j++) {
        timeSlots[i][j] = false; // false means the slot is not busy
      }
    }
  }




  /**
   * Determines how long a time block is
   * @param window
   * @return
   */
  public int windowDuration(int[] window){
    if (window == null || window.length != 2) {
      throw new IllegalArgumentException("Window array should have exactly two elements");
    }
    if (window[0] < 0 || window[0] >= 1440 || window[1] < 0 || window[1] >= 1440) {
      throw new IllegalArgumentException("Window values should be between 0 and 1439 (inclusive)");
    }
    int duration = window[1] - window[0];
    if (duration < 0) {
      duration += 1440;
    }
    return duration;
  }


  public TaskManager getTm(){
    return this.tm;
  }


  /**
   * returns a list of integer arrays. Each array within the list represents a free window
   * of time. Each array contains  integers.
   * first integer= start minute, second integer = end minute;
   * Example: if the only free time in a day is from 1am-3:30am and 11am-1:45pm
   * [ [60,210], [660, 825]]
   */

  public List<int[]> findAvailableTimeRanges() {
    ArrayList<int[]> availableRanges = new ArrayList<>();
    boolean isBusy = false;
    int startHour = -1;
    int startBlock = -1;
    for (int hour = 0; hour < 24; hour++) {
      for (int block = 0; block < 4; block++) {
        if (timeSlots[hour][block]) {
          // If the current hour and block is busy
          if (!isBusy) {
            // If we were previously not in a busy block, we just started a busy block
            isBusy = true;
          }
          if (startHour != -1 && startBlock != -1) {
            // If we were previously in an available block, we just ended it
            int endHour = hour;
            int endBlock = block;
            int[] range = {getMinuteOfDay(startHour, startBlock), getMinuteOfDay(endHour,
                endBlock)};
            availableRanges.add(range);
            startHour = -1;
            startBlock = -1;
          }
        } else {
          // If the current hour and block is available
          if (isBusy) {
            // If we were previously in a busy block, we just ended it
            isBusy = false;
            startHour = hour;
            startBlock = block;
          }
          if (startHour == -1 && startBlock == -1) {
            // If we were previously not in an available block, we just started one
            startHour = hour;
            startBlock = block;
          }
        }
      }
    }
    if (startHour != -1 && startBlock != -1) {
      // If we were previously in an available block, we just ended it
      int endHour = 23;
      int endBlock = 3;
      //int[] range = {getMinuteOfDay(startHour, startBlock), getMinuteOfDay(endHour, endBlock)};
      int[] range = {getMinuteOfDay(startHour, startBlock), 1439};
      availableRanges.add(range);
    }
    return availableRanges;
  }


  /**
   * Converts hours and time blocks into minutes of the day
   * @param hour - hour of the day
   * @param block - 15 minute block within the hour
   * @return - integer representing the number
   */
  public int getMinuteOfDay(int hour, int block) {
    return hour * 60 + block * 15;
  }


  /**
   * marks the time within the given range as either true or false
   * true for booked, false for not booked
   * To set the last window of the day, make endHourIndex = 4
   * @param startHourIndex
   * @param startMinuteIndex
   * @param endHourIndex
   * @param endMinuteIndex
   * @param set
   */
  public void bookTimeRange(int startHourIndex, int startMinuteIndex, int endHourIndex,
      int endMinuteIndex, boolean set) {
    // Convert hour and minute indices to slot indices
    if (endHourIndex == 4) {
      timeSlots[23][3] = set;
    }
    int startIndex = (startHourIndex * 4) + startMinuteIndex;
    int endIndex = (endHourIndex * 4) + endMinuteIndex;

    // Iterate over the time slots and set the specified range
    for (int i = startIndex; i < endIndex; i++) {
      int row = i / 4;
      int col = i % 4;
      timeSlots[row][col] = set;
    }
  }


  public boolean[][] getTimeSlots(){
    return this.timeSlots;
  }


}
