package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;

import Algorithim.TaskManager;
import Enums.Rating;
import Items.Calendar;
import Items.Day;
import Items.Task;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.Types;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;


public class taskHandler implements Route {

  private TaskManager userTaskManager;
  private Calendar userCalendar;
  private Firestore firestore;

  public taskHandler(TaskManager userTaskManager, Calendar userCalendar, Firestore firestore) {
    this.userTaskManager = userTaskManager;
    this.userCalendar = userCalendar;
    this.firestore = firestore;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String name = request.queryParams("name");
    String notes = request.queryParams("notes");
    String priority = request.queryParams("priority");
    String duration = request.queryParams("duration");
    String dueDate = request.queryParams("dueDate");
    String isComplete = request.queryParams("isComplete");
    String taskID = request.queryParams("id");
    String tokenID = request.queryParams("tokenID");

    try {

      // below here we are setting formating all the queryparams into
      // their appropriate types
      String decodedName = URLDecoder.decode(name, "UTF-8");
      String decodedNotes = URLDecoder.decode(notes, "UTF-8");

      Rating decodedPriority = Rating.fromValue(Integer.parseInt(priority));
      Double decodedDuration = Double.parseDouble(duration);
      Boolean decodedIsComplete = Boolean.parseBoolean(isComplete);
      Integer decodedID = Integer.parseInt(taskID);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime decodedDueDate = LocalDateTime.parse(dueDate, formatter);

      // create Task object
      Task task = new Task(decodedName, decodedNotes, decodedPriority, decodedDuration,
          decodedDueDate, decodedIsComplete, decodedID);

      // add Task to the task manager
      this.userTaskManager.addTask(task);

      LocalDate todaysDate = LocalDate.now();
      Day todaysSchedule = this.userCalendar.getSchedule(todaysDate); // Day object for the current
      // day
      ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();
      // this is the free slots on the calendar

      this.userTaskManager.suggestionHelper(task, todaysFreeTime);

      //the timeSuggestions for the just task that was added
      List<List<LocalTime>> taskTimeSuggestions = task.getTimeSuggestions();

//      Moshi moshi = new Moshi.Builder()
//          .add(new LocalTimeAdapter())
//          .build();
//
//      // Define the type for ArrayList<LocalTime[]>
//      Type listOfLocalTimeType = Types.newParameterizedType(List.class, LocalTime.class);
//      Type listOfLocalTimeArraysType = Types.newParameterizedType(List.class, listOfLocalTimeType);
//
//      // Create JSON adapter for the type
//      com.squareup.moshi.JsonAdapter<List<List<LocalTime>>> adapter =
//          moshi.adapter(listOfLocalTimeArraysType);
//
//      // Convert taskTimeSuggestions to JSON
//      System.out.println("DOES NOT WORK SO FAR");
//      String jsonTaskTimeSuggestions = adapter.toJson(taskTimeSuggestions);
//      System.out.println("WORKS SO FAR");
//
//      // Set the response type to JSON
//      response.type("application/json");
      firestore.createFirebaseTask(task,tokenID);

      return constructSuccessResponse(taskTimeSuggestions);




    } catch (NumberFormatException e) {
      // handle error for invalid number format
      response.status(400); // Bad Request
      response.body("Invalid input format for priority or duration");
      return constructErrorResponse(response);
    } catch (DateTimeParseException e) {
      // handle error for invalid date format
      response.status(400); // Bad Request
      response.body("Invalid input format for dueDate");
      return constructErrorResponse(response);
    } catch (UnsupportedEncodingException e) {
      // handle error for unsupported encoding
      response.status(500); // Internal Server Error
      response.body("Failed to decode input");
      return constructErrorResponse(response);
    } catch (Exception e) {
      // handle generic error
      response.status(500); // Internal Server Error
      response.body("Error occurred while processing request" + e);
      return constructErrorResponse(response);
    }
  }

}
