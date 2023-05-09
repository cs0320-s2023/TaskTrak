package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Firebase.Firestore;
import Items.Calendar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

      //Get time suggestions and setup task manager
//      ArrayList<List<LocalDateTime>> timesList = this.firestore.retrieveADayTimes(LocalDateTime.now(),tokenID);
//
//      //Block off times in calendar
//      String userID = this.firestore.getUserId(tokenID);
//      Calendar calendar = this.userState.getUserCalendar(userID);
//      TaskManager taskManager = this.userState.getUserTaskManager(userID);
//
//      for (List<LocalDateTime> times : timesList) {
//        calendar.blockOffTime(times.get(0),times.get(1),false, true, taskManager);
//      }
//      //Add tasks to task manager
//      for ()
//
//      //Retrieve time suggestions
//      taskManager.timeSuggestionAlgorithim(calendar);

      return constructSuccessResponse(events, tasks, new ArrayList());
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body("Invalid tokenID.");
      return constructErrorResponse(response);
    }
  }

}
