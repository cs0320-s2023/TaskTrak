import Paper from '@mui/material/Paper';
import {
  Scheduler,
  MonthView,
  Appointments,
  AppointmentForm,
  Toolbar,
  DateNavigator,
  TodayButton,
  ConfirmationDialog,
  EditRecurrenceMenu,
  DragDropProvider
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState, EditingState, ChangeSet, IntegratedEditing } from '@devexpress/dx-react-scheduler';
import React from 'react';
import CalendarItem from './CalendarItem';

interface MonthlyCalendarProps {
    currentDate: Date;
    setCurrentDate: (currentDate: Date) => void;

    calendarItems: CalendarItem[];
    setCalendarItems: (calendarItems: CalendarItem[]) => void;
}

export default function MonthlyCalendar(props: MonthlyCalendarProps){
    
    function currentDateChange(currentDate: Date){
        console.log('clicked');
        props.setCurrentDate(currentDate);
    }

    function commitChanges({ added, changed, deleted }: ChangeSet){
        if(added){
            const startingAddedID = props.calendarItems[props.calendarItems.length-1].id + 1;
            props.setCalendarItems([...props.calendarItems, {
                id: startingAddedID,
                title: 'title',
                startDate: new Date(),
                endDate: new Date(),
                ...added}])
        }

        if(changed){
            props.setCalendarItems(props.calendarItems.map(appointment => (
                changed[appointment.id] ? { ...appointment, ...changed[appointment.id] } : appointment
            )))
        }

        if(deleted){
            props.setCalendarItems(props.calendarItems.filter(appointment => appointment.id !== deleted))
        }
    }
    
    return(
        <Paper className='month-view'>
            <Scheduler data={props.calendarItems}>
                <ViewState
                    currentDate={props.currentDate}
                    onCurrentDateChange={currentDateChange}
                />
                <EditingState onCommitChanges={commitChanges}/>
                <EditRecurrenceMenu/>
                <MonthView />
                <Toolbar />
                <DateNavigator />
                <TodayButton />
                <ConfirmationDialog />
                <Appointments/>
                <AppointmentForm />
                {/* the updating on dragging and dropping is broken; it completely breaks down in the day view
                when it's done in  */}
                <DragDropProvider/>
            </Scheduler>
        </Paper>
    )
}