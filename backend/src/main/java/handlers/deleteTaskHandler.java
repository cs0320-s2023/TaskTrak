package handlers;

import spark.Request;
import spark.Response;
import spark.Route;

public class deleteTaskHandler implements Route {

  // Need to get the object, and remove it from the Task map
  // Will not have impact on the calendar time

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}
