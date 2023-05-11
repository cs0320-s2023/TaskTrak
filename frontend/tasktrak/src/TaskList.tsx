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
  TextFieldProps,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { Task } from "./CalendarItem";
import React, { useState } from "react";
import { getAuth } from "firebase/auth";
import { TaskMenu } from "./TaskCard";

interface TaskListProps {
  tasks: Task[];
  setTasks: (tasks: Task[]) => void;
}

export default function TaskList(props: TaskListProps) {
  const [open, setOpen] = useState(false);

  const [taskName, setTaskName] = useState("");
  const [dueDate, setDueDate] = useState<Date>(new Date());
  const [priority, setPriority] = useState(0);
  const [duration, setDuration] = useState(0);
  const [notes, setNotes] = useState("");
  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [index, setEditIndex] = useState<number | null>(null);

  function handleNewTaskButton() {
    setOpen(true);
  }

  function handleEditButtonClick(task: Task) {
    setSelectedTask(task);
    setTaskName(task.name);
    setDueDate(new Date(task.dueDate));
    setPriority(task.priority);
    setDuration(task.duration);
    setNotes(task.notes);
    setOpen(true);
  }

  async function handleEditSave() {
    if (selectedTask != null) {
      try {
        selectedTask.name = taskName;
        selectedTask.dueDate = dueDate;
        selectedTask.priority = priority;
        selectedTask.duration = duration;
        selectedTask.isComplete = false;
        selectedTask.notes = notes;

        const userTokenID = await getAuth().currentUser?.getIdToken(true);
        const filteredTasks = props.tasks.filter(
          (task) => task.id !== selectedTask.id
        );

        const response = await fetch(
          `http://localhost:3030/editTask?` +
            `name=${taskName}&` +
            `dueDate=${dueDate.toISOString()}&` +
            `priority=${priority}&` +
            `newDuration=${duration}&` +
            `isComplete=${false}&` +
            `id=${selectedTask.id}&` +
            `notes=${notes}&` +
            `tokenid=${userTokenID}`,
          {
            method: "POST",
          }
        );
        const taskTimeSuggestions = await response.json();

        const success: string[][] = taskTimeSuggestions["cause"];
        console.log("Response:", success);

        selectedTask.timeSuggestions = success;

        props.setTasks([...filteredTasks, selectedTask]);
        setTaskName("");
        setDueDate(new Date());
        setPriority(0);
        setDuration(0);
        handleClose();
        setSelectedTask(null);
      } catch (error) {
        console.log(error);
      }
    }
  }

  async function handleSave() {
    const startingAddedID =
      props.tasks.length == 0 ? 0 : props.tasks[props.tasks.length - 1].id + 1;
    const newTask: Task = {
      // id:
      name: taskName,
      dueDate: dueDate,
      priority: priority,
      duration: duration,
      // completion: 0, // Default completion value
      isComplete: false,
      notes: notes,
      id: startingAddedID,
      timeSuggestions: [],
      
      progress: 0,
    };

    try {
      const userTokenID = await getAuth().currentUser?.getIdToken(true);
      const response = await fetch(
        `http://localhost:3030/createTask?` +
          `name=${taskName}&` +
          `dueDate=${dueDate.toISOString()}&` +
          `priority=${priority}&` +
          `duration=${duration}.0&` +
          `isComplete=${false}&` +
          `id=${startingAddedID}&` +
          `notes=${notes}&` +
          `tokenID=${userTokenID}`,
        {
          // mode: "no-cors",
          method: "POST",
          // headers: {
          //   "Content-Type": "application/json",
          // },
        }
      );

      // if (!response.ok) {
      //   throw new Error("Failed to save task");
      // }

      const taskTimeSuggestions = await response.json();

      const success: string[][] = taskTimeSuggestions["cause"];
      console.log("Response:", success);

      newTask.timeSuggestions = success;

      props.setTasks([...props.tasks, newTask]);

      // Reset input values
      setTaskName("");
      setDueDate(new Date());
      setPriority(0);
      setDuration(0.0);
      // Close the dialog
      handleClose();
    } catch (error) {
      console.error("Error saving the task:", error);
      // You can handle the error here, e.g. show an error message to the user
    }
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
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
              value={dueDate}
              onChange={(newValue) => {
                if (newValue) {
                  setDueDate(newValue);
                }
              }}
              renderInput={(props) => <TextField {...props} />}
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
          <DialogContentText></DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="notes"
            type="string"
            label="notes"
            value={notes}
            onChange={(e) => setNotes(e.target.value)}
            fullWidth
            variant="standard"
          />
          <Button onClick={selectedTask != null ? handleEditSave : handleSave}>
            Save
          </Button>
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
          key={task.id}
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
              <CircularProgress variant="determinate" value={0} />
              <ListItemButton onClick={() => handleEditButtonClick(task)}>
                Edit
              </ListItemButton>
            </ListItemButton>
          </ListItem>
        </List>
      ))}
    </Paper>
  );
}

// function open
