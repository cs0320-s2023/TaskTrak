import React, { useState } from "react";
import { auth } from "./config";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { TextField, Button, Grid } from "@mui/material";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showForm, setShowForm] = useState(false);

  const handleSignup = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      console.error("Passwords don't match");
      return;
    }
    try {
      await createUserWithEmailAndPassword(auth, email, password);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      {!showForm && <Button onClick={() => setShowForm(true)}>Create New Account</Button>}
      {showForm && (
        <form onSubmit={handleSignup}>
        <Grid container
        direction="column"
        justifyContent="center"
        alignItems="center"
        spacing={2}
        >
            {/* <form onSubmit={handleSignup}> */}
                <Grid item xs={12}>
                    <TextField
                        autoFocus
                        id="email sign-up"
                        type="email"
                        label="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        autoFocus
                        id="password sign-up"
                        type="password"
                        label="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        autoFocus
                        id="confirm-password"
                        type="password"
                        label="Confirm Password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </Grid>
                <Button type="submit">Create New Account</Button>
            {/* </form> */}
        </Grid>
        </form>
      )}
    </>
  );
};

export default SignUp;
