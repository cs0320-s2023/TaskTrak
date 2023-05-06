package calendarTests;

import static Items.timeMethods.getMinuteOfDay;

import Items.timeMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testTimeMethods {
  @Test



  public void testValidWindowDuration() {
    int[] window = {500, 1000};
    int expectedDuration = 500;
    int actualDuration = timeMethods.windowDuration(window);
    Assertions.assertEquals(expectedDuration, actualDuration);
  }


  @Test
  public void testInvalidWindowArrayLength() {
    int[] window = {500};
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      timeMethods.windowDuration(window);
    });
  }

  @Test
  public void testInvalidWindowValues() {
    int[] window = {-100, 2000};
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      timeMethods.windowDuration(window);
    });
  }


    @Test
    public void testGetMinuteOfDay() {
      int hour = 7;
      int block = 2;
      int expectedMinuteOfDay = 105;
      int actualMinuteOfDay = getMinuteOfDay(hour, block);
      Assertions.assertEquals(expectedMinuteOfDay, actualMinuteOfDay);
    }

  }
