import { Grid, Tabs, Tab, Button, MenuItem, Menu } from "@mui/material";
import React, { useState } from "react";
import TaskList from "./TaskList";
import { Task, sampleTasks } from "./CalendarItem";
import { TaskMenu } from "./TaskCard";
import { Link } from "react-router-dom";
import { auth } from "./firebase/config";

// interface TaskViewProps{
//     tasks: Task[];
//     setTasks: (tasks: Task[]) => void;
// }

export default function TaskView(){
    const [tasks, setTasks] = useState(sampleTasks);

    function handleLogout(){
        auth.signOut();
    }

    return(
        <Grid
            container
            spacing={6}
            className="tasks"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={11}>
                <Tabs value='tasks'>
                    <Tab value='calendar' label='Calendar' to='/calendar' component={Link}></Tab>
                    <Tab value='tasks' label='Tasks' to='/tasks' component={Link}></Tab>
                </Tabs>
            </Grid>
            <Grid item xs={1}>
            {
              auth.currentUser ?
              <Button onClick={handleLogout} component={Link} to="/calendar">Log Out</Button> :
              <Button component={Link} to="/signin">Sign In</Button>
            }
          </Grid>
            <Grid item xs={8}>
                <TaskList tasks={tasks} setTasks={setTasks}></TaskList>
            </Grid>
            <Grid item xs={4}>
                <TaskMenu tasks={tasks}></TaskMenu>
            </Grid>
        </Grid>
    )
}