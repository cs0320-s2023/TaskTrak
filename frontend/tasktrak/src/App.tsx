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
  Tab,
  Tabs,
} from "@mui/material";
// import SignUp from "./firebase/signUp";
import MonthlyCalendar from "./MonthlyCalendar";
import DailyCalendar from "./DailyCalendar";
import WeeklyCalendar from "./WeeklyCalendar";
import { sampleCalendarItems, sampleTasks } from "./CalendarItem";
import { CalendarItem } from "./CalendarItem";
import { onAuthStateChanged, User } from "firebase/auth";
import firebase from "firebase/compat/app";
import * as firebaseui from "firebaseui";
import "firebaseui/dist/firebaseui.css";
import { AuthProvider } from "./firebase/provider/AuthProvider";
import { TaskMenu } from "./TaskCard";
import TaskList from "./TaskList";
import React from "react";
import ReactDOM from "react-dom";
import { Link } from "react-router-dom";
import { auth } from "./firebase/config";
import TaskView from "./TaskView";

function App(): JSX.Element {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [viewMode, setViewMode] = useState<"month" | "week">("month");
  // const [user, setUser] = useState<User | null>(null);
  const [pageView, setPageView] = useState<"calendar" | "tasks">("calendar");
  const [calendarItems, setCalendarItems] = useState(sampleCalendarItems);
  const [tasks, setTasks] = useState(sampleTasks);

  function toggleViewMode() {
    setViewMode(viewMode === "month" ? "week" : "month");
  }

  function handleLogout(){
    auth.signOut();
    setCalendarItems([]);
    setTasks([]);
  }

  const calendarViewMenu: React.ReactNode[] = [
    <Button
      id="basic-button"
      aria-controls="button"
      aria-haspopup="false"
      onClick={toggleViewMode}
    >
      {viewMode === "month" ? "Switch to Week View" : "Switch to Month View"}
    </Button>
  ];

  // useEffect(() => {
  //   const unsubscribe = auth.onAuthStateChanged((user: User | null) => {
  //     // setUser(user);
  //     // console.log(`user set to ${user?.email}`)
  //   });

  //   return () => {
  //     unsubscribe();
  //     console.log("unsub")
  //   };
  // }, []);

  useEffect(() => {
    auth.onAuthStateChanged((user) => {
      const userEvents = auth.currentUser
        ?.getIdToken(true)
        .then((userTokenID) =>
          fetch(
            `http://localhost:3030/login?tokenID=${userTokenID}`
          )
        )
        .then((response) => {
          if(response.ok){ return response.json() }
          else { throw new Error('API response failed!') } // good ?
        })
        .then((data) => {
          // console.log(data);
        })
      // setCalendarItems()
    })
  })

  const viewOptions = ["calendar", "tasks"];

  return (
    <>
      <Grid
        container
        spacing={6}
        className="calendars"
        justifyContent="center"
        alignItems="center"
      >
        <Grid item xs={11}>
            <Tabs value={pageView} onChange={(event, value) => setPageView(value)}>
              {viewOptions.map((item) => (
                <Tab value={item} label={item}></Tab>
              ))}
            </Tabs>
          </Grid>
          <Grid item xs={1}>
            {
              auth.currentUser ?
              <Button onClick={handleLogout} component={Link} to="/calendar">Log Out</Button> :
              <Button component={Link} to="/signin">Sign In</Button>
            }
          </Grid>
      </Grid>
      {pageView == "calendar" && (
        <Grid
          container
          spacing={6}
          className="calendars"
          justifyContent="center"
          alignItems="center"
        >
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
        </Grid>
      )}
      {pageView == "tasks" && (
        <TaskView tasks={tasks} setTasks={setTasks} calendarItems={calendarItems} setCalendarItems={setCalendarItems}></TaskView>
      )}
    </>
  );
}

export default App;
