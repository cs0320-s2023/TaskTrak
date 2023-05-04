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

export const sampleCalendarItems: CalendarItem[] = [
  {
    title: "Event1",
    allDay: false,
    startDate: new Date(2023, 3, 23, 13, 30),
    endDate: new Date(2023, 3, 23, 15, 15),
    repeat: undefined,
    id: 0,
    priority: 1,
    notes: "example note",
  },
];

export interface Task {
  name: string;
  notes: string;
  priority: 0 | 1 | 2;
  dread: 0 | 1 | 2;
  duration: number;
  dueDate: Date;
  isComplete: Boolean;
  timeSuggestions: number[];
}

export const sampleTasks: Task[] = [
  {
    name: "task1",
    notes: "note",
    priority: 2,
    dread: 1,
    duration: 3,
    dueDate: new Date(2023, 4, 10, 12, 0),
    isComplete: true,
    timeSuggestions: [3, 4, 5, 3],
  },
  {
    name: "task2",
    notes: "note",
    priority: 2,
    dread: 1,
    duration: 3,
    dueDate: new Date(2023, 4, 11, 12, 0),
    isComplete: true,
    timeSuggestions: [383, 430, 3, 3],
  },
];
