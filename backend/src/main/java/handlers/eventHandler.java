package handlers;

import Items.Calendar;
import Items.Day;
import Items.Event;
import Items.timeMethods;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;
import java.net.URLDecoder;
import Firebase.Firestore;

public class eventHandler implements Route {

  private Calendar calendar;
  private Firestore firestore;


  public eventHandler(Calendar calendar, Firestore firestore){
    this.calendar = calendar;
    this.firestore = firestore;
  }


  /**
   * This method handles the incoming request and creates a new event based on the parameters passed
   * in the request. It then blocks off the appropriate sections of the user's calendar.
   *
   *
   * @param request the incoming request object containing the parameters for creating the event
   * @param response the response object used to handle any errors
   * @return null
   * @throws Exception if there is an error parsing the date parameters or adding the event to the calendar
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    System.err.println("handle method being run");
    String title = request.queryParams("title");
    String startDateString = request.queryParams("startDate");
    String endDateString = request.queryParams("endDate");
    String eventID = request.queryParams("id");
    String notes = request.queryParams("notes");
    String isAllDay = request.queryParams("isAllDay");
    String isRepeated = request.queryParams("isRepeated");

//    String tokenID = request.queryParams("tokenID"); //Used for user identification, not needed
//    at the moment

    try {
      String decodedNotes = URLDecoder.decode(notes, "UTF-8");

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime startTime = LocalDateTime.parse(startDateString, formatter);
      LocalDateTime endTime = LocalDateTime.parse(endDateString, formatter);
      LocalDate eventDate = startTime.toLocalDate();
      Boolean allDay = Boolean.parseBoolean(isAllDay);
      // String repeated = Boolean.parseBoolean(isRepeated);

      Event event = new Event(title, decodedNotes, startTime, endTime);

<<<<<<< HEAD
=======
      // Creates a Day object for the event day if it doesn't
      this.calendar.addDay(startTime.toLocalDate(), new Day());
      firestore.createEventFirebase(event);
>>>>>>> 0bbf3e6998cbdeb560bcbadf7eaee1abde815296

      int startHour = event.getStartTime().getHour();
      int endHour = event.getEndTime().getHour();

      int startMinuteBlock = event.getStartTime().getMinute() / 15;
      int endMinuteBlock = event.getEndTime().getMinute() / 15;

      LocalDate startDate = event.getStartTime().toLocalDate();
      LocalDate endDate = event.getEndTime().toLocalDate();


      List<LocalDate> dateList = new ArrayList<>(); // list of dates the events span
      if (!allDay.equals(true)) { // if the event is not all day

        LocalDate date = startDate; // date in the loop
        while (!date.isAfter(endDate)) { // within date range
          dateList.add(date); // add date
          date = date.plusDays(1); // iterate by 1 date within the range
        }
        // for each day get the calendar Day Object
        for (LocalDate day : dateList) {
          int dayNum = 1;
          this.calendar.addDay(day, new Day()); // creates day object if it doesn't exist already

          if (dateList.size() > 1) { // if event spans multiple days
            if (dayNum == 0) { // first day of multi day
            this.calendar.getSchedule(day).bookTimeRange(startHour, startMinuteBlock,
                23, 3, true);
            }
            if (dayNum < dateList.size()) { // if this is a middle day of the event, book all day
              this.calendar.getSchedule(day).bookTimeRange(0, 0,
                  23, 3, true);
            }
            if (dayNum == dateList.size()) { // if this is the last day of the event
              this.calendar.getSchedule(day).bookTimeRange(0, 0,
                  endHour, endMinuteBlock, true);
            }
        } else { // if the event is only on one day, block off that time
            this.calendar.getSchedule(day).bookTimeRange(startHour, startMinuteBlock, endHour,
                endMinuteBlock,true);
          }
            dayNum ++;
          }
      }


      System.out.println(event.getName());

<<<<<<< HEAD
      System.out.println(event);
=======

    System.out.println(event);
>>>>>>> 0bbf3e6998cbdeb560bcbadf7eaee1abde815296


    } catch (DateTimeParseException e) {
      String errorMessage = "Invalid date format. Expected format is 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
      response.status(400);
      response.body(errorMessage);
      return response;
    }
    return null;
  }
}
