package Algorithim;

import Items.Calendar;
import Items.Day;
import Items.Event;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class eventManager {

  private Map<String, Event> eventMap;

  public eventManager() {
    this.eventMap = new HashMap<>();
  }

  /**
   * Adds a Event to the Event list
   *
   * @param event
   */
  public void addEvent(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("event cannot be null");
    }

    if (this.eventMap.containsKey(event.getName())) {
      throw new IllegalArgumentException("event with name '" + event.getName() + "' already "
          + "exists");
    }

    this.eventMap.put(event.getName(), event);
  }

  /**
   * removes a event to the event list
   */
  public void removeEvent(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    if (!eventMap.containsKey(name)) {
      throw new IllegalArgumentException("event with name '" + name + "' does not exist");
    }

    this.eventMap.remove(name);
  }


  /**
   * Returns the specific event you are searching for by name
   * @param name
   * @return
   */
  public Event getEvent(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    Event event = this.eventMap.get(name);
    if (event == null) {
      throw new IllegalArgumentException("event with name '" + name + "' does not exist");
    }

    return event;
  }


  /**
   * Returns the entire eventList
   *
   * @return
   */
  public Map<String, Event> getEventMap() {
    return this.eventMap;
  }



}
