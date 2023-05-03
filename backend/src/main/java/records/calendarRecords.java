package records;

import com.squareup.moshi.Json;

public class calendarRecords {

    public record EventsJson(
          @Json(name = "events") Event[] events
    ){}

    public record Date(
        @Json(name = "year") Integer year,
        @Json(name = "monthIndex") Integer monthIndex,
        @Json(name = "date") Integer date,
        @Json(name = "hours") Integer hours,
        @Json(name = "minutes") Integer minutes
    ){}


  public record Event(
      @Json(name = "title") String title,
      @Json(name = "startDate") Date startDate,
      @Json(name = "endDate") Date endDate,
      @Json(name = "id") Integer id
  ){}
}
