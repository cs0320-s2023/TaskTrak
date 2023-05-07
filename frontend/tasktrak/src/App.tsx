import Paper from "@mui/material/Paper";
import {
  Scheduler,
  DayView,
  MonthView,
  Appointments,
  Toolbar,
  DateNavigator,
  TodayButton,
  WeekView,
} from "@devexpress/dx-react-scheduler-material-ui";
import { ViewState } from "@devexpress/dx-react-scheduler";
import { useEffect, useState } from "react";
import "./App.css";
import {
  Button,
  ButtonTypeMap,
  ExtendButtonBase,
  Grid,
  Menu,
  MenuItem,
} from "@mui/material";
import SignUp from "./firebase/signUp";
import MonthlyCalendar from "./MonthlyCalendar";
import DailyCalendar from "./DailyCalendar";
import WeeklyCalendar from "./WeeklyCalendar";
import { sampleCalendarItems, sampleTasks } from "./CalendarItem";
import { CalendarItem } from "./CalendarItem";
import { getAuth, onAuthStateChanged, User } from "firebase/auth";
import firebase from "firebase/compat/app";
import * as firebaseui from "firebaseui";
import "firebaseui/dist/firebaseui.css";
import { AuthProvider } from "./firebase/provider/AuthProvider";
import { TaskMenu } from "./TaskCard";
import TaskList from "./TaskList";
import React from "react";
import ReactDOM from "react-dom";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./firebase/Login";
import UserAccount from "./firebase/UserAccount";

function App(): JSX.Element {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [viewMode, setViewMode] = useState<"month" | "week">("month");
  const [user, setUser] = useState<User | null>(null);

  function toggleViewMode() {
    setViewMode(viewMode === "month" ? "week" : "month");
  }

  const [loginFormOpen, setLoginFormOpen] = useState(false);

  const [calendarItems, setCalendarItems] = useState(sampleCalendarItems);

  const [tasks, setTasks] = useState(sampleTasks);

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
    // console.log(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const calendarViewMenu: React.ReactNode[] = [
    <Button
      id="basic-button"
      aria-controls={open ? "basic-menu" : undefined}
      aria-haspopup="true"
      aria-expanded={open ? "true" : undefined}
      onClick={toggleViewMode}
    >
      {viewMode === "month" ? "Switch to Week View" : "Switch to Month View"}
    </Button>,
    <Menu
      id="basic-menu"
      anchorEl={anchorEl}
      open={open}
      onClose={handleClose}
      MenuListProps={{
        "aria-labelledby": "basic-button",
      }}
    >
      <MenuItem onClick={handleClose}>Month View</MenuItem>
      <MenuItem onClick={handleClose}>Week View</MenuItem>
      <MenuItem onClick={handleClose}>Accessibility View</MenuItem>
    </Menu>,
  ];

  function handleLogin() {
    setLoginFormOpen(true);
  }

  useEffect(() => {
    const auth = getAuth();
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      setUser(user);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    // <AuthProvider>
    <>
      {!user && <AuthProvider />}
      {user && (
        <Grid
          container
          spacing={6}
          className="calendars"
          justifyContent="center"
          alignItems="center"
        >
          <Grid item xs={12}>
            <UserAccount></UserAccount>
          </Grid>
          <Grid item xs={11}>
            <Button onClick={handleClick}>Accessibility Mode</Button>
          </Grid>
          <Grid item xs={1}>
            <Button onClick={handleLogin}>Login</Button>
          </Grid>
          <Grid item xs={6}>
            {viewMode === "month" ? (
              <MonthlyCalendar
                currentDate={currentDate}
                setCurrentDate={setCurrentDate}
                calendarItems={calendarItems}
                setCalendarItems={setCalendarItems}
                calendarViewMenu={calendarViewMenu}
              />
            ) : (
              <WeeklyCalendar
                currentDate={currentDate}
                setCurrentDate={setCurrentDate}
                calendarItems={calendarItems}
                setCalendarItems={setCalendarItems}
                calendarViewMenu={calendarViewMenu}
              />
            )}
          </Grid>
          <Grid item xs={4}>
            <DailyCalendar
              currentDate={currentDate}
              calendarItems={calendarItems}
              setCalendarItems={setCalendarItems}
            />
          </Grid>
          <Grid item xs={6} md={7}>
            <TaskList tasks={tasks} setTasks={setTasks}></TaskList>
          </Grid>
          <Grid item xs={12} md={5}>
            <TaskMenu tasks={tasks} />
          </Grid>
        </Grid>
      )}
    </>
  );
}

export default App;
