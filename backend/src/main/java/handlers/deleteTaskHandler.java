package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import Items.Calendar;
import com.google.firebase.auth.FirebaseAuthException;
import spark.Request;
import spark.Response;
import spark.Route;

public class deleteTaskHandler implements Route {

  // Need to get the object, and remove it from the Task map
  // Will not have impact on the calendar time
  Firestore firestore;

  public deleteTaskHandler(Firestore firestore){
    this.firestore = firestore;
  }

  @Override
  public Object handle(Request request, Response response){
    String taskID = request.queryParams("id");
    String tokenID = request.queryParams("tokenID");
    try {
      firestore.deleteFirebaseEvent(taskID,tokenID);
      return constructSuccessResponse("Event successfully deleted.");

    } catch (NumberFormatException e) {
      response.status(400);
      response.body("Invalid integer for event id.");
      return constructErrorResponse(response);
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    }
  }
}
