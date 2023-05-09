import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { AuthProvider } from "./firebase/provider/AuthProvider";
import reportWebVitals from "./reportWebVitals";
import Root from './routes/root';
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from "./firebase/Login";
import SignUp from "./firebase/signUp";
import UserAccount from "./firebase/UserAccount";
import TaskView from "./TaskView";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root></Root>
  },
  {
    path: "/calendar",
    element: <App/>
  },
  {
    path: "/signin",
    element: <UserAccount/>
  },
  {
    path: "/tasks",
    element: <TaskView/>
  }
])

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}/>
    </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
