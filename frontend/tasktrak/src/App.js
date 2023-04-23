import logo from './logo.svg';
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
import MonthlyCalendar from './MonthlyCalendar.tsx';
import DailyCalendar from './DailyCalendar.tsx';

function App() {
  const [currentDate, setCurrentDate] = useState(new Date());

  return (
    <div className='calendars'>
      <MonthlyCalendar currentDate={currentDate} setCurrentDate={setCurrentDate}/>
      <DailyCalendar currentDate={currentDate}/>
    </div>
    
  );
}

export default App;
