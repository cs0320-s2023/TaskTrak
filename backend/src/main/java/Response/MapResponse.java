package Response;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Response;

// Custom Moshi adapter for LocalTime
class LocalTimeAdapter {
  @ToJson
  String toJson(LocalTime localTime) {
    return localTime.toString();
  }

  @FromJson
  LocalTime fromJson(String localTime) {
    return LocalTime.parse(localTime);
  }
}
public class MapResponse {

  public static String constructErrorResponse(Response response) {
    Moshi moshi = new Moshi.Builder().build();

    Map<String, Object> failResponse = new HashMap();
    failResponse.put("result", "error");
    failResponse.put("cause", response.status() + ": " + response.body());

    Type mapOfStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String,Object>> adapter = moshi.adapter(mapOfStringObjectType);
    return adapter.toJson(failResponse);
  }

  public static String constructSuccessResponse(Object result) {
    Moshi moshi = new Moshi.Builder().add(new LocalTimeAdapter()).build();

    Map<String, Object> successResponse = new HashMap();
    successResponse.put("result", "success");
    successResponse.put("cause", result);

    Type mapOfStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String,Object>> adapter = moshi.adapter(mapOfStringObjectType);
    return adapter.toJson(successResponse);
  }

  public static String constructSuccessResponse(List events, List tasks) {
    Moshi moshi = new Moshi.Builder().add(new LocalTimeAdapter()).build();

    Map<String, Object> successResponse = new HashMap();
    successResponse.put("result", "success");
    successResponse.put("events", events);
    successResponse.put("tasks", tasks);

    Type mapOfStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String,Object>> adapter = moshi.adapter(mapOfStringObjectType);
    return adapter.toJson(successResponse);
  }

}
