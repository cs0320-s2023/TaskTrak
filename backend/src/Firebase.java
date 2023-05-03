import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Firebase {
    // Use a service account
    InputStream serviceAccount = new FileInputStream("backend/private/tasktrak-c6e87-firebase-adminsdk-mtf84-cd7c4ba400.json");
    GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(credentials)
        .build();
    FirebaseApp app = initializeApp(options);
    Firestore db = getFirestore(app);

    // def addEvent(event : CalendarItem) {
    //     try {
    //         let userID: string;
    //         const user = auth.currentUser;
    //         if (user == null) {
    //             return; //NO user logged in
    //         }
    //         else {
    //             userID = user.uid;
    //         }
            
    //         const userRef = collection(db,"users")
    //         const userQuery = query(userRef,where("id","==",userID))
    //         const userQuerySnapshot = await getDocs(userQuery)
    //         const userDoc = userQuerySnapshot.docs[0].data();
    
    //         const eventRef = collection(db,userID + "/events")        
    //     } catch (Exception e) {
    
    //     }
    // }
    
    // async function addTask(task, userID : string) {
    
    // }
    
    //Likely need to call this when using Firebase authentication
    def addNewUser(String userID) {
        try {
            DocumentReference docRef = document(collection(db),userID);
                    
            // const docRef = await addDoc(collection(db, "users"), {
            //     first: "Ada",
            //     last: "Lovelace",
            //     born: 1815,
            // });
            // console.log("Document written with ID: ", docRef.id);
        } catch (Exception e) {
            console.error("Error adding document: ", e);
        }
    }
}