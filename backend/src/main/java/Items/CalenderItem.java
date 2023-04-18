package Items;

import java.util.Date;

public interface CalenderItem {
  void setName(String name); // sets the name of the item
  String getName(); // gets the name of the item
  void setDescription(String description); // sets the description of the item
  String getDescription(); // gets the description of the item
  void setDate(Date date); // sets the Date of the item
  Date getDate(); // gets the data of the item
  void setNotes(String notes); // sets the notes of the item (comments, links, names, etc.)
  String getNotes(); // gets the notes of the item
}