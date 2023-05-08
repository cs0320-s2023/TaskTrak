package handlers;

import Firebase.Firestore;
import Items.Calendar;
import spark.Request;
import spark.Response;
import spark.Route;

public class workingHoursHandler implements Route {

  private Calendar calendar;
  private Firestore firestore;


  public workingHoursHandler(Calendar calendar, Firestore firestore){
    this.calendar = calendar;
    this.firestore = firestore;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String startHour = request.queryParams("startHour");
    String endHour = request.queryParams("endHour");


    // Need to figure out how to do this so that each day gets created with this timing blocked off
    return null;
  }

}
