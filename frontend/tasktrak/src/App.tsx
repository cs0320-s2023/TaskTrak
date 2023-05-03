import Paper from '@mui/material/Paper';
import {
  Scheduler,
  DayView,
  MonthView,
  Appointments,
  Toolbar,
  DateNavigator,
  TodayButton,
  WeekView,
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState } from '@devexpress/dx-react-scheduler';
import { useState, useEffect } from 'react';
import './App.css';
import { Button, ButtonTypeMap, ExtendButtonBase, Grid, Menu, MenuItem } from '@mui/material';
import MonthlyCalendar from './MonthlyCalendar';
import DailyCalendar from './DailyCalendar';
import WeeklyCalendar from './WeeklyCalendar'
import React from 'react';
import { sampleCalendarItems } from './CalendarItem';
import { CalendarItem } from './CalendarItem';
import { getAuth, onAuthStateChanged, User } from 'firebase/auth';

import firebase from 'firebase/compat/app';
import * as firebaseui from 'firebaseui';
import 'firebaseui/dist/firebaseui.css';
import { AuthProvider } from './firebase/provider/AuthProvider';

function App(): JSX.Element {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [viewMode, setViewMode] = useState<'month' | 'week'>('month');

  // could be updated with default value of CalendarItems[] if i move it to its own file ?
  const [calendarItems, setCalendarItems] = useState(sampleCalendarItems)

  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
        // console.log(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

  const calendarViewMenu: React.ReactNode[] = ([
    <Button
        id="basic-button"
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
    >
        testing button here
    </Button>,
    <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
            'aria-labelledby': 'basic-button',
        }}
    >
        <MenuItem onClick={handleClose}>Month View</MenuItem>
        <MenuItem onClick={handleClose}>Week View</MenuItem>
        <MenuItem onClick={handleClose}>Accessibility View</MenuItem>
    </Menu>
  ])

  function handleLogin(){
    
  }

  function toggleViewMode() {
    setViewMode(viewMode === 'month' ? 'week' : 'month');
  }
return (
  <AuthProvider>
    <Grid
      container
      spacing={6}
      className="calendars"
      justifyContent="center"
      alignItems="center"
    >
      <Grid item xs={11}>
        <Button onClick={toggleViewMode}>
          {viewMode === 'month' ? 'Switch to Week View' : 'Switch to Month View'}
        </Button>
      </Grid>
      <Grid item xs={1}>
        <Button>button</Button>
      </Grid>
      <Grid item xs={6}>
        {viewMode === 'month' ? (
          <MonthlyCalendar
            currentDate={currentDate}
            setCurrentDate={setCurrentDate}
            calendarItems={calendarItems}
            setCalendarItems={setCalendarItems}
            calendarViewMenu={calendarViewMenu}
          />
        ) : (
          <WeeklyCalendar
            currentDate={currentDate}
            setCurrentDate={setCurrentDate}
            calendarItems={calendarItems}
            setCalendarItems={setCalendarItems}
          />
        )}
      </Grid>
      <Grid item xs={4}>
        <DailyCalendar
          currentDate={currentDate}
          calendarItems={calendarItems}
          setCalendarItems={setCalendarItems}
        />
      </Grid>
    </Grid>
  </AuthProvider>
);
}

export default App;
