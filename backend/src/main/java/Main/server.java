package Main;

import static spark.Spark.after;

import Items.Calendar;
import handlers.eventHandler;
import java.beans.EventHandler;
import spark.Spark;

public class server {


  public class Server {
    public static void main(String[] args) {

      Spark.port(3232);

      after((request, response) -> {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "*");
      });


      Calendar userCalendar = new Calendar();
      eventHandler EV = new eventHandler(userCalendar);

      // For creating an event

      Spark.post("/createEvent", EV);
      //Spark.post();
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
