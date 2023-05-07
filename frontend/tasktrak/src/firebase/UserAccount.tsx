import { Button, Grid } from '@mui/material';
import React from "react";
import SignUp from './signUp';
import Login from './Login';

export default function UserAccount(){
    function handleLogin(){
        return(
            <Login/>
        )
    }

    return(
        <Grid container
            direction="column"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={6}>
                {/* <SignUp/> */}
                <Button>Login to existing account</Button>
            </Grid>
            <Grid item xs={6}>
                {/* <Login/> */}
                <Button>Create new account</Button>
            </Grid>
        </Grid>
    )
}