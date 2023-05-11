// TaskMenu.tsx
import React, {useState} from "react";
import {
  Card,
  CardContent,
  CardHeader,
  Grid,
  Typography,
  Fab,
  Paper,
  List,
  ListItem,
  ListItemText,
  ListItemButton,
  Dialog,
  DialogTitle,
  DialogContent,
  Select,
  Slider,
  TextField,
  CircularProgress,
  Divider,
  Button,
  TextFieldProps,
  DialogContentText,
} from "@mui/material";
import { Task } from "./CalendarItem";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { auth } from "./firebase/config";
import {CalendarItem} from './CalendarItem'

interface TaskMenuProps {
  tasks: Task[];
  calendarItems: CalendarItem[];
  setCalendarItems: (calendarItems: CalendarItem[]) => void;
  // createAppointmentFromTask: (task: Task) => void;
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

export const TaskMenu: React.FC<TaskMenuProps> = (props: TaskMenuProps) => {
  const sortedTasks = props.tasks.sort((taskA, taskB) => {
    if (taskA.dueDate.getTime() === taskB.dueDate.getTime()) {
      return taskB.priority - taskA.priority;
    }
    return taskA.dueDate.getTime() - taskB.dueDate.getTime();
  });
  const [open, setOpen] = useState(false);
  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [timePair1, setTimePair1] = useState("");
  const [timePair2, setTimePair2] = useState("");

  function handleOpen(){
    setOpen(true);
  }

  function handleClose(){
    setOpen(false);
  }

  const requestOptions = {
    method: "POST",
  };
  function handleCalClick(){
    addTaskToCalendar();
    handleClose();
  }

  async function addTaskToCalendar(){

    if (selectedTask !== null){
      console.log(timePair1);
      console.log(timePair2);
    let sHourString = timePair1.slice(0, 3);
    console.log(timePair1)
    let sMinString = timePair1.slice(3, 5);
    let fHourString = timePair2.slice(0, 3);
    let fMinString = timePair2.slice(3, 5);

    console.log(sHourString)
    console.log(sMinString)
    console.log(fHourString)
    console.log(fMinString)

    let sHour = parseInt(sHourString);
    let sMin = parseInt(sMinString);
    let fHour = parseInt(fHourString);
    let fMin = parseInt(fMinString);
    // new Date(2023, 3, 23, 15, 15);
    let d = selectedTask.dueDate


    let startDate: Date = new Date(d.getFullYear(), d.getMonth(), d.getDate(), sHour, sMin);
    let finishDate: Date = new Date(d.getFullYear(), d.getMonth(), d.getDate(), fHour, fMin);

    console.log(startDate);
    console.log(finishDate);

    const startingAddedID = props.calendarItems.length == 0 ? 0 :
        props.calendarItems[props.calendarItems.length - 1].id + 1;
    const newCalendarItem: CalendarItem = {
      title: selectedTask.name,
      allDay: false,
      startDate: startDate,
      endDate: finishDate,
      repeat: "false",
      id: startingAddedID,
      priority: selectedTask.priority,
      notes: selectedTask.notes
    }
    props.setCalendarItems([... props.calendarItems, newCalendarItem]);

    const userTokenID = auth.currentUser
    ?.getIdToken(true)
    .then((userTokenID) =>
      fetch(
        `http://localhost:3030/createEvent?` +
          `title=${selectedTask.name}&` +
          `startDate=${startDate.toISOString()}&` +
          `endDate=${finishDate.toISOString()}&` +
          `id=${startingAddedID}&` +
          `notes=${selectedTask.notes}&` +
          `isAllDay=${false}&` +
          `isRepeated=${false}&` +
          `tokenID=${userTokenID}`,
        requestOptions
      )
      
    );
    setSelectedTask(null);
    setTimePair1("");
    setTimePair2("");
  }
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
                  task.timeSuggestions.slice(0, 6).map((timePair, idx) => (
                    <Grid item key={idx}>
                      <Fab
                        variant="extended"
                        color="primary"
                        size="small"
                        onClick={() => {
                          handleOpen(); 
                          setSelectedTask(task);
                          setTimePair1(timePair[0]);
                          setTimePair2(timePair[1]);
                        }
                      }
                      >
                        {timePair[0]} - {timePair[1]}
                      </Fab>
                      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add Task to Calendar</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Task Name"
            value={task.name}
            // onChange={(e) => setTaskName(e.target.value)}
            fullWidth
            variant="standard"
          />

          <DialogContentText>Due Date</DialogContentText>
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
              value={task.dueDate}
              onChange={(newValue) => {
                if (newValue) {
                  new Date();
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
            value={task.priority}
            // onChange={(_, value) => setPriority(value as 0 | 1 | 2)}
            marks={marks}
          />
          <DialogContentText>Duration</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            type="number"
            label="Hours"
            value={task.duration}
            // onChange={(e) => setDuration(parseInt(e.target.value))}
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
            value={task.notes}
            // onChange={(e) => setNotes(e.target.value)}
            fullWidth
            variant="standard"
          />
          <Button onClick={() => handleCalClick()}>
            Add Task
          </Button>
        </DialogContent>
      </Dialog>
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
