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
    Button
} from '@mui/material'
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Task } from './CalendarItem';
import React, { useState } from 'react';

interface TaskListProps {
    tasks: Task[];
    setTasks: (tasks: Task[]) => void;
}

export default function TaskList(props: TaskListProps){
    const [open, setOpen] = useState(false);

    function handleNewTaskButton(){
        setOpen(true);
    }

    function handleClose(){
        setOpen(false);
    }

    const marks = [
        {
            value: 0,
            label: 'Low'
        },
        {
            value: 1,
            label: 'Medium'
        },
        {
            value: 2,
            label: 'High'
        }
    ]
    
    return(
        <Paper className='task-list-view'>
            <Button onClick={handleNewTaskButton}>New Task</Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>New Task</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Task Name"
                        fullWidth
                        variant="standard"
                    />
                    <DialogContentText>Due Date</DialogContentText>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker/>
                    </LocalizationProvider>
                    <DialogContentText>Priority</DialogContentText>
                    <Slider min={0} max={2} step={1} marks={marks}></Slider>
                    <DialogContentText>Duration</DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        type="number"
                        label="Hours"
                        fullWidth
                        variant="standard"
                    />
                </DialogContent>
            </Dialog>
            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                    <ListItem alignItems='center'>
                        <ListItemButton>
                            <ListItemText primary="Task"/>
                        </ListItemButton>
                        <Divider orientation='vertical' flexItem></Divider>
                        <ListItemButton>
                            <ListItemText primary="Due Date"/>
                        </ListItemButton>
                        <Divider orientation='vertical' flexItem></Divider>
                        <ListItemButton>
                            <ListItemText primary="Priority"/>
                        </ListItemButton>
                        <Divider orientation='vertical' flexItem></Divider>
                        <ListItemButton>
                            <ListItemText primary="Completion"/>
                        </ListItemButton>
                    </ListItem>
                </List>
            {props.tasks.map((task) => (
                <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                    <ListItem>
                        <ListItemButton>
                            <ListItemText primary={task.name} secondary={task.notes}/>
                        </ListItemButton>
                        <ListItemButton>
                            <ListItemText
                                primary={`${task.dueDate.getMonth()}/`+
                                    `${task.dueDate.getDate()}/`+
                                    `${task.dueDate.getFullYear()}`}
                                secondary={task.notes}/>
                        </ListItemButton>
                        <ListItemButton>
                            <ListItemText primary={task.priority}/>
                        </ListItemButton>
                        <ListItemButton>
                            <CircularProgress variant='determinate' value={50}/>
                        </ListItemButton>
                    </ListItem>
                </List>
            ))}
        </Paper>
    )
}

// function open 