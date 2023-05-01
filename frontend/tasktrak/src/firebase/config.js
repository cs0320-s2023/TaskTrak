// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getFirestore } from 'firebase/firestore/lite';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyA3ZjDAjeqIvvk48D3Bjo_2mugZXf1FLOo",
  authDomain: "tasktrak-c6e87.firebaseapp.com",
  databaseURL: "https://tasktrak-c6e87-default-rtdb.firebaseio.com",
  projectId: "tasktrak-c6e87",
  storageBucket: "tasktrak-c6e87.appspot.com",
  messagingSenderId: "10871974966",
  appId: "1:10871974966:web:40b188f77dddf1e26e3c97",
  measurementId: "G-4G33Q6P6E8",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

const db = getFirestore(app);

// const db = getDatabase();
// set(ref(db, "users/testUser1"), {
//   calendar: { events: { event1: "TestInfo" } },
// });

// writeUserData("testUser1", { calendar: { events: { event1: "TestInfo" } } });
//Ask about module bundling
