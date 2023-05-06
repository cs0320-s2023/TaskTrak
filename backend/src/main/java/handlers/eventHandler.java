package handlers;

import Items.Calendar;
import Items.Day;
import Items.Event;
import Items.timeMethods;
import java.time.LocalDate;
import java.io.UnsupportedEncodingException;
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
    String title = request.queryParams("title");
    String startDateString = request.queryParams("startDate");
    String endDateString = request.queryParams("endDate");
    String eventID = request.queryParams("id");
    String notes = request.queryParams("notes");
    String isAllDay = request.queryParams("isAllDay");
    String isRepeated = request.queryParams("isRepeated");
    String tokenID = request.queryParams("tokenID"); //Used for user identification, not needed
//    at the moment

    try {
      String decodedNotes = URLDecoder.decode(notes, "UTF-8");
      String decodedTitle = URLDecoder.decode(title, "UTF-8");

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime startTime = LocalDateTime.parse(startDateString, formatter);
      LocalDateTime endTime = LocalDateTime.parse(endDateString, formatter);
      LocalDate eventDate = startTime.toLocalDate();

      int id = Integer.parseInt(eventID);
      Boolean allDay = Boolean.parseBoolean(isAllDay);
//      Boolean repeated = Boolean.parseBoolean(isRepeated);

      Event event = new Event(decodedTitle, decodedNotes, startTime, endTime, id, allDay);
      firestore.createEventFirebase(event,tokenID);

      System.out.println(event.getName());

      // Creates a Day object for the event day if it doesn't

      System.out.println("added day");



      this.calendar.blockOffTime(event,allDay);




    } catch (DateTimeParseException e) {
      String errorMessage = "Invalid date format. Expected format is 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
      response.status(400);
      response.body(errorMessage);
      System.err.println(e);
      return response;
    } catch (UnsupportedEncodingException e) {
      System.err.println("Invalid string for notes");
      response.status(400);
      response.body("Invalid string for notes.");
      return response;
    }
    return null;
  }
}
