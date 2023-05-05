// import React, { useState } from 'react';
// import { TextField, Button } from "@mui/material";
// import { auth } from 'firebaseui';
// // import { auth } from './config';
// import firebase from 'firebase/compat/app';
// import { EmailAuthProvider, GoogleAuthProvider } from 'firebase/auth';
// import { getFirestore } from 'firebase/firestore';
// import { firebaseConfig } from './config';

// export default function SignUp(){

//     const uiConfig: auth.Config = {
//         callbacks: {
//             signInSuccessWithAuthResult: (authResult, redirectUrl) => {
//                 return true;
//             },
//             uiShown: () => {
//                 const loader = document.getElementById('loader');
//                 if(loader){
//                     loader.style.display = 'none';
//                 }
//             }
//         },
//         signInFlow: 'popup',
//         signInSuccessUrl: 'index.html', // TEMPORARY I THINK
//         signInOptions: [
//             EmailAuthProvider.PROVIDER_ID,
//             GoogleAuthProvider.PROVIDER_ID
//         ]
//     }

//     // firebase.initializeApp(firebaseConfig);
//     // var db = getFirestore();
    
//     // const ui = new auth.AuthUI(firebase.auth());
//     const ui: auth.AuthUI = auth.AuthUI.getInstance() || new auth.AuthUI(firebase.auth());
    
//     ui.start('#firebaseui-auth-container', uiConfig)

//     return(
//         <div id='firebaseui-auth-container'>
//             <h1>test</h1>
//         </div>
//     );
// }