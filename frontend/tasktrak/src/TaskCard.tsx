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
}

export const TaskMenu: React.FC<TaskMenuProps> = ({ tasks }) => {
  return (
    <Grid container spacing={2} direction="column" alignItems="flex-end">
      {tasks.map((task, index) => (
        <Grid item key={index} xs={12} sm={6} md={4} lg={3}>
          <Card>
            <CardHeader
              title={task.name}
              subheader={task.dueDate.toLocaleDateString()}
              action={<CircularProgress variant="determinate" value={100} />}
            />
            <CardContent>
              <Typography variant="body2" color="text.secondary">
                Duration: {task.duration} minutes
              </Typography>
              <Grid container spacing={1} justifyContent="center">
                {task.timeSuggestions.slice(0, 4).map((time, idx) => (
                  <Grid item key={idx}>
                    <Fab variant="extended" color="primary" size="small">
                      {time} min
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
