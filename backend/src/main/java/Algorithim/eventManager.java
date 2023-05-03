package Algorithim;

import Items.Event;
import java.util.HashMap;
import java.util.Map;

public class eventManager {
  
    private Map<String, Event> eventMap;

    public eventManager() {
      this.eventMap = new HashMap<>();
    }

    /**
     * Adds a Event to the Event list
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
     * @return
     */
    public Map<String, Event> getEventMap() {
      return this.eventMap;
    }








    /**
     * For loop for looping through the event list -- to be modified later
     */
    public void eventLoop(){
      Map<String, Event> events = this.getEventMap();
      for (Map.Entry<String, Event> entry : events.entrySet()) {
        String eventName = entry.getKey();
        Event event = entry.getValue();
        // do something with the event
      }
    }


  /**
   * Converts hours and time blocks into minutes of the day
   * @param hour - hour of the day
   * @param block - 15 minute block within the hour
   * @return - integer representing the number
   */
  public int getMinuteOfDay(int hour, int block) {
    return hour * 60 + block * 15;
  }

  }
