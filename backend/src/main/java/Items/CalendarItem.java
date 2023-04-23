package Items;

import java.time.LocalDateTime;
import java.util.Date;

public interface CalendarItem {
  void setName(String name); // sets the name of the item
  String getName(); // gets the name of the item
  void setNotes(String notes); // sets the notes of the item (comments, links, names, etc.)
  String getNotes(); // gets the notes of the item
//  Boolean getIsComplete();
//  void changeCompletion();

}