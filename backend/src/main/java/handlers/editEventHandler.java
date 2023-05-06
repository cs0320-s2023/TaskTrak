package handlers;

import spark.Request;
import spark.Response;
import spark.Route;

public class editEventHandler implements Route {


  // We need to decide how to handle delete an event:

  // Are we removing the event and creating a new one OR
  // editing the same object and updating the fields OR
  //
  // If it is the former, we would just call the deleteEventhandler and then the eventHandler


  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }

}
