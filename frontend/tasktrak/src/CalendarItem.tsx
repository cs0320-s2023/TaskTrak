export interface CalendarItem {
    title: string;
    allDay: boolean;
    startDate: Date;
    endDate: Date;
    repeat: string | undefined;
    id: number;
    priority: 0 | 1 | 2;
    notes: string;
}

export const sampleCalendarItems: CalendarItem[] = [{
    title: 'Event1',
    allDay: false,
    startDate: new Date(2023, 3, 23, 13, 30),
    endDate: new Date(2023, 3, 23, 15, 15),
    repeat: undefined,
    id: 0,
    priority: 1,
    notes: "example note"
}]

export interface Task {
    title: string;
    dueDate: Date;
}