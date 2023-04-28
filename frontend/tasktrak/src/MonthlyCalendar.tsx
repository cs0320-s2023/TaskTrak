import { Paper, Input } from '@mui/material';
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
  DragDropProvider,
  AllDayPanel
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState, EditingState, ChangeSet, IntegratedEditing, SelectOption } from '@devexpress/dx-react-scheduler';
import React from 'react';
import { CalendarItem } from './CalendarItem';
import './MonthlyCalendar.css';
import { useState } from 'react';

interface MonthlyCalendarProps {
    currentDate: Date;
    setCurrentDate: (currentDate: Date) => void;

    calendarItems: CalendarItem[];
    setCalendarItems: (calendarItems: CalendarItem[]) => void;


}

export default function MonthlyCalendar(props: MonthlyCalendarProps){
    
    function currentDateChange(currentDate: Date){
        props.setCurrentDate(currentDate);
    }
    const [customAttributeValue, setCustomAttributeValue] = useState<string | number>(0);

    function commitChanges({ added, changed, deleted }: ChangeSet){
        if(added){
            const startingAddedID = props.calendarItems[props.calendarItems.length-1].id + 1;
            props.setCalendarItems([...props.calendarItems, {
                id: startingAddedID,
                title: 'title',
                startDate: new Date(),
                endDate: new Date(),
                priority: 0, // Default priority value
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

    function onValueChange(nextValue: string | number){
        setCustomAttributeValue(nextValue);
    }

    const priorityOptions: SelectOption[] = [
        {id: 0, text: 'Low'},
        {id: 1, text: 'Moderate'},
        {id: 2, text: 'High'}
    ]
    
    return(
        <Paper className='month-view' >
            <Scheduler data={props.calendarItems}>
                <ViewState
                    currentDate={props.currentDate}
                    onCurrentDateChange={currentDateChange}
                />
                <EditingState onCommitChanges={commitChanges}/>
                <EditRecurrenceMenu />
                <MonthView />
                <Toolbar />
                <DateNavigator />
                <TodayButton />
                <ConfirmationDialog />
                <Appointments />
                <AppointmentForm
                    basicLayoutComponent={({onFieldChange, appointmentData, ...bl_props}) => (
                        <AppointmentForm.BasicLayout 
                            {...bl_props}
                            appointmentData={appointmentData}
                            onFieldChange={onFieldChange}
                        >
                            <AppointmentForm.Select
                                {...props}
                                value={appointmentData.prioritySelect}
                                onValueChange={(nextValue: string | number) => {
                                    onFieldChange({ prioritySelect: nextValue })
                                }}
                                availableOptions={priorityOptions}
                                type='outlinedSelect'
                                label='Custom Attribute'
                            />
                        </AppointmentForm.BasicLayout>
                    )}
                    // booleanEditorComponent={({onValueChange, ...allDay_props}) => (
                    //     <AppointmentForm.BooleanEditor
                    //     {...props}
                    //     value={allDay_props.value}
                    //     onValueChange={onValueChange}
                    //     />
                    // )}
                />
            </Scheduler>
        </Paper>
    )
}