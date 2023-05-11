import React, { useState } from "react";
import { auth } from "./config";
import { signInWithEmailAndPassword } from "firebase/auth";
import { TextField, Button, Grid, Box, Card, Paper } from "@mui/material";
import { redirect, useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await signInWithEmailAndPassword(auth, email, password);
      // Clear input fields
      setEmail("");
      setPassword("");
      // Set success message and clear any error message
      setSuccessMessage("Login successful!");
      setErrorMessage("");
    //   console.log(`logged in ${auth.currentUser?.email}`)
      console.log(auth.currentUser?.getIdToken());
      navigate("/calendar");
    } catch (error) {
      // Display error message and clear any success message
      setErrorMessage(
        "Login failed. Please check your credentials and try again."
      );
      setSuccessMessage("");
    }
  };

  return (
    <>
      {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      {!successMessage && (
        <form onSubmit={handleLogin}>
            <Grid container
                direction="column"
                justifyContent="center"
                alignItems="center"
                spacing={2}
            >
                    <Grid item xs={12}>
                        <TextField
                            autoFocus
                            id="email"
                            type="email"
                            label="Email"
                            variant="filled"
                            sx={{ bgcolor: 'white' }}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            id="password"
                            type="password"
                            label="Password"
                            variant="filled"
                            sx={{ bgcolor: 'white' }}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}> {/*not sure why this wont go in the center we'll figire it out*/}
                        <Button type="submit">Login</Button>
                    </Grid>
            </Grid>
        </form>
      )}
    </>
  );
};

export default Login;
