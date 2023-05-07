package Main;

import static spark.Spark.after;

import Algorithim.TaskManager;
import Items.Calendar;
import handlers.deleteEventHandler;
import handlers.deleteTaskHandler;
import handlers.eventHandler;
import Firebase.Firestore;
import handlers.taskHandler;
import java.util.HashMap;
import java.util.Map;
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

      //This needs to be changed so that it is only created when a user logs in
      //Maybe, construct an empty map of calendars, then add to that map when a user logs in
      //Remove when they sign out. The map will always exist, keys will be userIDs taken from
      //a firebase query (when a user signs up, generate a private key for just their data)
      //
      Map<String,Calendar> calendarMap = new HashMap<>();
      Calendar userCalendar = new Calendar();
      TaskManager userTaskManager = new TaskManager();

      eventHandler EventHandler = new eventHandler(userCalendar, firestore);
      deleteEventHandler deleteEvent = new deleteEventHandler(userCalendar,firestore);
      taskHandler TaskHandler = new taskHandler(userTaskManager, userCalendar,firestore);
      deleteTaskHandler deleteTask = new deleteTaskHandler(firestore);

      Spark.post("createEvent", EventHandler);
      Spark.post("createTask", TaskHandler);
      Spark.delete("deleteEvent", deleteEvent);
      Spark.delete("deleteTask", deleteTask);
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
