import { Grid, Tabs, Tab, Button, MenuItem, Menu } from "@mui/material";
import React, { useState } from "react";
import TaskList from "./TaskList";
import { Task, sampleTasks } from "./CalendarItem";
import { TaskMenu } from "./TaskCard";

export default function TaskView(){
    const [tasks, setTasks] = useState(sampleTasks);

    return(
        <Grid
            container
            spacing={6}
            className="tasks"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={8}>
                <TaskList tasks={tasks} setTasks={setTasks}></TaskList>
            </Grid>
            <Grid item xs={4}>
                <TaskMenu tasks={tasks}></TaskMenu>
            </Grid>
        </Grid>
    )
}