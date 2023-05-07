package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import Items.Calendar;
import com.google.firebase.auth.FirebaseAuthException;
import spark.Request;
import spark.Response;
import spark.Route;

public class deleteEventHandler implements Route {
  Calendar calendar;
  Firestore firestore;

  public deleteEventHandler(Calendar calendar, Firestore firestore){
    this.calendar = calendar;
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    String eventID = request.queryParams("id");
    String tokenID = request.queryParams("tokenID");
    try {
      firestore.deleteFirebaseEvent(eventID,tokenID);
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
