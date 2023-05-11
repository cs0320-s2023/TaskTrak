import { useEffect, useState } from "react";
import "./App.css";
import {
  Button,
  Grid,
  Tab,
  Tabs,
  Typography,
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
          // let eventSet = new Set(calendarItems);
          // setCalendarItems([...eventSet]);

          setTasks(tasks.concat(jsonTasksData));
          // let taskSet = new Set(tasks);
          // setTasks([...taskSet]);
          // console.log(taskSet)
          // console.log(tasks)

          console.log("events")
          console.log(calendarItems);
          console.log("tasks")
          console.log(tasks);
        })
        .catch((error) => {
          console.error("ERROR!", error);
        })
        // .then(() => {
        //   // let eventIdSet = new Set(calendarItems.map((item) => {
        //   //   return item.id;
        //   // }));

        //   // console.log(eventIdSet);

        //   // const filteredEvents = calendarItems.filter((item) => {
        //   //   if(eventIdSet.has(item.id)) {
        //   //     eventIdSet.delete(item.id);
        //   //     return item;
        //   //   }
        //   //   else if(!eventIdSet.has(item.id)) {
        //   //     console.log(`duplicate item found!`);
        //   //     console.log(item);
        //   //   }
        //   // })

        //   // // console.log(filteredEvents);

        //   // setCalendarItems(filteredEvents);
        // })
      // setCalendarItems([userEvents, ...calendarItems])
    })

    // setTasks(tasks.map())
  }, [])

  useEffect(() => {
    let eventIdSet = new Set(calendarItems.map((item) => {
      return item.id;
    }));

    console.log(eventIdSet); 

    const filteredEvents = calendarItems.filter((item) => {
      if(eventIdSet.has(item.id)) {
        eventIdSet.delete(item.id);
        return item;
      }
      else if(!eventIdSet.has(item.id)) {
        console.log(`duplicate item found!`);
        console.log(item);
      }
    })

    // console.log(filteredEvents);

    setCalendarItems(filteredEvents); 
  }, calendarItems)

  const viewOptions = ["calendar", "tasks"];

  return (
    <>
    <Typography variant="h3" fontFamily='sans-serif' textAlign='center' color='whitesmoke'>
      <strong>TaskTrak</strong>
    </Typography>
      <Grid
        container
        spacing={8}
        className="calendars"
        justifyContent="center"
        alignItems="center"
      >
        <Grid item xs={9}>
          <Tabs value={pageView} onChange={(event, value) => setPageView(value)} sx={{ bgcolor: 'white' }}>
            {viewOptions.map((item) => (
              <Tab value={item} label={item} ></Tab>
            ))}
          </Tabs>
        </Grid>
        {/* <Grid item xs={2}>
          
        </Grid> */}
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
