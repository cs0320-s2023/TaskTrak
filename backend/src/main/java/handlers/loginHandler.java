package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Enums.Rating;
import Firebase.Firestore;
import Items.Calendar;
import Items.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
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

public class loginHandler implements Route {
  private UserState userState;
  private Firestore firestore;

  public loginHandler(UserState userState, Firestore firestore){
    this.userState = userState;
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String tokenID = request.queryParams("tokenID");

    try {
      //Get event and task lists
      ArrayList<List<Map<String,Object>>> eventTaskList = this.firestore.retrieveCalendar(tokenID);
      List<Map<String,Object>> events = eventTaskList.get(0);
      List<Map<String,Object>> tasks = eventTaskList.get(1);
      System.err.println("test");
      //Get time suggestions and setup task manager
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);
      LocalDateTime currentTime = LocalDateTime.now();
      System.err.println(currentTime);
      ArrayList<List<LocalDateTime>> timesList = this.firestore.retrieveADayTimes(currentTime,tokenID);

      System.err.println("test1");
      //Create new user objects and add to map
      String userID = this.firestore.getUserId(tokenID);
      Calendar calendar = new Calendar();
      this.userState.addUserCalendar(calendar,userID);
      TaskManager taskManager = new TaskManager();
      this.userState.addUserTaskManager(taskManager,userID);

      System.err.println("test2");

      //Block off time from user calendar
      for (List<LocalDateTime> times : timesList) {
        calendar.blockOffTime(times.get(0),times.get(1),false, true, taskManager);
      }
      //Add tasks to task manager
      System.err.println("test3");
      for (Map<String,Object> taskData : tasks) {
        String name = taskData.get("title").toString();
        String notes = taskData.get("notes").toString();
        Rating priority = Rating.fromValue((int) ((long)taskData.get("priority")));
        double duration = (double) taskData.get("duration");
        LocalDateTime dueDate = LocalDateTime.parse(taskData.get("dueDate").toString(),formatter);
        boolean isComplete = (boolean) taskData.get("isComplete");
        int taskID = (int) ((long) taskData.get("taskID"));

        Task loadedTask = new Task(name, notes,priority, duration, dueDate, isComplete, taskID);
        taskManager.addTask(loadedTask);
      }

      //Retrieve time suggestions
      System.err.println("test4");

      return constructSuccessResponse(events, tasks, taskManager.getAllTimeSuggestions());
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body("Invalid tokenID.");
      return constructErrorResponse(response);
    } catch (NullPointerException e) {
      response.status(500);
      response.body("Error retrieving task data from database: " + e);
      return constructErrorResponse(response);
    } catch (DateTimeParseException e) {
      response.status(500);
      response.body("Error parsing task due date data from database: " + e);
      return constructErrorResponse(response);
    } catch (ClassCastException e) {
      response.status(500);
      response.body("Error parsing task data from database: " + e);
      return constructErrorResponse(response);
    } catch(Exception e) {
      response.status(500);
      response.body("Unknown error: " + e);
      return constructErrorResponse(response);
    }
  }

}
