import { Paper, List, ListItem, ListItemText, ListItemButton, Dialog, CircularProgress } from '@mui/material';
import { Task } from './CalendarItem';
import React from 'react';

interface TaskListProps {
    tasks: Task[];
    setTasks: (tasks: Task[]) => void;
}

export default function TaskList(props: TaskListProps){
    return(
        <Paper className='task-list-view'>
            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                    <ListItem>
                        <ListItemButton>
                            <ListItemText primary="Task"/>
                        </ListItemButton>
                        <ListItemButton>
                            <ListItemText primary="Due Date"/>
                        </ListItemButton>
                        <ListItemButton>
                            <ListItemText primary="Priority"/>
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