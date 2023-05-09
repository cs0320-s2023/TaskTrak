// TaskMenu.tsx
import React from "react";
import {
  Card,
  CardContent,
  CardHeader,
  CircularProgress,
  Grid,
  Typography,
  Button,
  Fab,
} from "@mui/material";
import { Task } from "./CalendarItem";

interface TaskMenuProps {
  tasks: Task[];
  createAppointmentFromTask: (task: Task) => void;
}

function getPriorityColor(priority: number): string {
  switch (priority) {
    case 0:
      return "green";
    case 1:
      return "orange";
    case 2:
      return "red";
    default:
      return "gray";
  }
}
function getPriorityLabel(priority: number): string {
  switch (priority) {
    case 0:
      return "Low";
    case 1:
      return "Moderate";
    case 2:
      return "High";
    default:
      return "Unknown";
  }
}

export const TaskMenu: React.FC<TaskMenuProps> = ({ tasks }) => {
  const sortedTasks = tasks.sort((taskA, taskB) => {
    if (taskA.dueDate.getTime() === taskB.dueDate.getTime()) {
      return taskB.priority - taskA.priority;
    }
    return taskA.dueDate.getTime() - taskB.dueDate.getTime();
  });

  return (
    <Grid
      container
      spacing={2}
      direction="column"
      justifyContent="flex-end"
      alignItems="flex-end"
    >
      {sortedTasks.map((task, index) => (
        <Grid item key={index} xs={12} sm={6} md={4} lg={3}>
          <Card>
            <CardHeader
              title={
                <div style={{ display: "flex", alignItems: "center" }}>
                  <Typography variant="h5" component="div">
                    {task.name}
                  </Typography>
                  <div
                    style={{
                      backgroundColor: getPriorityColor(task.priority),
                      borderRadius: "50%",
                      width: "15px",
                      height: "15px",
                      marginLeft: "8px",
                    }}
                  />
                </div>
              }
              subheader={`${task.dueDate.toLocaleDateString()} | Priority: ${getPriorityLabel(
                task.priority
              )}`}
              action={
                <>
                  <CircularProgress
                    variant="determinate"
                    value={task.progress}
                  />
                </>
              }
            />
            <CardContent>
              <Typography variant="body2" color="text.secondary">
                Duration: {task.duration} hours
              </Typography>
              <Grid container spacing={1} justifyContent="center">
                {typeof task.timeSuggestions == "object" &&
                  task.timeSuggestions.slice(0, 4).map((timePair, idx) => (
                    <Grid item key={idx}>
                      <Fab
                        variant="extended"
                        color="primary"
                        size="small"
                        onClick={() => createAppointmentFromTask(task)}
                      >
                        {timePair[0]} - {timePair[1]}
                      </Fab>
                    </Grid>
                  ))}
              </Grid>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};
