package Items;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class timeMethods {


  public timeMethods(){};



  public void checkDayChange() {
    java.util.Calendar cal = Calendar.getInstance();
    int previousDay = cal.get(Calendar.DAY_OF_MONTH);

    while (true) {
      cal = Calendar.getInstance();
      int currentDay = cal.get(Calendar.DAY_OF_MONTH);

      if (currentDay != previousDay) {
        // call your functions here
        System.out.println("The day has changed!");

        // update previous day
        previousDay = currentDay;
      }

      // sleep for 1 minute
      try {
        Thread.sleep(60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Determines how long a time block is
   * @param window
   * @return
   */
  public static int windowDuration(int[] window){
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

  /**
   * Converts hours and time blocks into minutes of the day
   * @param hour - hour of the day
   * @param block - 15 minute block within the hour
   * @return - integer representing the number
   */
  public static int getMinuteOfDay(int hour, int block) {
    return hour * 60 + block * 15;
  }

}
