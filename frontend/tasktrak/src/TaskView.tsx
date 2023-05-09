import { Grid, Tabs, Tab, Button, MenuItem, Menu } from "@mui/material";
import React, { useState } from "react";
import TaskList from "./TaskList";
import { Task, sampleTasks } from "./CalendarItem";
import { TaskMenu } from "./TaskCard";

interface TaskViewProps{
    tasks: Task[];
    setTasks: (tasks: Task[]) => void;
}

export default function TaskView(props: TaskViewProps){
export default function TaskView() {
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
                <TaskList tasks={props.tasks} setTasks={props.setTasks}></TaskList>
            </Grid>
            <Grid item xs={4}>
                <TaskMenu tasks={props.tasks}></TaskMenu>
            </Grid>
        </Grid>
    )
  return (
    <Grid
      container
      spacing={6}
      className="tasks"
      justifyContent="center"
      alignItems="center"
    >
      <Grid item xs={8}>
        <TaskList tasks={tasks}></TaskList>
      </Grid>
      <Grid item xs={4}>
        <TaskMenu tasks={tasks} createAppointmentFromTask={}></TaskMenu>
      </Grid>
    </Grid>
  );
}
