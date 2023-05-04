import { Paper, Input } from "@mui/material";
import {
  Scheduler,
  WeekView,
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
import { CalendarItem } from "./CalendarItem";
// import './WeeklyCalendar.css';
import { useState } from "react";

interface WeeklyCalendarProps {
  currentDate: Date;
  setCurrentDate: (currentDate: Date) => void;

  calendarItems: CalendarItem[];
  setCalendarItems: (calendarItems: CalendarItem[]) => void;

  calendarViewMenu: React.ReactNode[];
}

export default function WeeklyCalendar(props: WeeklyCalendarProps) {
  function currentDateChange(currentDate: Date) {
    props.setCurrentDate(currentDate);
  }
  const [customAttributeValue, setCustomAttributeValue] = useState<
    string | number
  >(0);

  function commitChanges({ added, changed, deleted }: ChangeSet) {
    if (added) {
      const startingAddedID =
        props.calendarItems[props.calendarItems.length - 1].id + 1;
        props.setCalendarItems([...props.calendarItems, {
            id: startingAddedID,
            title: added.summary,
            startDate: added.startDate,
            endDate: added.endDate,
            priority: added.priority,
            allDay: added.allDay,
            repeat: added.rRule,
            notes: added.notes,
            ...added}])
    }

    if (changed) {
      props.setCalendarItems(
        props.calendarItems.map((appointment) =>
          changed[appointment.id]
            ? { ...appointment, ...changed[appointment.id] }
            : appointment
        )
      );
    }

    if (deleted) {
      props.setCalendarItems(
        props.calendarItems.filter((appointment) => appointment.id !== deleted)
      );
    }
  }

  function onValueChange(nextValue: string | number) {
    setCustomAttributeValue(nextValue);
  }

  const priorityOptions: SelectOption[] = [
    { id: 0, text: "Low" },
    { id: 1, text: "Moderate" },
    { id: 2, text: "High" },
  ];

  return (
    <Paper className="week-view">
      <Scheduler data={props.calendarItems} height={690}>
        <ViewState
          currentDate={props.currentDate}
          onCurrentDateChange={currentDateChange}
        />
        <EditingState onCommitChanges={commitChanges} />
        <EditRecurrenceMenu />
        <WeekView />
        <Toolbar
          rootComponent={(tb_props) => (
            <Toolbar.Root
              children={[tb_props.children, props.calendarViewMenu]}
            />
          )}
        />
        <DateNavigator />
        <AllDayPanel />
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
                value={appointmentData.prioritySelect}
                onValueChange={(nextValue: string | number) => {
                  onFieldChange({ prioritySelect: nextValue });
                }}
                availableOptions={priorityOptions}
                type="outlinedSelect"
                label="Custom Attribute"
              />
            </AppointmentForm.BasicLayout>
          )}
        />
      </Scheduler>
    </Paper>
  );
}