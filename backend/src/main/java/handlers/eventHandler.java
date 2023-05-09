package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Items.Calendar;
import Items.Day;
import Items.Event;
import Items.Task;
import com.google.firebase.FirebaseException;
import java.time.LocalDate;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;
import java.net.URLDecoder;
import Firebase.Firestore;

public class eventHandler implements Route {

  private UserState userState;
  private Firestore firestore;


  public eventHandler(UserState userState, Firestore firestore){
    this.userState = userState;
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
   */
  @Override
  public Object handle(Request request, Response response) {
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

      String userID = firestore.getUserId(tokenID);

      Calendar calendar = this.userState.getUserCalendar(userID);
      TaskManager taskManager = this.userState.getUserTaskManager(userID);

      calendar.blockOffTime(startTime, endTime, allDay, true, taskManager);


      Map<Integer, ArrayList<List<LocalTime>>> updatedTimeSuggestions = new HashMap<>();

      for (Integer key : taskManager.getTaskMap().keySet()) {
        ArrayList<List<LocalTime>> updatedTaskSuggestions = // updated task suggestions
            taskManager.getTask(key).getTimeSuggestions();

        // maps the task ID to the new time suggestions
        updatedTimeSuggestions.put(key, updatedTaskSuggestions);
      }




      return constructSuccessResponse(updatedTimeSuggestions);

    } catch (DateTimeParseException e) {
      String errorMessage = "Invalid date format. Expected format is 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
      response.status(400);
      response.body(errorMessage);
      System.err.println(e);
      return constructErrorResponse(response);

    } catch (UnsupportedEncodingException e) {
      System.err.println("Invalid string for notes");
      response.status(400);
      response.body("Invalid string for notes.");
      return constructErrorResponse(response);

    } catch (NumberFormatException e) {
      response.status(400);
      response.body("Invalid integer for event id.");
      return constructErrorResponse(response);

    } catch (FirebaseException e) {
      response.status(500);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    } catch (NullPointerException e) {
      response.status(500);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    }
  }
}
