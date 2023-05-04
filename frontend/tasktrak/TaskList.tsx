import { List, ListItem, ListItemText, Paper } from '@mui/material';
import { Task } from './src/CalendarItem';
import React from 'react';

interface TaskListProps {
    tasks: Task[];
    setTasks: (tasks: Task[]) => void;
}

export default function TaskList(){
    return(
        <Paper className='task-list-view'>
            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                <ListItem>
                    <ListItemText></ListItemText>
                </ListItem>
            </List>
        </Paper>
    )
}