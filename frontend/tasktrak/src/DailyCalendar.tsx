import Paper from '@mui/material/Paper';
import {
  Scheduler,
  DayView
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState } from '@devexpress/dx-react-scheduler';
import React from 'react';

interface DailyCalendarProps {
    currentDate: string;
}

export default function DailyCalendar(props: DailyCalendarProps){
    return(
        <Paper className='day-view'>
            <Scheduler>
                <ViewState currentDate={props.currentDate}/>
                <DayView startDayHour={0} endDayHour={24}/>
            </Scheduler>
        </Paper>
    )
}