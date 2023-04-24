import Paper from '@mui/material/Paper';
import {
  Scheduler,
  DayView,
  MonthView,
  Appointments,
  Toolbar,
  DateNavigator,
  TodayButton,
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState } from '@devexpress/dx-react-scheduler';
import { useState } from 'react';
import './App.css';
import { Button } from '@mui/material';
import MonthlyCalendar from './MonthlyCalendar';
import DailyCalendar from './DailyCalendar';
import React from 'react';
import { sampleCalendarItems } from './CalendarItem';
import CalendarItem from './CalendarItem';

function App() {
  const [currentDate, setCurrentDate] = useState(new Date());

  // could be updated with default value of CalendarItems[] if i move it to its own file ?
  const [calendarItems, setCalendarItems] = useState(sampleCalendarItems)

  return (
    <div className='calendars'>
      <MonthlyCalendar
        currentDate={currentDate}
        setCurrentDate={setCurrentDate}
        calendarItems={calendarItems}
        setCalendarItems={setCalendarItems}
        />
      <DailyCalendar
        currentDate={currentDate}
        calendarItems={calendarItems}
        setCalendarItems={setCalendarItems}
        />
    </div>
    
  );
}

export default App;
