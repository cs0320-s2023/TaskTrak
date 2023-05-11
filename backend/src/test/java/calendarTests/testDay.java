package calendarTests;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Algorithim.TaskManager;
import Enums.Rating;
import Items.Day;
import Items.Task;
import Items.timeMethods;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testDay {
    private Day day;

  @BeforeEach
  public void setUp() {
    day = new Day();
  }





  @Test
  void testSizeOfDay() {
    int[][] timeSlots = this.day.getTimeSlots();
    assertEquals(24, timeSlots.length);
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 4; j++) {
        assertEquals(4, timeSlots[i].length, "The number of columns should be 4 for row " + i);
        assertEquals(1, timeSlots[i][j]);
      }
    }

    for (int i = 7; i < 24; i++) {
      for (int j = 0; j < 4; j++) {
        assertEquals(4, timeSlots[i].length, "The number of columns should be 4 for row " + i);
        assertEquals(0, timeSlots[i][j]);
      }
    }
  }


  @Test
  void timeSuggestion(){
    day.bookTimeRange(0,0,8,0, true);
    day.bookTimeRange(12,0,18,0, true);
    Task task1 = new Task("Task 1", "Notes 1", Rating.HIGH, 2.0,
        LocalDateTime.now().plusDays(1), false, 0);

    ArrayList<int[]> freeTimes = day.findAvailableTimeRanges();
    System.out.println(freeTimes.get(1));
    TaskManager.suggestionHelper(task1, freeTimes);

    ArrayList<List<LocalTime>> timeIntervals = new ArrayList<>();

    // Add the time intervals to the ArrayList
    timeIntervals.add(List.of(LocalTime.of(8, 0), LocalTime.of(10, 0)));
    timeIntervals.add(List.of(LocalTime.of(10, 0), LocalTime.of(12, 0)));
    timeIntervals.add(List.of(LocalTime.of(18, 0), LocalTime.of(20, 0)));
    timeIntervals.add(List.of(LocalTime.of(20, 0), LocalTime.of(22, 0)));

    System.out.println(task1.getTimeSuggestions());
    assertEquals(timeIntervals, task1.getTimeSuggestions());
  }


  @Test
  void timeSuggestion2(){
    day.bookTimeRange(0,0,8,0, true);
    day.bookTimeRange(12,0,18,0, true);
    Task task1 = new Task("Task 1", "Notes 1", Rating.HIGH, 4.2,
        LocalDateTime.now().plusDays(1), false, 0);

    ArrayList<int[]> freeTimes = day.findAvailableTimeRanges();
    TaskManager.suggestionHelper(task1, freeTimes);

    ArrayList<List<LocalTime>> timeIntervals = new ArrayList<>();

    // Add the time intervals to the ArrayList
//    timeIntervals.add(List.of(LocalTime.of(8, 0), LocalTime.of(10, 0)));
//    timeIntervals.add(List.of(LocalTime.of(10, 0), LocalTime.of(12, 0)));
//    timeIntervals.add(List.of(LocalTime.of(18, 0), LocalTime.of(20, 0)));
//    timeIntervals.add(List.of(LocalTime.of(20, 0), LocalTime.of(22, 0)));

    System.out.println(task1.getTimeSuggestions());
    //assertEquals(timeIntervals, task1.getTimeSuggestions());
  }


  @Test
  void testEmptyAvailability() {
    List<int[]> slots = day.findAvailableTimeRanges();
    assertEquals(1, slots.size());
    assertEquals(420, slots.get(0)[0]);
    assertEquals(1439, slots.get(0)[1]);
  }


  @Test
  void testBookTimeRange(){
    assertEquals(1 , day.getTimeSlots()[0][0]);
    assertEquals(0, day.getTimeSlots()[23][3]);
    day.bookTimeRange(0,0, 1, 0, true);
    assertEquals(2, day.getTimeSlots()[0][0]);
    assertEquals(2, day.getTimeSlots()[0][1]);
    assertEquals(2, day.getTimeSlots()[0][2]);
    assertEquals(2,day.getTimeSlots()[0][3]);
    assertEquals(1, day.getTimeSlots()[1][0]);
    day.bookTimeRange(0,0, 0, 1, true);
    assertEquals(3, day.getTimeSlots()[0][0]);
    day.bookTimeRange(0,0, 0, 1, false);
    assertEquals(2, day.getTimeSlots()[0][0]);
    day.bookTimeRange(23,1, 23, 3, true);
    assertEquals(1, day.getTimeSlots()[23][1]);
    assertEquals(1, day.getTimeSlots()[23][2]);
    assertEquals(0, day.getTimeSlots()[23][0]);
    assertEquals(0, day.getTimeSlots()[23][3]);
  }



  @Test
  void testSingleSlot() {
    day.bookTimeRange(8, 0, 9, 2, true);
    List<int[]> slots = day.findAvailableTimeRanges();
    assertEquals(2, slots.size());

    assertEquals(420, slots.get(0)[0]);
    assertEquals(480, slots.get(0)[1]);

    assertEquals(570, slots.get(1)[0]);
    assertEquals(1439, slots.get(1)[1]);

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
    day.bookTimeRange(6, 0, 12, 0, true);
    day.bookTimeRange(15, 0, 17, 0, true);
    List<int[]> ranges = day.findAvailableTimeRanges();
    assertEquals(2, ranges.size());
    assertArrayEquals(new int[]{720, 900}, ranges.get(0));
    assertArrayEquals(new int[]{1020,1439}, ranges.get(1));
  }


  @Test
  public void testInvalidStartHourIndex() {

    assertThrows(IllegalArgumentException.class, () ->
        day.bookTimeRange(-1, 0, 10, 0, true));
  }

  @Test
  public void testInvalidStartMinuteIndex() {

    assertThrows(IllegalArgumentException.class, () ->
        day.bookTimeRange(0, 5, 10, 0, true));
  }

  @Test
  public void testInvalidEndHourIndex() {

    assertThrows(IllegalArgumentException.class, () ->
        day.bookTimeRange(10, 0, 25, 0, true));
  }

  @Test
  public void testInvalidEndMinuteIndex() {

    assertThrows(IllegalArgumentException.class, () ->
        day.bookTimeRange(10, 0, 20, 5, true));
  }

  @Test
  public void testInvalidTimeRange() {

    assertThrows(IllegalArgumentException.class, () ->
        day.bookTimeRange(12, 0, 10, 0, true));
  }


  }