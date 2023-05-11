package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import spark.Request;
import spark.Response;
import spark.Route;

public class logoutHandler implements Route {
  private UserState userState;
  private Firestore firestore;

  public logoutHandler(UserState userState, Firestore firestore){
    this.userState = userState;
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String tokenID = request.queryParams("tokenID");

    try {
      String userID = this.firestore.getUserId(tokenID);
      //Remove user fails
      if (!this.userState.removeUser(userID)) {
        response.status(500);
        response.body("Error: user unable to be removed from map");
        return constructErrorResponse(response);
      }
      return constructSuccessResponse("Successfully logged out!");
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body(e.getMessage());
      return constructErrorResponse(response);
    }
  }
}
