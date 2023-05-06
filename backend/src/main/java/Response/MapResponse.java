package Response;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import spark.Response;

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

  public static String constructSuccessResponse(String result) {
    Moshi moshi = new Moshi.Builder().build();

    Map<String, Object> successResponse = new HashMap();
    successResponse.put("result", "success");
    successResponse.put("cause", result);

    Type mapOfStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String,Object>> adapter = moshi.adapter(mapOfStringObjectType);
    return adapter.toJson(successResponse);
  }

}
