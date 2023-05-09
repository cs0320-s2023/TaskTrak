package Main;

import static spark.Spark.after;

import Algorithim.TaskManager;
import Items.Calendar;
import handlers.UserState;
import handlers.deleteEventHandler;
import handlers.deleteTaskHandler;
import handlers.editEventHandler;
import handlers.eventHandler;
import Firebase.Firestore;
import handlers.loginHandler;
import handlers.taskHandler;
import java.util.HashMap;
import java.util.Map;
import spark.Spark;
import handlers.editTaskHandler;

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

//      Calendar userCalendar = new Calendar();
//      TaskManager userTaskManager = new TaskManager();
      UserState userState = new UserState(new HashMap<String,Calendar>(), new HashMap<String,TaskManager>());


      Spark.post("createEvent", new eventHandler(userState, firestore));
      Spark.post("createTask", new taskHandler(userState,firestore));
      Spark.post("editTask", new editTaskHandler(userState, firestore));
      Spark.post("editEvent", new editEventHandler(userState, firestore));
      Spark.delete("deleteEvent", new deleteEventHandler(userState,firestore));
      Spark.delete("deleteTask", new deleteTaskHandler(userState,firestore));
      Spark.get("login", new loginHandler(userState,firestore));
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
