import { Grid } from '@mui/material';
import React from "react";
import SignUp from './SignUp';
import Login from './Login';

export default function UserAccount(){
    return(
        <Grid container
            direction="column"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={6}>
                <SignUp/>
            </Grid>
            <Grid item xs={6}>
                <Login/>
            </Grid>
        </Grid>
    )
}