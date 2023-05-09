package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Algorithim.TaskManager;
import Firebase.Firestore;
import Items.Calendar;
import com.google.firebase.auth.FirebaseAuthException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import spark.Request;
import spark.Response;
import spark.Route;

public class deleteEventHandler implements Route {
  private UserState userState;
  private Firestore firestore;


  public deleteEventHandler(UserState userState, Firestore firestore){
    this.userState = userState;
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    String eventID = request.queryParams("id");
    String startDate = request.queryParams("startDate");
    String endDate = request.queryParams("endDate");
    String isAllDay = request.queryParams("isAllDay");
    String tokenID = request.queryParams("tokenID");
    try {
      LocalDateTime startTime = LocalDateTime.parse(startDate);
      LocalDateTime endTime = LocalDateTime.parse(endDate);

      Boolean allDay = Boolean.parseBoolean(isAllDay);

      firestore.deleteFirebaseEvent(eventID, tokenID);
      String userID = firestore.getUserId(tokenID);

      Calendar calendar = this.userState.getUserCalendar(userID);
      TaskManager taskManager = this.userState.getUserTaskManager(userID);

      calendar.blockOffTime(startTime,endTime,allDay,false,taskManager);
      return constructSuccessResponse("Event successfully deleted.");

    } catch (DateTimeParseException e) {
      response.status(400);
      response.body("Invalid time for start or end time: " + e);
      return constructErrorResponse(response);
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    }

  }
}
