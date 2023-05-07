import React, { useState } from "react";
import { auth } from "./config";
import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";
import { TextField, Button } from "@mui/material";

// const auth = getAuth();

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSignup = async (e: React.FormEvent<HTMLFormElement>) => {
    console.log(email);
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
    <form onSubmit={handleSignup}>
      <TextField
        autoFocus
        id="email sign-up"
        type="email"
        label="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <TextField
        autoFocus
        id="password sign-up"
        type="password"
        label="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <TextField
        autoFocus
        id="confirm-password"
        type="password"
        label="Confirm Password"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
      />
      <Button type="submit">Signup</Button>
    </form>
  );
};

export default SignUp;
