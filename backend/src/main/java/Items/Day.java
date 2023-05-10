package Items;

import Algorithim.TaskManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.print.attribute.IntegerSyntax;

public class Day {
  private int[][] timeSlots;
  private TaskManager tm;


  /**
   * initializes all the time blocks to 0 (free)
    */
  public Day() {
    this.tm = new TaskManager();
    this.timeSlots = new int[24][4]; // 24 rows down, 4 columns across
    for (int i = 7; i < 24; i++) {
      for (int j = 0; j < 4; j++) {
        timeSlots[i][j] = 0; // false means the slot is not busy (for working hours)
      }
    }
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 4; j++) {
        timeSlots[i][j] = 1;
      }
    }
  }










  /**
   * returns a list of integer arrays. Each array within the list represents a free window
   * of time. Each array contains  integers.
   * first integer= start minute, second integer = end minute;
   * Example: if the only free time in a day is from 1am-3:30am and 11am-1:45pm
   * [ [60,210], [660, 825]]
   */
  public ArrayList<int[]> findAvailableTimeRanges() {
    ArrayList<int[]> availableRanges = new ArrayList<>();
    boolean isBusy = false;
    int startHour = -1;
    int startBlock = -1;
    for (int hour = 0; hour < 24; hour++) {
      for (int block = 0; block < 4; block++) {
        if (hour >= 0 && hour < timeSlots.length && block >= 0) {
          if (timeSlots[hour][block] == 0) {
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
          } else {
            // If the current hour and block is busy
            if (!isBusy) {
              // If we were previously not in a busy block, we just started a busy block
              isBusy = true;
            }
            if (startHour != -1 && startBlock != -1) {
              // If we were previously in an available block, we just ended it
              int endHour = hour;
              int endBlock = block;
              int[] range = { getMinuteOfDay(startHour, startBlock), getMinuteOfDay(endHour, endBlock) };
              availableRanges.add(range);
              startHour = -1;
              startBlock = -1;
            }
          }
        } else {
          System.err.println("Invalid array access at hour: " + hour + ", block: " + block);
        }
      }
    }
    if (startHour != -1 && startBlock != -1) {
      // If we were previously in an available block, we just ended it
      int endHour = 23;
      int endBlock = 3;
      // int[] range = {getMinuteOfDay(startHour, startBlock), getMinuteOfDay(endHour, endBlock)};
      int[] range = { getMinuteOfDay(startHour, startBlock), 1439 };
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
    if (hour < 0 || hour > 23) {
      throw new IllegalArgumentException("Invalid hour value. Hour should be between 0 and 23.");
    }

    if (block < 0 || block > 3) {
      throw new IllegalArgumentException("Invalid block value. Block should be between 0 and 3.");
    }

    return hour * 60 + block * 15;
  }


  /**
   * Sets the time blocks over a given range
   * 'set' is the determination of whether we are making the block free or busy
   * true if making busy, false if making free
   * This will iterate the time block integer value either up or down 1.
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
    // this is to mark off the final slot in the day
    if (startHourIndex < 0 || startHourIndex > 23) {
      throw new IllegalArgumentException("Invalid startIndex value. It should be between 0 and 23"
          + ".");
    }

    if (startMinuteIndex < 0 || startMinuteIndex > 4) {
      throw new IllegalArgumentException("Invalid startIndex value. It should be between 0 and 3."
          + "4 is valid trying to fill the 11:45 to 11:59 range for the day");
    }


    // Validate endHourIndex and endMinuteIndex
    if (endHourIndex < 0 || endHourIndex > 23) {
      throw new IllegalArgumentException(
          "Invalid endHourIndex value. It should be between 0 and 23.");
    }

    if (endMinuteIndex < 0 || endMinuteIndex > 4) {
      throw new IllegalArgumentException("Invalid endMinuteIndex value. It should be between 0 "
          + "and 3. 4 is valid trying to fill the 11:45 to 11:59 range for the day.");
    }

    // Check if start time is after end time
    if (startHourIndex > endHourIndex || (startHourIndex == endHourIndex
        && startMinuteIndex >= endMinuteIndex)) {
      throw new IllegalArgumentException(
          "Invalid time range. The start time must be before the end time.");
    }

      if (endMinuteIndex == 4) {
        if (set) { // if are marking the block as busy, iterate number up
          timeSlots[23][3]++;
        } else {
          timeSlots[23][3]--; // if are marking the block as free, iterate number down
        }
      }
      int startIndex = (startHourIndex * 4) + startMinuteIndex;
      int endIndex = (endHourIndex * 4) + endMinuteIndex;

      // Iterate over the time slots and set the specified range
      for (int i = startIndex; i < endIndex; i++) {
        int row = i / 4;
        int col = i % 4;
        if (set) { // if are marking the block as busy, iterate number up
          timeSlots[row][col]++;
        } else {
          timeSlots[row][col]--; // if are marking the block as free, iterate number down
        }
      }
  }


  /**
   * gets the timeSlots array
   * @return int[][] of timeSlots
   */
  public int[][] getTimeSlots(){
    return this.timeSlots;
  }

  public TaskManager getTm(){
    return this.tm;
  }


  //  /**
//   * Determines how long a time block is
//   * @param window
//   * @return
//   */
//  public int windowDuration(int[] window){
//    if (window == null || window.length != 2) {
//      throw new IllegalArgumentException("Window array should have exactly two elements");
//    }
//    if (window[0] < 0 || window[0] >= 1440 || window[1] < 0 || window[1] >= 1440 || window[1] - window[0] >= 0) {
//      throw new IllegalArgumentException("Window values should be between 0 and 1439 (inclusive),"
//          + " and the second value should be greater than the first");
//    }
//    int duration = window[1] - window[0];
//    if (duration < 0) {
//      duration += 1440;
//    }
//    return duration;
//  }
}
