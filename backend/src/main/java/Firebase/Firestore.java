package Firebase;

import static com.google.firebase.FirebaseApp.initializeApp;
import static com.google.firebase.cloud.FirestoreClient.getFirestore;

import Items.Event;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
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
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.units.qual.A;

public class Firestore {
  private com.google.cloud.firestore.Firestore db;
  private String testingTokenID;

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
      this.testingTokenID = FirebaseAuth.getInstance().createCustomToken("123456789");
    } catch (FileNotFoundException e) {
      System.err.println("File not found");
    } catch (SecurityException e) {
      System.err.println("Security????");
    } catch (FirebaseAuthException e) {
      System.err.println("Failed to create token");
    }
    catch (IOException e) {
      System.err.println("Something else wrong");
    }
  }

  public void createEventFirebase(Event event, String tokenID) throws FirebaseAuthException{
      DocumentReference userRef = getUserRef(tokenID);
      DocumentReference eventRef = userRef.collection("events").document(event.getId().toString());
      //  const userQuery = query(userRef,where("id","==",userID)) //Will be used with token to get userID?
      //  const userQuerySnapshot = await getDocs(userQuery)
      //  const userDoc = userQuerySnapshot.docs[0].data();

      Map<String, Object> docData = new HashMap<>();
      docData.put("title", event.getName());
      docData.put("startTime", event.getStartTime().toLocalTime().toString());
      docData.put("endTime", event.getEndTime().toLocalTime().toString());
      docData.put("startDate", event.getStartTime().toLocalDate().toString());
      docData.put("endDate", event.getEndTime().toLocalDate().toString());
      docData.put("notes", event.getNotes());
      docData.put("isAllDay", event.getIsAllDay());
      ApiFuture<WriteResult> test = eventRef.set(docData); //SET FAILING FOR SOME REASON

        //  const eventRef = collection(db,userID + "/events")
//     } catch (Exception e) {
//       System.err.println("FAILED: " + e);
//     }
   }

  //Likely need to call this when using Firebase authentication
  public void addNewUser(String userID) {
      try {
          DocumentReference docRef = db.collection("users").document(userID);

          // const docRef = await addDoc(collection(db, "users"), {
          //     first: "Ada",
          //     last: "Lovelace",
          //     born: 1815,
          // });
          // console.log("Document written with ID: ", docRef.id);
      } catch (Exception e) {
          System.err.println("Error adding document: " + e);
      }
  }

  public ArrayList<String[]> retrieveADayTimes(LocalDateTime dateTime,String userTokenID) throws FirebaseAuthException{
    DocumentReference userRef = getUserRef(userTokenID);
    CollectionReference events = userRef.collection("events");
//    events.whereIn()
    return new ArrayList<>();


  }

  private DocumentReference getUserRef(String tokenID) throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenID);
    String userID = decodedToken.getUid();
    return db.collection("users").document(userID);
  }
}