package records;

import com.squareup.moshi.Json;

public class calendarRecords {

    public record EventsJson(
          @Json(name = "events") Event[] events
    ){}

    public record Date(
        @Json(name = "year") int year,
        @Json(name = "monthIndex") int monthIndex,
        @Json(name = "date") int date,
        @Json(name = "hours") int hours,
        @Json(name = "minutes") int minutes
    ){}


  public record Event(
      @Json(name = "title") String title,
      @Json(name = "startDate") Date startDate,
      @Json(name = "endDate") Date endDate,
      @Json(name = "id") int id
  ){}
}
