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
import java.time.LocalDateTime;
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

      Map<String, Object> docData = new HashMap<>();
      docData.put("title", event.getName());
      docData.put("startTime", event.getStartTime().toLocalTime().toString());
      docData.put("endTime", event.getEndTime().toLocalTime().toString());
      docData.put("dateSpan",new ArrayList<String>(List.of(startDate,endDate)));

      docData.put("notes", event.getNotes());
      docData.put("isAllDay", event.getIsAllDay());
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

  public ArrayList<List<Map<String,Object>>> retrieveCalendar(String userTokenID) throws FirebaseAuthException{
    DocumentReference userRef = getUserRef(userTokenID);
    ApiFuture<QuerySnapshot> eventsQuery = userRef.collection("events").get();
    ApiFuture<QuerySnapshot> tasksQuery = userRef.collection("tasks").get();

    List<Map<String, Object>> events = new ArrayList<>();
    List<Map<String, Object>> tasks = new ArrayList<>();
    try {
      for (DocumentSnapshot doc : eventsQuery.get().getDocuments()) {
        events.add(doc.getData());
      }
      for (DocumentSnapshot doc : tasksQuery.get().getDocuments()) {
        tasks.add(doc.getData());
      }
    } catch (Exception e) {
      System.err.println("Getting data failed for unknown reason: " + e);
    }
    return new ArrayList(List.of(events,tasks));

  }

  public ArrayList<List<String>> retrieveADayTimes(LocalDateTime dateTime,String userTokenID) throws FirebaseAuthException{
    DocumentReference userRef = getUserRef(userTokenID);
    CollectionReference events = userRef.collection("events");


    ApiFuture<QuerySnapshot> queryStart = events.whereIn("dateSpan",List.of(dateTime)).get();
    try {
      ArrayList<List<String>> times = new ArrayList<>();
      for (DocumentSnapshot doc : queryStart.get().getDocuments()) {
        String startDate = doc.get("startDate").toString();
        String endDate = doc.get("endDate").toString();
        times.add(List.of(startDate,endDate));
      }
      System.out.println(times);
      return times;
    } catch (Exception e) {
      System.err.println("Document getting was interrupted");
      return new ArrayList<>();
    }
//    events.whereIn()

  }

  private DocumentReference getUserRef(String tokenID) throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenID);
    String userID = decodedToken.getUid();
    return db.collection("users").document(userID);
  }

  //TASK STUFF
  public void createFirebaseTask(Task task, String tokenID) throws FirebaseException{
    // try {
      DocumentReference userRef = db.collection("users").document("testUser4"); //getUserRef(tokenID);
      DocumentReference taskRef =
          userRef.collection("tasks").document(task.getTaskID().toString());

      Map<String, Object> docData = new HashMap<>();
      docData.put("title", task.getName());
      docData.put("dueDate", task.getDueDate().toString());
      docData.put("notes", task.getNotes());
      docData.put("duration", task.getTimeToComplete());
      docData.put("priority", task.getPriority().getValue());
      docData.put("isComplete", task.getIsComplete());

      ApiFuture<WriteResult> test = taskRef.set(docData); //SET FAILING FOR SOME REASON
    // }
    // catch (FirebaseAuthException e) {
    //   throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Invalid user token ID.",e.getCause());
    // } catch (FirebaseException e) {
    //   throw new FirebaseException(ErrorCode.INVALID_ARGUMENT, "Firebase: Task data unable to be set. This may be due to passing the wrong types.", e.getCause());
    // }
  }

  public void deleteFirebaseTask(Integer taskID, String tokenID) throws FirebaseAuthException{
    DocumentReference userRef = getUserRef(tokenID);
    userRef.collection("events").document(taskID.toString()).delete();
  }
}