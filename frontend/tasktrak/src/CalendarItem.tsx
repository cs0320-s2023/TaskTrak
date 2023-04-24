export default interface CalendarItem {
    title: string
    startDate: Date
    endDate: Date
    id: number
}

export const sampleCalendarItems: CalendarItem[] = [{
    title: 'Event #1',
    startDate: new Date(2023, 3, 23, 13, 30),
    endDate: new Date(2023, 3, 23, 15, 15),
    id: 0
  }]