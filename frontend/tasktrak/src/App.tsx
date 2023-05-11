import { useEffect, useState } from "react";
import "./App.css";
import {
  Button,
  Grid,
  Tab,
  Tabs,
} from "@mui/material";
import MonthlyCalendar from "./MonthlyCalendar";
import DailyCalendar from "./DailyCalendar";
import { Task, sampleCalendarItems, sampleTasks } from "./CalendarItem";
import { CalendarItem } from "./CalendarItem";
import "firebaseui/dist/firebaseui.css";
import React from "react";
import { Link } from "react-router-dom";
import { auth } from "./firebase/config";
import TaskView from "./TaskView";

function App(): JSX.Element {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [pageView, setPageView] = useState<"calendar" | "tasks">("calendar");
  const [calendarItems, setCalendarItems] = useState<CalendarItem[]>(sampleCalendarItems);
  const [tasks, setTasks] = useState<Task[]>(sampleTasks);

  function handleLogout(){
    auth.signOut();
    setCalendarItems([]);
    setTasks([]);
  }

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
          if(response.ok){ return response.text() }
          else { throw new Error('API response failed!') } // good ?
        })
        .then((data) => {
          const reviver = (key: string, value: any): any => {
            if ((key == "startDate" || key == "endDate" || key == "dueDate") && typeof value === 'string') {
              const dateRegex = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.(\d{3})Z$/;
              if (dateRegex.test(value)) {
                const [, year, month, day, hours, minutes, seconds, milliseconds] = dateRegex.exec(value)!;
                return new Date(
                  Date.UTC(
                    parseInt(year, 10),
                    parseInt(month, 10) - 1,
                    parseInt(day, 10),
                    parseInt(hours, 10),
                    parseInt(minutes, 10),
                    parseInt(seconds, 10),
                    parseInt(milliseconds, 10)
                  )
                );
              }
            }
            return value;
          };

          let jsonData = JSON.parse(data);
          let jsonEventsString = JSON.stringify(jsonData.events);
          let jsonTasksString = JSON.stringify(jsonData.tasks);
          let jsonEventsData = JSON.parse(jsonEventsString, reviver);
          let jsonTasksData = JSON.parse(jsonTasksString, reviver);

          // console.log(jsonData.events);
          console.log(jsonEventsData);
          console.log("that was jsoneventsdata");
          console.log(jsonTasksData);
          console.log("that was jsontasksdata");

          setCalendarItems(calendarItems.concat(jsonEventsData));
          // setCalendarItems(calendarItems.filter((item, index) => {
          //   return calendarItems.indexOf(item) === index;
          // }))
          setTasks(tasks.concat(jsonTasksData));
          // setTasks(tasks.filter((item, index) => {
          //   return tasks.indexOf(item) == index;
          // }))
          console.log("events")
          console.log(calendarItems);
          console.log("tasks")
          console.log(tasks);
        })
        .catch((error) => {
          console.error("ERROR!", error);
        })
      // setCalendarItems([userEvents, ...calendarItems])
    })

    // setTasks(tasks.map())
  }, [])

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
            <MonthlyCalendar
                currentDate={currentDate}
                setCurrentDate={setCurrentDate}
                calendarItems={calendarItems}
                setCalendarItems={setCalendarItems}
              />
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
