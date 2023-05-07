package Main;

import static spark.Spark.after;

import Algorithim.TaskManager;
import Items.Calendar;
import handlers.deleteEventHandler;
import handlers.eventHandler;
import Firebase.Firestore;
import handlers.taskHandler;
import spark.Spark;

public class server {


  public static class Server {
    public static void main(String[] args) {


      Spark.port(3030);

      after((request, response) -> {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "*");
        response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
      });



      Firestore firestore = new Firestore();

      Calendar userCalendar = new Calendar();
      TaskManager userTaskManager = new TaskManager();

      eventHandler EventHandler = new eventHandler(userCalendar, firestore);
      deleteEventHandler deleteEvent = new deleteEventHandler(userCalendar,firestore);
      taskHandler TaskHandler = new taskHandler(userTaskManager, userCalendar,firestore);

      // For creating an event
      System.out.println("test: " + EventHandler);

      Spark.post("createEvent", EventHandler);
      Spark.post("createTask", TaskHandler);
      Spark.delete("deleteEvent", deleteEvent);
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
