import { collection, addDoc, setDoc, query, where, getDocs} from "firebase/firestore";
import { getFirestore } from 'firebase/firestore/lite';
import CalendarItem from '../CalendarItem'

const db = getFirestore();

async function addEvent(event : CalendarItem) {
    try {
        const currUser =
        const userRef = collection(db,"users")
        const userQuery = query(userRef,where("id","==",userID))
        const userQuerySnapshot = await getDocs(userQuery)

        const eventRef = collection(userDoc,"events")        
    } catch (e) {

    }
}

// async function addTask(task, userID : string) {

// }

//Likely need to call this when using Firebase authentication
async function addUser() {
    try {
        const docRef = await addDoc(collection(db, "users"), {
            first: "Ada",
            last: "Lovelace",
            born: 1815,
        });
        console.log("Document written with ID: ", docRef.id);
    } catch (e) {
        console.error("Error adding document: ", e);
    }
}

// import {
//   getDatabase,
//   set,
//   ref,
//   onValue,
//   push,
//   update,
//   child,
// } from "firebase/database";

// //Keep in mind, write functionally instead of as methods (tree shaking), more efficient
// function writeUserData(userID: string, calendarJson) {
//   const database_ref = getDatabase();
//   //assumes that JSON structure will be "users" outermost then userID to store data for each user
//   set(ref(database_ref, "users/" + userID), calendarJson);
//   //Set vs. Update: set rewrites entire subsection, update changes only what you say to
// }

// function updateCalendarItem(userID: string, calendarItemJson) {
//   const db_ref = getDatabase();
// }

// /**
//  * Create calendar item within database
//  * @param userID
//  * @param calendarItemJson
//  */
// function addCalendarItem(userID: string, calendarItemJson) {
//   const db_ref = getDatabase();
//   //We need to determine json structure!
//   //Gets key for new calendar item
//   const newItemKey = push(child(ref(db_ref),"calendar?")).key;

//   const updates = {};
// //   updates[""]
// }

// //Listens for changes to database and retrieves data
// //Likely want to make this user specific only for stuff that can be changed
// //Potentially, if we make updates in the future, we should also make one for formatting
// const db_ref = getDatabase();
// const generalCalendarRef = ref(db_ref, "users/" + "testUserID");
// onValue(generalCalendarRef, (snapshot) => {
//   const calendarData = snapshot.val();
// });
