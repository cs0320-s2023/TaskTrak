package handlers;

import Items.Calendar;
import Items.Day;
import Items.Event;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
  @Override
  public Object handle(Request request, Response response) throws Exception {
    System.err.println("handle method being run");
    Firestore test = new Firestore();
    test.createEventFirebase();

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

      Boolean allDay = Boolean.parseBoolean(isAllDay);
      // String repeated = Boolean.parseBoolean(isRepeated);

      Event event = new Event(title, decodedNotes, startTime, endTime);

      // Creates a Day object for the event day if it doesn't
      this.calendar.addDay(startTime.toLocalDate(), new Day());
      firestore.

      // We need to get the time of the event




      System.out.println(event.getName());


    System.out.println(event);


    } catch (DateTimeParseException e) {
      String errorMessage = "Invalid date format. Expected format is 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
      response.status(400);
      response.body(errorMessage);
      return response;
    }


    return null;





  }
}
