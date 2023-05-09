package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Enums.Rating;
import Firebase.Firestore;
import Items.Calendar;
import Items.Day;
import Items.Task;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

public class editTaskHandler implements Route{

  private UserState userState;
  private Firestore firestore;


  public editTaskHandler(UserState userState, Firestore firestore){
    this.userState = userState;
    this.firestore = firestore;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String name = request.queryParams("name");
    String notes = request.queryParams("notes");
    String priority = request.queryParams("priority");
    String dueDate = request.queryParams("dueDate");
    String isComplete = request.queryParams("isComplete");
    String taskID = request.queryParams("id");
    String newDuration = request.queryParams("newDuration");
    String tokenID = request.queryParams("tokenID");

    try {

      // below here we are setting formating all the queryparams into
      // their appropriate types
      String decodedName = URLDecoder.decode(name, "UTF-8");
      String decodedNotes = URLDecoder.decode(notes, "UTF-8");

      Rating decodedPriority = Rating.fromValue(Integer.parseInt(priority));
      Double decodedNewDuration = Double.parseDouble(newDuration);
      Boolean decodedIsComplete = Boolean.parseBoolean(isComplete);
      int decodedID = Integer.parseInt(taskID);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime decodedDueDate = LocalDateTime.parse(dueDate, formatter);

      List<List<LocalTime>> taskTimeSuggestions = new ArrayList<>();

      String userID = firestore.getUserId(tokenID);

      Calendar calendar = this.userState.getUserCalendar(userID);
      TaskManager taskManager = this.userState.getUserTaskManager(userID);

      Task oldTask = taskManager.getTask(decodedID);

      if (decodedNewDuration != oldTask.getTimeToComplete()) {
        //The DANGER is that not all the task information in this map will be up-to-date
        oldTask.setTimeToComplete(decodedNewDuration);

        LocalDate todaysDate = LocalDate.now();
        Day todaysSchedule = calendar.getSchedule(
            todaysDate); // Day object for the current
        ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();
        // this is the free slots on the calendar

        taskManager.suggestionHelper(oldTask, todaysFreeTime);

        //the timeSuggestions for the just task that was added
        taskTimeSuggestions = oldTask.getTimeSuggestions();
      }

      // create Task object for database
      Task newTask = new Task(decodedName, decodedNotes, decodedPriority, decodedNewDuration,
          decodedDueDate, decodedIsComplete, decodedID);

      firestore.createFirebaseTask(newTask,tokenID);


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
