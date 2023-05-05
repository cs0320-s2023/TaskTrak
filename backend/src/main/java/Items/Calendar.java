package Items;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Calendar {
    private Map<LocalDate, Day> bigCalendar;

  /**
   * Constructor for the Calendar
   */
  public Calendar() {
      bigCalendar = new HashMap<>();
      createDayObjectForToday();
    }

//
//    public void bigUpdater(ArrayList[String[]], ) {
//    //Joel is passing me 2 object arays of strings that are start and end time
//      // convert these to minutes of day
//      //


  /**
   * Returns the day object for that specific Day
   * @param date - date we want to look at
   * @return - Day object
   */
    public Day getSchedule(LocalDate date) {
      return bigCalendar.get(date);
    }

  /**
   * Adds a day to the map
   * @param date - date of the day to be added
   * @param day - the day schedule object we are adding
   */
    public void addDay(LocalDate date, Day day) {
      if(!this.bigCalendar.containsKey(date)) {
        this.bigCalendar.put(date, day);
        }
      }


  /**
   * Removes a day from the BigCalendar map
   * @param date
   */
  public void removeDay(LocalDate date) {
      bigCalendar.remove(date);
    }

  /**
   * If a day object does not already exist for the current day, it will create one at midnight
   * This method ensures that scheduling suggestions can always be provided
   *
   * potential idea -- possibly make a cache
   */
  public void createDayObjectForToday() {
      // Get the current date and time
      LocalDateTime now = LocalDateTime.now();
      LocalDate today = now.toLocalDate();
      LocalTime midnight = LocalTime.MIDNIGHT;
      LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

      // Check if a Day object already exists for today
      if (!bigCalendar.containsKey(today)) {
        // Create a new Day object for today
        Day todaySchedule = new Day();
        bigCalendar.put(today, todaySchedule);
        System.out.println("Created new Day object for today: " + today);
      }

      // Schedule the next call to this method at midnight tomorrow
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
      Duration duration = Duration.between(now, tomorrowMidnight);
      long initialDelay = duration.getSeconds();
      scheduler.schedule(this::createDayObjectForToday, initialDelay, TimeUnit.SECONDS);
  }



  public void blockOffTime(Event event, boolean allDay) {
    int startHour = event.getStartTime().getHour();
    int endHour = event.getEndTime().getHour();

    int startMinuteBlock = event.getStartTime().getMinute() / 15;
    int endMinuteBlock = event.getEndTime().getMinute() / 15;

    LocalDate startDate = event.getStartTime().toLocalDate();
    LocalDate endDate = event.getEndTime().toLocalDate();


    List<LocalDate> dateList = new ArrayList<>(); // list of dates the events span
    if (!allDay) { // if the event is not all day

      LocalDate date = startDate; // date in the loop



      while (!date.isAfter(endDate)) { // within date range
        this.addDay(date, new Day()); // adds day object to calendar if it does not exist
        dateList.add(date); // add date
        date = date.plusDays(1); // iterate by 1 date within the range
      }
      // for each day get the calendar Day Object
      for (LocalDate day : dateList) {
        int dayNum = 1;
//        this.addDay(day, new Day()); // creates day object if it doesn't exist already
//
        if (dateList.size() > 1) { // if event spans multiple days
          if (dayNum == 0) { // first day of multi day
            this.getSchedule(day).bookTimeRange(startHour, startMinuteBlock,
                23, 3, true);
          }
          if (dayNum < dateList.size()) { // if this is a middle day of the event, book all day
            this.getSchedule(day).bookTimeRange(0, 0,
                23, 3, true);
          }
          if (dayNum == dateList.size()) { // if this is the last day of the event
            this.getSchedule(day).bookTimeRange(0, 0,
                endHour, endMinuteBlock, true);
          }
        } else { // if the event is only on one day, block off that time
          this.getSchedule(day).bookTimeRange(startHour, startMinuteBlock, endHour,
              endMinuteBlock,true);
        }
        dayNum ++;
      }
    }
  }



}
