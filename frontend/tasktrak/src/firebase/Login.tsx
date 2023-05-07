import React, { useState } from "react";
import { auth } from "./config";
import { signInWithEmailAndPassword } from "firebase/auth";
import { TextField, Button } from "@mui/material";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

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
          <TextField
            autoFocus
            id="email"
            type="email"
            label="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <TextField
            id="password"
            type="password"
            label="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button type="submit">Login</Button>
        </form>
      )}
    </>
  );
};

export default Login;
