//package Response;
//
//import java.util.HashMap;
//
//public class MapResponse {
//
//  public static void constructResponse() {
//    Moshi moshi = new Moshi.Builder().build();
//
//    Map<String, Object> failResponse = new HashMap();
//    failResponse.put("result", result);
//    failResponse.put("cause", cause);
//
//    Type mapOfStringObjectType = Types.newParameterizedType(Map.class, String.class, Object.class);
//    JsonAdapter<Map<String,Object>> adapter = moshi.adapter(mapOfStringObjectType);
//    return adapter.toJson(failResponse);
//  }
//
//}
