package Main;

import static spark.Spark.after;

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


      // Setting up the handler for the GET /order endpoint
      // LoadHandler loadHandler = new LoadHandler();
      // Spark.get("load", loadHandler);
      // Spark.get("view", new ViewHandler(loadHandler));
      // Spark.get("search", new SearchHandler(loadHandler));
      // Spark.get("weather", new WeatherHandler());
      eventHandler EV = new eventHandler();

      Spark.get("event", EV);
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started.");
    }
  }

}
