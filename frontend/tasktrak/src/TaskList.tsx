import {
  Paper,
  List,
  ListItem,
  ListItemText,
  ListItemButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  Select,
  Slider,
  TextField,
  CircularProgress,
  Divider,
  Button,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { Task } from "./CalendarItem";
import React, { useState } from "react";

interface TaskListProps {
  tasks: Task[];
  setTasks: (tasks: Task[]) => void;
}

export default function TaskList(props: TaskListProps) {
  const [open, setOpen] = useState(false);

  const [taskName, setTaskName] = useState("");
  const [dueDate, setDueDate] = useState<Date | null>(null);
  const [priority, setPriority] = useState(0);
  const [duration, setDuration] = useState(0);

  function handleNewTaskButton() {
    setOpen(true);
  }
  function handleSave() {
    const newTask: Task = {
      id: Math.random().toString(36).substr(2, 9), // Generate a random ID
      name: taskName,
      dueDate: dueDate || new Date(),
      priority: priority,
      duration: duration,
      completion: 0, // Default completion value
      notes: "",
    };

    props.setTasks([...props.tasks, newTask]);

    // Reset input values
    setTaskName("");
    setDueDate(null);
    setPriority(0);
    setDuration(0);

    // Close the dialog
    handleClose();
  }

  function handleClose() {
    setOpen(false);
  }

  const marks = [
    {
      value: 0,
      label: "Low",
    },
    {
      value: 1,
      label: "Medium",
    },
    {
      value: 2,
      label: "High",
    },
  ];

  return (
    <Paper className="task-list-view">
      <Button onClick={handleNewTaskButton}>New Task</Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>New Task</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Task Name"
            value={taskName}
            onChange={(e) => setTaskName(e.target.value)}
            fullWidth
            variant="standard"
          />

          <DialogContentText>Due Date</DialogContentText>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              value={dueDate}
              onChange={(newValue) => setDueDate(newValue)}
            />
          </LocalizationProvider>
          <DialogContentText>Priority</DialogContentText>
          <Slider
            min={0}
            max={2}
            step={1}
            value={priority}
            onChange={(_, value) => setPriority(value as 0 | 1 | 2)}
            marks={marks}
          />
          <DialogContentText>Duration</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            type="number"
            label="Hours"
            value={duration}
            onChange={(e) => setDuration(parseInt(e.target.value))}
            fullWidth
            variant="standard"
          />
        </DialogContent>
      </Dialog>
      <List sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}>
        <ListItem alignItems="center">
          <ListItemButton>
            <ListItemText primary="Task" />
          </ListItemButton>
          <Divider orientation="vertical" flexItem></Divider>
          <ListItemButton>
            <ListItemText primary="Due Date" />
          </ListItemButton>
          <Divider orientation="vertical" flexItem></Divider>
          <ListItemButton>
            <ListItemText primary="Priority" />
          </ListItemButton>
          <Divider orientation="vertical" flexItem></Divider>
          <ListItemButton>
            <ListItemText primary="Completion" />
          </ListItemButton>
        </ListItem>
      </List>
      {props.tasks.map((task) => (
        <List
          sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}
        >
          <ListItem>
            <ListItemButton>
              <ListItemText primary={task.name} secondary={task.notes} />
            </ListItemButton>
            <ListItemButton>
              <ListItemText
                primary={
                  `${task.dueDate.getMonth()}/` +
                  `${task.dueDate.getDate()}/` +
                  `${task.dueDate.getFullYear()}`
                }
                secondary={task.notes}
              />
            </ListItemButton>
            <ListItemButton>
              <ListItemText primary={task.priority} />
            </ListItemButton>
            <ListItemButton>
              <CircularProgress variant="determinate" value={50} />
            </ListItemButton>
          </ListItem>
        </List>
      ))}
    </Paper>
  );
}

// function open
