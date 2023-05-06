package handlers;


import Algorithim.TaskManager;
import Enums.Rating;
import Items.Calendar;
import Items.Day;
import Items.Task;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;


public class taskHandler implements Route {

  private TaskManager userTaskManager;
  private Calendar userCalendar;

  public taskHandler(TaskManager userTaskManager, Calendar userCalendar) {
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String name = request.queryParams("name");
    String notes = request.queryParams("notes");
    String priority = request.queryParams("priority");
    String duration = request.queryParams("duration");
    String dueDate = request.queryParams("dueDate");
    String isComplete = request.queryParams("isComplete");

    try {

      // below here we are setting formating all the queryparams into
      // their appropriate types
      String decodedName = URLDecoder.decode(name, "UTF-8");
      String decodedNotes = URLDecoder.decode(notes, "UTF-8");

      Rating decodedPriority = Rating.fromValue(Integer.parseInt(priority));
      Double decodedDuration = Double.parseDouble(duration);
      Boolean decodedIsComplete = Boolean.parseBoolean(isComplete);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      LocalDateTime decodedDueDate = LocalDateTime.parse(dueDate, formatter);

      // creat Task object
      Task task = new Task(decodedName, decodedNotes, decodedPriority, decodedDuration,
          decodedDueDate, decodedIsComplete);

      // add Task to the task manager
      userTaskManager.addTask(task);

      LocalDate todaysDate = LocalDate.now();
      Day todaysSchedule = userCalendar.getSchedule(todaysDate); // Day object for the current day
      ArrayList<int[]> todaysFreeTime = todaysSchedule.findAvailableTimeRanges();
      // this is the free slots on the calendar

      userTaskManager.suggestionHelper(task, todaysFreeTime);

      //the timeSuggestions for the just task that was added
      ArrayList<LocalTime[]> taskTimeSuggestions = task.getTimeSuggestions();
      Gson gson = new Gson();
      String jsonTaskTimeSuggestions = gson.toJson(taskTimeSuggestions);
      response.type("timeSuggestions/json");

      return taskTimeSuggestions;




    } catch (NumberFormatException e) {
      // handle error for invalid number format
      response.status(400); // Bad Request
      return "Invalid input format for priority or duration";
    } catch (DateTimeParseException e) {
      // handle error for invalid date format
      response.status(400); // Bad Request
      return "Invalid input format for dueDate";
    } catch (UnsupportedEncodingException e) {
      // handle error for unsupported encoding
      response.status(500); // Internal Server Error
      return "Failed to decode input";
    } catch (Exception e) {
      // handle generic error
      response.status(500); // Internal Server Error
      return "Error occurred while processing request";
    }
  }

}
