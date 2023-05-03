package Items;

public class timeMethods {


  public timeMethods(){};


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

  /**
   * Converts hours and time blocks into minutes of the day
   * @param hour - hour of the day
   * @param block - 15 minute block within the hour
   * @return - integer representing the number
   */
  public int getMinuteOfDay(int hour, int block) {
    return hour * 60 + block * 15;
  }

}
