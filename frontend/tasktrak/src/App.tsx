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
import { useState } from 'react';
import './App.css';
import { Button, Grid } from '@mui/material';
import MonthlyCalendar from './MonthlyCalendar';
import DailyCalendar from './DailyCalendar';
import WeeklyCalendar from './WeeklyCalendar'
import React from 'react';
import { sampleCalendarItems } from './CalendarItem';
import { CalendarItem } from './CalendarItem';

function App() {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [viewMode, setViewMode] = useState<'month' | 'week'>('month');

  // could be updated with default value of CalendarItems[] if i move it to its own file ?
  const [calendarItems, setCalendarItems] = useState(sampleCalendarItems)

  function toggleViewMode() {
  setViewMode(viewMode === 'month' ? 'week' : 'month');
}
return (
  <Grid
    container
    spacing={6}
    className="calendars"
    justifyContent="center"
    alignItems="center"
  >
    <Grid item xs={12}>
      <Button onClick={toggleViewMode}>
        {viewMode === 'month' ? 'Switch to Week View' : 'Switch to Month View'}
      </Button>
    </Grid>
    <Grid item xs={6}>
      {viewMode === 'month' ? (
        <MonthlyCalendar
          currentDate={currentDate}
          setCurrentDate={setCurrentDate}
          calendarItems={calendarItems}
          setCalendarItems={setCalendarItems}
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
);
}

export default App;
