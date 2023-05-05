package calendarTests;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Items.Day;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testDay {
    private Day day;

  @BeforeEach
  public void setUp() {
    day = new Day();
  }


  @Test
  void testEmptyAvailability() {
    List<int[]> slots = day.findAvailableTimeRanges();
    assertEquals(1, slots.size());
    assertEquals(0, slots.get(0)[0]);
    assertEquals(1439, slots.get(0)[1]);

  }


  @Test
  void testBookTimeRange(){
    assertFalse(day.getTimeSlots()[0][0]);
    assertFalse(day.getTimeSlots()[23][3]);
    day.bookTimeRange(0,0, 1, 0, true);
    assertTrue( day.getTimeSlots()[0][0]);
    assertTrue(day.getTimeSlots()[0][1]);
    assertTrue(day.getTimeSlots()[0][2]);
    assertTrue(day.getTimeSlots()[0][3]);
    assertFalse(day.getTimeSlots()[1][0]);
    day.bookTimeRange(0,0, 0, 1, true);
    assertTrue(day.getTimeSlots()[0][0]);
    day.bookTimeRange(23,1, 23, 3, true);
    assertTrue(day.getTimeSlots()[23][1]);
    assertTrue(day.getTimeSlots()[23][2]);
    assertFalse(day.getTimeSlots()[23][0]);
    assertFalse(day.getTimeSlots()[23][3]);

  }



  @Test
  void testSingleSlot() {
    day.bookTimeRange(8, 0, 9, 2, true);
    List<int[]> slots = day.findAvailableTimeRanges();
    assertEquals(2, slots.size());
    assertEquals(0, slots.get(0)[0]);
    assertEquals(480, slots.get(0)[1]);
    assertEquals(570, slots.get(1)[0]);
    assertEquals(1439, slots.get(1)[1]);
    assertEquals(2, slots.size());
    assertEquals(0, slots.get(0)[0]);
    assertEquals(day.getMinuteOfDay(8,0), slots.get(0)[1]);
    assertEquals(day.getMinuteOfDay(9,2), slots.get(1)[0]);
  }


  @Test
  public void testFindAvailableTimeRangesAllBooked() {
    day.bookTimeRange(0, 0, 23, 4, true);
    List<int[]> ranges = day.findAvailableTimeRanges();
    assertTrue(ranges.isEmpty());
    assertTrue(ranges.isEmpty());
  }

  @Test
  public void testFindAvailableTimeRangesMultipleRanges() {
    day.bookTimeRange(0, 0, 0, 2, true);
    day.bookTimeRange(1, 0, 2, 2, true);
    day.bookTimeRange(3, 0, 3, 3, true);
    List<int[]> ranges = day.findAvailableTimeRanges();
    assertEquals(3, ranges.size());
    assertArrayEquals(new int[]{30,60}, ranges.get(0));
    assertArrayEquals(new int[]{150, 180}, ranges.get(1));
    assertArrayEquals(new int[]{225,1439}, ranges.get(2));
  }

  }