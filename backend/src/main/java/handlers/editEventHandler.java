package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Firebase.Firestore;
import Items.Calendar;
import Items.Event;
import com.google.firebase.FirebaseException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
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

public class editEventHandler implements Route {

  private UserState userState;
  private Firestore firestore;

  public editEventHandler(UserState userState, Firestore firestore){
    this.userState = userState;
    this.firestore = firestore;
  }

  // We need to decide how to handle edit an event:

  // Are we removing the event and creating a new one OR
  // editing the same object and updating the fields OR
  //
  // If it is the former, we would just call the deleteEventhandler and then the eventHandler


  @Override
  public Object handle(Request request, Response response) throws Exception {
    String title = request.queryParams("title");
    String oldStartDateString = request.queryParams("oldStartDate");
    String oldEndDateString = request.queryParams("oldEndDate");
    String eventID = request.queryParams("id");
    String notes = request.queryParams("notes");
    String isAllDay = request.queryParams("isAllDay");
    String isRepeated = request.queryParams("isRepeated");
    String tokenID = request.queryParams("tokenID"); //Used for user identification, not needed
//    at the moment
    String newStartTime = request.queryParams("newStartDate");
    String newEndTime = request.queryParams("newEndDate");

    // -------------------erasing old event-------------------

    try {

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime oldStartTime = LocalDateTime.parse(oldStartDateString, formatter);
      LocalDateTime oldEndTime = LocalDateTime.parse(oldEndDateString, formatter);

      int id = Integer.parseInt(eventID);
      Boolean oldAllDay = Boolean.parseBoolean(isAllDay);
//      Boolean repeated = Boolean.parseBoolean(isRepeated);

      String userID = firestore.getUserId(tokenID);
      Calendar calendar = this.userState.getUserCalendar(userID);
      TaskManager taskManager = this.userState.getUserTaskManager(userID);


      calendar.blockOffTime(oldStartTime, oldEndTime, oldAllDay, false, taskManager);

      // -------------------making new changes-------------------

      String newDecodedNotes = URLDecoder.decode(notes, "UTF-8");
      String newDecodedTitle = URLDecoder.decode(title, "UTF-8");

      LocalDateTime newStartDate = LocalDateTime.parse(newStartTime, formatter);
      LocalDateTime newEndDate = LocalDateTime.parse(newEndTime, formatter);
      Boolean newAllDay = Boolean.parseBoolean(isAllDay);


      Event newEvent = new Event(newDecodedTitle, newDecodedNotes, newStartDate, newEndDate, id,
          newAllDay);

      firestore.createEventFirebase(newEvent, tokenID);

      calendar.blockOffTime(newStartDate, newEndDate, newAllDay, true, taskManager);


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

    } catch (
        UnsupportedEncodingException e) {
      System.err.println("Invalid string for notes");
      response.status(400);
      response.body("Invalid string for notes.");
      return constructErrorResponse(response);

    } catch (NumberFormatException e) {
      response.status(400);
      response.body("Invalid integer for event id.");
      return constructErrorResponse(response);

    } catch (
        FirebaseException e) {
      response.status(500);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    }
  }
}