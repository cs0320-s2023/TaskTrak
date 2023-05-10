package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
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

  private UserState userState;
  private Firestore firestore;


  public taskHandler(UserState userState, Firestore firestore){
    this.userState = userState;
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

      String userID = this.firestore.getUserId(tokenID);

      TaskManager taskManager = this.userState.getUserTaskManager(userID);
      Calendar calendar = this.userState.getUserCalendar(userID);

      // add Task to the task manager
      taskManager.addTask(task);

      LocalDate todaysDate = LocalDate.now();
      Day todaysSchedule = calendar.getSchedule(todaysDate); // Day object for the current
      // day
      ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();
      // this is the free slots on the calendar

      taskManager.suggestionHelper(task, todaysFreeTime);

      //the timeSuggestions for the just task that was added

      List<List<LocalTime>> taskTimeSuggestions = task.getTimeSuggestions();



      this.firestore.createFirebaseTask(task,tokenID);

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
    } catch (NullPointerException e) {
      response.status(500);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    } catch (FirebaseException e) {
      response.status(500);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    } catch (Exception e) {
      // handle generic error
      response.status(500); // Internal Server Error
      response.body("Error occurred while processing request" + e);
      return constructErrorResponse(response);
    }
  }

}
