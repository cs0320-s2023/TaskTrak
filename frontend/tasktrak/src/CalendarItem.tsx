export default interface CalendarItem {
    title: string;
    startDate: Date;
    endDate: Date;
    id: number;
    priority: 0 | 1 | 2;
}

export const sampleCalendarItems: CalendarItem[] = [{
    title: 'Event #1',
    startDate: new Date(2023, 3, 23, 13, 30),
    endDate: new Date(2023, 3, 23, 15, 15),
    id: 0,
    priority: 1
  }]