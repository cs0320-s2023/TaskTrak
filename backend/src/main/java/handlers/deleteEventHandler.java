package handlers;

import static Response.MapResponse.constructErrorResponse;

import Firebase.Firestore;
import Items.Calendar;
import spark.Request;
import spark.Response;
import spark.Route;

public class deleteEventHandler implements Route {


  public eventHandler(Calendar calendar, Firestore firestore){
    this.calendar = calendar;
    this.firestore = firestore;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    String eventID = request.queryParams("id");
    String tokenID = request.queryParams("tokenID");
    try {
      int id = Integer.parseInt(eventID);



    } catch (NumberFormatException e) {
      response.status(400);
      response.body("Invalid integer for event id.");
      return constructErrorResponse(response);
    }

  }
}
