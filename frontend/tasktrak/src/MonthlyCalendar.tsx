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
  DragDropProvider
} from '@devexpress/dx-react-scheduler-material-ui';
import { ViewState, EditingState, ChangeSet, IntegratedEditing, SelectOption } from '@devexpress/dx-react-scheduler';
import React from 'react';
import CalendarItem from './CalendarItem';
import './MonthlyCalendar.css';

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

    function onValueChange(){
    }

    // const BasicLayout = () => {
    //     const onCustomFieldChange = (nextValue) => {
    //       onFieldChange({ customField: nextValue });
    //     };
      
    //     return (
    //       <AppointmentForm.BasicLayout
    //         // {...AppointmentForm.BasicLayout.defaultProps}
    //         // appointmentData={appointmentData}
    //         // onFieldChange={onFieldChange}
    //         // {...restProps}
    //       >
    //         <AppointmentForm.Label
    //           text="Custom Field"
    //           type='titleLabel'
    //         />
    //         <AppointmentForm.TextEditor
    //           value={appointmentData.customField}
    //           onValueChange={onCustomFieldChange}
    //           placeholder="Custom field"
    //           readOnly={false}
    //           type='numberEditor'
    //         />
    //       </AppointmentForm.BasicLayout>
    //     );
    //   };

    const customAttribute: SelectOption = {id: 5000, text: 'Custom Attribute'}
    
    return(
        // <ResizableBox
        //     width={1400}
        //     height={1000}
        //     // draggableOpts={{}}
        //     minConstraints={[1000, 750]}
        //     maxConstraints={[1400, 1000]}
        //     className='box'
        //     >
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
                    <Appointments />
                    <AppointmentForm
                        basicLayoutComponent={(props) => (
                            <AppointmentForm.BasicLayout 
                                {...props}
                                customAttributeEditor={<Input value={props.appointmentData.customAttribute} />}
                            >
                                <AppointmentForm.BooleanEditor
                                    {...props}
                                    onValueChange={onValueChange}
                                    label='Custom Attribute'
                                />
                                {/* <AppointmentForm.Select
                                    {...props}
                                    onValueChange={onValueChange}
                                    availableOptions={}
                                    label='Custom Attribute'
                                /> */}
                            </AppointmentForm.BasicLayout>
                        )}
                        // selectComponent={(props) => (
                        //     <AppointmentForm.Select
                        //         {...props}
                        //         availableOptions={[...[customAttribute]]}
                        //         readOnly={true}
                        //     />
                        // )}
                        // booleanEditorComponent={(props) => (
                        //     <AppointmentForm.BooleanEditor
                        //         {...props}
                        //         label='Custom Attribute'
                        //     />
                        // )}
                    />
                    {/* the updating on dragging and dropping is broken; it completely breaks down in the day view
                    when it's done in  */}
                    <DragDropProvider/>
                </Scheduler>
            </Paper>
        // </ResizableBox>
    )
}