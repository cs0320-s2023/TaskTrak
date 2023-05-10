import { Box, Button, Card, Grid, Paper } from '@mui/material';
import React from "react";
import SignUp from './signUp';
import Login from './Login';
import { auth } from "./config"

export default function UserAccount(){
    

    return(
        <>
        {/*!auth.currentUser &&*/ (
                <Grid container
                    direction="column"
                    justifyContent="center"
                    alignItems="center"
                    marginTop={10}
                >
                    <h1>Login</h1>
                    <Grid item xs={6}>
                        <Login/>
                    </Grid>
                    <Grid item xs={6}>
                        <SignUp/>
                    </Grid>
                </Grid>
            )
        }
        {/* {
            auth.currentUser && (
                <Grid container
                    direction="column"
                    justifyContent="center"
                    alignItems="center"
                    marginTop={10}
                >
                    <Grid item xs={6}>
                        <h1>hi</h1>
                    </Grid>
                </Grid>
            )
        } */}
        </>
    );
}