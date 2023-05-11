package Firebase;

import static com.google.firebase.FirebaseApp.initializeApp;
import static com.google.firebase.cloud.FirestoreClient.getFirestore;

import Items.Event;
import Items.Task;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firestore {
  private com.google.cloud.firestore.Firestore db;
  public Firestore() {
    try {
      InputStream serviceAccount = new FileInputStream(
          "private/tasktrak-c6e87-firebase-adminsdk-mtf84-cd7c4ba400.json");
      GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(credentials)
          .build();
      FirebaseApp app = initializeApp(options);
      this.db = getFirestore(app);
    } catch (FileNotFoundException e) {
      System.err.println("File not found");
    } catch (SecurityException e) {
      System.err.println("Security????");
    }
    catch (IOException e) {
      System.err.println("Something else wrong");
    }
  }

  public void createEventFirebase(Event event, String tokenID) throws FirebaseException{
    try {
      DocumentReference userRef = getUserRef(tokenID);
      DocumentReference eventRef = userRef.collection("events").document(event.getId().toString());

      String startDate = event.getStartTime().toLocalDate().toString();
      String endDate = event.getEndTime().toLocalDate().toString();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      Map<String, Object> docData = new HashMap<>();
      docData.put("title", event.getName());
      docData.put("startDate", event.getStartTime().format(formatter));
      docData.put("endDate", event.getEndTime().format(formatter));
      docData.put("dateSpan",new ArrayList<String>(List.of(startDate,endDate)));
      docData.put("notes", event.getNotes());
      docData.put("allDay", event.getIsAllDay());
      ApiFuture<WriteResult> test = eventRef.set(docData); //SET FAILING FOR SOME REASON
    } catch (FirebaseAuthException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Invalid user token ID.",e.getCause());
    } catch (FirebaseException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Event data unable to be set. This may be due to passing the wrong types.", e.getCause());
    }
  }

   public void deleteFirebaseEvent(String eventID, String tokenID) throws FirebaseAuthException{
     DocumentReference userRef = getUserRef(tokenID);
     userRef.collection("events").document(eventID).delete();
   }

  //Likely need to call this when using Firebase authentication
  public void addNewUser(String userID) {
      try {
          DocumentReference docRef = db.collection("users").document(userID);

      } catch (Exception e) {
          System.err.println("Error adding document: " + e);
      }
  }

  public ArrayList<List<Map<String,Object>>> retrieveCalendar(String userTokenID) throws FirebaseException{
    try {
      DocumentReference userRef = getUserRef(userTokenID);
      ApiFuture<QuerySnapshot> eventsQuery = userRef.collection("events").get();
      ApiFuture<QuerySnapshot> tasksQuery = userRef.collection("tasks").get();

      List<Map<String, Object>> events = new ArrayList<>();
      List<Map<String, Object>> tasks = new ArrayList<>();

      for (DocumentSnapshot doc : eventsQuery.get().getDocuments()) {
        Map<String,Object> eventData = doc.getData();
        eventData.put("id", Integer.parseInt(doc.getId()));
        events.add(eventData);
      }
      for (DocumentSnapshot doc : tasksQuery.get().getDocuments()) {
        Map<String,Object> taskData = doc.getData();
        taskData.put("id", Integer.parseInt(doc.getId()));
        tasks.add(taskData);
      }
      return new ArrayList(List.of(events,tasks));
    } catch (FirebaseAuthException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Invalid user token ID.",
          e.getCause());
    } catch (NumberFormatException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Task or event ID unable to be parsed to integer.", e.getCause());
    } catch (Exception e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Getting data failed for some reason", e.getCause());
    }

//    return new ArrayList(List.of(events,tasks));
  }


  public ArrayList<List<LocalDateTime>> retrieveADayTimes(LocalDateTime dateTime,String userTokenID) throws FirebaseAuthException{
    DocumentReference userRef = getUserRef(userTokenID);
    CollectionReference events = userRef.collection("events");
//    System.out.println("Gets here");
    LocalDate currentDate = dateTime.toLocalDate();
    ApiFuture<QuerySnapshot> queryStart = events.whereArrayContains("dateSpan",currentDate.toString()).whereEqualTo("allDay", false).get();

//    System.err.println("TEST: " + queryStart);
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);
      ArrayList<List<LocalDateTime>> times = new ArrayList<>();

      for (DocumentSnapshot doc : queryStart.get().getDocuments()) {
        LocalDateTime startDate = LocalDateTime.parse(doc.get("startDate").toString(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(doc.get("endDate").toString(), formatter);
        times.add(List.of(startDate, endDate));
      }
      System.out.println(times);
      return times;
    } catch (DateTimeParseException e) {
      System.err.println("Invalid time parsed");
      return new ArrayList<>();
    } catch (Exception e) {
      System.err.println("Document getting was interrupted");
      return new ArrayList<>();
    }
  }


  public String getUserId(String tokenID) throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenID);
    return decodedToken.getUid();
  }

  private DocumentReference getUserRef(String tokenID) throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenID);
    String userID = decodedToken.getUid();
    return db.collection("users").document(userID);
  }

  //TASK STUFF
  public void createFirebaseTask(Task task, String tokenID) throws FirebaseException{
    try {
      DocumentReference userRef = getUserRef(tokenID);
      DocumentReference taskRef =
          userRef.collection("tasks").document(task.getTaskID().toString());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          .withZone(ZoneOffset.UTC);

      Map<String, Object> docData = new HashMap<>();
      docData.put("name", task.getName());
      docData.put("dueDate", task.getDueDate().format(formatter));
      docData.put("notes", task.getNotes());
      docData.put("duration", task.getTimeToComplete());
      docData.put("priority", task.getPriority().getValue());
      docData.put("isComplete", task.getIsComplete());

      ApiFuture<WriteResult> test = taskRef.set(docData); //SET FAILING FOR SOME REASON
    }
    catch (FirebaseAuthException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Invalid user token ID.",e.getCause());
    } catch (FirebaseException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Task data unable to be set. This may be due to passing the wrong types.", e.getCause());
    }
  }

  public void deleteFirebaseTask(Integer taskID, String tokenID) throws FirebaseException{
    try {
      DocumentReference userRef = getUserRef(tokenID);
      userRef.collection("events").document(taskID.toString()).delete();
    }catch (FirebaseAuthException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Invalid user token ID.",e.getCause());
    } catch (FirebaseException e) {
      throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Task data unable to be set. This may be due to passing the wrong types.", e.getCause());
    }
  }
}