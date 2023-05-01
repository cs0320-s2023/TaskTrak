package Items;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
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
      bigCalendar.put(date, day);
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


}
