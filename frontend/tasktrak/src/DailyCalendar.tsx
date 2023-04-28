import Paper from '@mui/material/Paper';
import {
  Scheduler,
  DayView,
  Appointments,
  AppointmentForm,
  ConfirmationDialog,
  EditRecurrenceMenu,
  DragDropProvider,
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState, EditingState, ChangeSet, IntegratedEditing } from '@devexpress/dx-react-scheduler';
import React from 'react';
import CalendarItem from './CalendarItem';

interface DailyCalendarProps {
    currentDate: Date;

    calendarItems: CalendarItem[];
    setCalendarItems: (calendarItems: CalendarItem[]) => void;
}

export default function DailyCalendar(props: DailyCalendarProps){
    function commitChanges({ added, changed, deleted }: ChangeSet){
        if(added){
            const startingAddedID = props.calendarItems[props.calendarItems.length-1].id + 1;
            props.setCalendarItems([...props.calendarItems, {
                id: startingAddedID,
                title: 'title',
                startDate: new Date(),
                endDate: new Date(),
                priority: 0,
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
        <Paper className='day-view'>
            <Scheduler data={props.calendarItems} height={690}>
                <ViewState currentDate={props.currentDate}/>
                <EditingState onCommitChanges={commitChanges}/>
                <EditRecurrenceMenu/>
                <DayView startDayHour={0} endDayHour={24} cellDuration={60} />
                <ConfirmationDialog />
                <Appointments />
                <AppointmentForm
                    basicLayoutComponent={(props) => (
                        <AppointmentForm.BasicLayout 
                            {...props}
                            customAttributeEditor={<input value={props.appointmentData.customAttribute} />}
                        />
                    )}
                />
                <DragDropProvider/>
            </Scheduler>
        </Paper>
    )
}