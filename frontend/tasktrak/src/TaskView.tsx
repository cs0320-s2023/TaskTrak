import { Grid, Tabs, Tab, Button, MenuItem, Menu } from "@mui/material";
import React, { useState } from "react";
import TaskList from "./TaskList";
import { Task, sampleTasks } from "./CalendarItem";
import { TaskMenu } from "./TaskCard";
import {CalendarItem} from './CalendarItem'

interface TaskViewProps{
    tasks: Task[];
    setTasks: (tasks: Task[]) => void;
    calendarItems: CalendarItem[];
    setCalendarItems: (calendarItems: CalendarItem[]) => void;
}

export default function TaskView(props: TaskViewProps){

    return(
        <Grid
            container
            spacing={6}
            className="tasks"
            justifyContent="center"
            alignItems="center"
        >
            <Grid item xs={2}></Grid>
            <Grid item xs={6}>
                <TaskList tasks={props.tasks} setTasks={props.setTasks}></TaskList>
            </Grid>
            <Grid item xs={2}>
                <TaskMenu tasks={props.tasks} calendarItems={props.calendarItems} setCalendarItems={props.setCalendarItems}></TaskMenu>
            </Grid>
            <Grid item xs={2}></Grid>
        </Grid>
    )
}
