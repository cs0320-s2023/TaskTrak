package Items;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.IntegerSyntax;

public class Day {
  private boolean[][] timeSlots;

  public Day() {
    this.timeSlots = new boolean[24][60 / 15]; // 24 rows down, 4 columns across
    // Initialize all time slots as available (true)
    for (int i = 0; i < 24; i++) {
      for (int j = 0; j < 60 / 15; j++) {
        timeSlots[i][j] = true;
      }
    }
  }


  /**
   * This returns a list of integer arrays. Each array within the list represents a free window
   * of time. Each array contains 4 integers.
   * first integer= start hour, second integer = start 15-min window, third integer = end hour,
   * fourth integer= end 15-min window
   * Example: if the only free time in a day is from 1am-3:30am and 11am-1:45pm
   * [ [1,0,3,2], [11,0,13,3] ]
   * @return
   */
  public List<int[]> checkAvailability() {
    List<int[]> availableSlots = new ArrayList<>();
    int startHour = -1, endHour = -1, startMinute = -1, endMinute = -1;
    for (int i = 0; i < timeSlots.length; i++) {
      for (int j = 0; j < timeSlots[i].length; j++) {
        if (timeSlots[i][j]) {
          if (startHour == -1) {  // Start of a new available slot
            startHour = i;
            startMinute = j * 15;
          }
          endHour = i;
          endMinute = j * 15 + 15;
        } else if (startHour != -1) {  // End of an available slot
          availableSlots.add(new int[]{startHour, startMinute, endHour, endMinute});
          startHour = -1;
          endHour = -1;
          startMinute = -1;
          endMinute = -1;
        }
      }
    }
    // If the last time slot was available, add it to the list as well
    if (startHour != -1) {
      availableSlots.add(new int[]{startHour, startMinute, endHour, endMinute});
    }
    return availableSlots;
  }


}
