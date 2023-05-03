import React, { useState } from 'react';
import { TextField, Button } from "@mui/material";
import firebaseui from 'firebaseui';
import firebase from 'firebase/compat';

export default function signUp(){
    const uiConfig: firebaseui.auth.Config = {
        callbacks: {
            signInSuccessWithAuthResult: (authResult, redirectUrl) => {
                return true;
            },
            uiShown: () => {
                const loader = document.getElementById('loader');
                if(loader){
                    loader.style.display = 'none';
                }
            }
        },
        signInFlow: 'popup',
        signInSuccessUrl: 'index.html', // TEMPORARY I THINK
        signInOptions: [
            firebase.auth.EmailAuthProvider.PROVIDER_ID,
            firebase.auth.GoogleAuthProvider.PROVIDER_ID
        ]
    }
    
    const ui = new firebaseui.auth.AuthUI(firebase.auth());
    
    ui.start('#firebaseui-auth-container', uiConfig)

    return(
        <div id='firebaseui-auth-container'/>
    );
}