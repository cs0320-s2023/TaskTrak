import { Paper, Input, Button, Menu, MenuItem } from "@mui/material";
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
  AllDayPanel,
} from "@devexpress/dx-react-scheduler-material-ui";
import {
  ViewState,
  EditingState,
  ChangeSet,
  IntegratedEditing,
  SelectOption,
} from "@devexpress/dx-react-scheduler";
import React from "react";
import { CalendarItem, Task } from "./CalendarItem";
import "./MonthlyCalendar.css";
import { useState } from "react";
// import { getAuth } from "firebase/auth";
import { auth } from "./firebase/config";

interface MonthlyCalendarProps {
  currentDate: Date;
  setCurrentDate: (currentDate: Date) => void;

  calendarItems: CalendarItem[];
  setCalendarItems: (calendarItems: CalendarItem[]) => void;

  calendarViewMenu: React.ReactNode[];
}

export default function MonthlyCalendar(props: MonthlyCalendarProps) {
  const [formOpen, setFormOpen] = useState(false);
  const [formAppointment, setFormAppointment] = useState<CalendarItem | null>(
    null
  );

  function createAppointmentFromTask(task: Task) {
    setFormAppointment({
      title: task.name,
      startDate: task.dueDate,
      endDate: new Date(
        task.dueDate.getTime() + task.duration * 60 * 60 * 1000
      ), // Add the duration to the start date
      priority: task.priority,
      allDay: false,
      notes: "", // Fill this in if the Task has a corresponding field
      repeat: undefined,
      id: props.calendarItems[props.calendarItems.length - 1].id + 1,
      // Fill in other fields as necessary
    });
    setFormOpen(true);
  }

  function currentDateChange(currentDate: Date) {
    props.setCurrentDate(currentDate);
  }
  // const [customAttributeValue, setCustomAttributeValue] = useState<string | number>(0);

  const requestOptions = {
    method: "POST",
  };

  function commitChanges({ added, changed, deleted }: ChangeSet) {
    if (added) {
      console.log(props.calendarItems);
      const startingAddedID = props.calendarItems.length == 0 ? 0 :
        props.calendarItems[props.calendarItems.length - 1].id + 1;
      props.setCalendarItems([
        ...props.calendarItems,
        {
          id: startingAddedID,
          title: added.summary,
          startDate: added.startDate,
          endDate: added.endDate,
          priority: added.priority,
          allDay: added.allDay,
          repeat: added.rRule,
          notes: added.notes,
          ...added,
        },
      ]);
      let newEvent: CalendarItem =
        props.calendarItems[props.calendarItems.length - 1];
      const userTokenID = auth.currentUser
        ?.getIdToken(true)
        .then((userTokenID) =>
          fetch(
            `http://localhost:3030/createEvent?` +
              `title=${newEvent.title}&` +
              `startDate=${newEvent.startDate.toISOString()}&` +
              `endDate=${newEvent.endDate.toISOString()}&` +
              `id=${newEvent.id}&` +
              `notes=${newEvent.notes}&` +
              `isAllDay=${newEvent.allDay}&` +
              `isRepeated=${newEvent.repeat}&` +
              `tokenID=${userTokenID}`,
            requestOptions
          )
        );
      console.log(props.calendarItems);
    }

    if (changed) {
      props.setCalendarItems(
        props.calendarItems.map((appointment) =>
          changed[appointment.id]
            ? { ...appointment, ...changed[appointment.id] }
            : appointment
        )
      );
      console.log(props.calendarItems);
    }

    if (deleted) {
      props.setCalendarItems(
        props.calendarItems.filter((appointment) => appointment.id !== deleted)
      );
      console.log(props.calendarItems);
    }
  }

  // function onAddEvent(addedAppointment: object){
  //     const addedEvent: CalendarItem = {
  //         title: addedAppointment.title,

  //     }
  // }

  // function onValueChange(nextValue: string | number){
  //     setCustomAttributeValue(nextValue);
  // }

  const priorityOptions: SelectOption[] = [
    { id: 0, text: "Low" },
    { id: 1, text: "Moderate" },
    { id: 2, text: "High" },
  ];

  return (
    <Paper className="month-view">
      <Scheduler data={props.calendarItems}>
        <ViewState
          currentDate={props.currentDate}
          onCurrentDateChange={currentDateChange}
        />
        <EditingState onCommitChanges={commitChanges} />
        <EditRecurrenceMenu
        // overlayComponent={({visible, ...ol_props}) => (
        //     <EditRecurrenceMenu.Overlay
        //         {...ol_props}
        //         visible={}
        //     />
        // )}
        />
        <MonthView />
        <Toolbar
          rootComponent={(tb_props) => (
            <Toolbar.Root
              children={[tb_props.children, props.calendarViewMenu]}
            />
          )}
        />
        <DateNavigator />
        <TodayButton />
        <ConfirmationDialog />
        <Appointments />
        <AppointmentForm
          basicLayoutComponent={({
            onFieldChange,
            appointmentData,
            ...bl_props
          }) => (
            <AppointmentForm.BasicLayout
              {...bl_props}
              appointmentData={appointmentData}
              onFieldChange={onFieldChange}
            >
              <AppointmentForm.Select
                {...props}
                value={appointmentData.priority}
                onValueChange={(nextValue: string | number) => {
                  onFieldChange({ priority: nextValue });
                }}
                availableOptions={priorityOptions}
                type="outlinedSelect"
                label="Custom Attribute"
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
  );
}
