package Firebase;

import static com.google.firebase.FirebaseApp.initializeApp;
import static com.google.firebase.cloud.FirestoreClient.getFirestore;

import Items.Event;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

  public void createEventFirebase(Event event) {
    try {
//             let userID: string;
//             const user = auth.currentUser;
//             if (user == null) {
//                 return; //NO user logged in
//             }
//             else {
//                 userID = user.uid;
//             }


      String userID = "testUser1";
      System.err.println("idk");
      DocumentReference userRef = db.collection("users").document(userID);
      DocumentReference eventRef = userRef.collection("events").document("0");
      //  const userQuery = query(userRef,where("id","==",userID)) //Will be used with token to get userID?
      //  const userQuerySnapshot = await getDocs(userQuery)
      //  const userDoc = userQuerySnapshot.docs[0].data();

      System.err.println("Failed before");
      Map<String, Object> docData = new HashMap<>();
      docData.put("title", event.getName());
      docData.put("startTime", event.getStartTime().toString());
      docData.put("endTime", event.getEndTime().toString());
      docData.put("notes",event.getNotes());
      System.err.println("docData: " + docData);
      ApiFuture<WriteResult> test = eventRef.set(docData); //SET FAILING FOR SOME REASON
      System.err.println("written: " + test.get());

        //  const eventRef = collection(db,userID + "/events")
     } catch (Exception e) {
       System.err.println("FAILED: " + e);
     }
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
}