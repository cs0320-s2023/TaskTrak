import Paper from '@mui/material/Paper';
import {
  Scheduler,
  MonthView,
  Appointments,
  Toolbar,
  DateNavigator,
  TodayButton,
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState } from '@devexpress/dx-react-scheduler';
import React from 'react';

interface MonthlyCalendarProps {
    currentDate: Date;
    setCurrentDate: (currentDate: Date) => void;
}

export default function MonthlyCalendar(props: MonthlyCalendarProps){

    function currentDateChange(currentDate: Date){
        console.log('clicked');
        props.setCurrentDate(currentDate);
    }
    
    return(
        <Paper className='month-view'>
            <Scheduler>
                <ViewState
                    currentDate={props.currentDate}
                    onCurrentDateChange={currentDateChange}
                />
                <MonthView/>
                <Toolbar/>
                <DateNavigator></DateNavigator>
                <TodayButton />
                <Appointments/>
            </Scheduler>
        </Paper>
    )
}