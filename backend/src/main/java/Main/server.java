package Main;

import static spark.Spark.after;

import Items.Calendar;
import handlers.eventHandler;
import Firebase.Firestore;
import spark.Spark;

public class server {


  public static class Server {
    public static void main(String[] args) {

      Spark.port(3030);

      after((request, response) -> {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "*");
      });

      Calendar userCalendar = new Calendar();
      Firestore firestore = new Firestore();

      eventHandler EV = new eventHandler(userCalendar,firestore);

      // For creating an event
      System.out.println("test: " + EV);

      Spark.post("createEvent", EV);
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
