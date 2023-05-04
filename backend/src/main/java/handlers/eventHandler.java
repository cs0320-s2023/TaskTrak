package handlers;

import Items.Calendar;
import Items.Event;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import spark.Request;
import spark.Response;
import spark.Route;
import java.net.URLDecoder;

public class eventHandler implements Route {

  private Calendar calendar;


  public eventHandler(Calendar calendar){
    this.calendar = calendar;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
      System.out.println("handle method being run");
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

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime startTime = LocalDateTime.parse(startDateString, formatter);
      LocalDateTime endTime = LocalDateTime.parse(endDateString, formatter);

      Boolean allDay = Boolean.parseBoolean(isAllDay);
      Boolean repeated = Boolean.parseBoolean(isRepeated);

      Event Event = new Event(title, decodedNotes, startTime, endTime);

      if(Event.getName() != null) {
        System.out.println(Event.getName());
      }

    System.out.println(Event);


    } catch (DateTimeParseException e) {
      String errorMessage = "Invalid date format. Expected format is 'yyyy-MM-dd'T'HH:mm:ss'Z'";
      response.status(400);
      response.body(errorMessage);
      return response;
    }


    return null;





  }
}
