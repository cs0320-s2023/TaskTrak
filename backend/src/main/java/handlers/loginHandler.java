package handlers;

import static Response.MapResponse.constructErrorResponse;
import static Response.MapResponse.constructSuccessResponse;

import Firebase.Firestore;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class loginHandler implements Route {
  private Firestore firestore;
  public loginHandler(Firestore firestore) {
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String tokenID = request.queryParams("tokenID");

    try {
      ArrayList<List<Map<String,Object>>> calendar = firestore.retrieveCalendar(tokenID);
      return constructSuccessResponse(calendar);
    } catch (FirebaseAuthException e) {
      response.status(400);
      response.body("Invalid tokenID.");
      return constructErrorResponse(response);
    }
  }

}
