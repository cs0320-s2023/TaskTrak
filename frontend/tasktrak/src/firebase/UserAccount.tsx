import { Box, Button, Card, Grid, Paper } from '@mui/material';
import React from "react";
import SignUp from './signUp';
import Login from './Login';

export default function UserAccount(){
    

    return(
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