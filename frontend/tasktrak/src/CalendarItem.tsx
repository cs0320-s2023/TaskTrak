export interface CalendarItem {
  title: string;
  allDay: boolean;
  startDate: Date;
  endDate: Date;
  repeat: string | undefined;
  id: number;
  priority: number;
  notes: string;
}

export const sampleCalendarItems: CalendarItem[] = [
  // {
  //   title: "Event1",
  //   allDay: false,
  //   startDate: new Date(2023, 3, 23, 13, 30),
  //   endDate: new Date(2023, 3, 23, 15, 15),
  //   repeat: undefined,
  //   id: 0,
  //   priority: 1,
  //   notes: "example note",
  // },
];

export interface Task {
  name: string;
  notes: string;
  priority: number;
  duration: number;
  dueDate: Date;
  isComplete: Boolean;
  id: number;
  timeSuggestions: string[][];
  progress: number;
}

export const sampleTasks: Task[] = [
  // {
  //   name: "task0",
  //   notes: "note",
  //   priority: 0,
  //   duration: 3,
  //   dueDate: new Date(2023, 4, 10, 12, 0),
  //   isComplete: true,
  //   id: 3,
  //   timeSuggestions: [["4:00 - 7:00"]],
  //   progress: 0,
  // },
  // {
  //   name: "task1",
  //   notes: "note",
  //   priority: 2,
  //   duration: 3,
  //   dueDate: new Date(2023, 4, 8, 12, 0),
  //   isComplete: true,
  //   id: 0,
  //   timeSuggestions: [["4:00 - 7:00"]],
  //   progress: 25,
  // },
  // {
  //   name: "task2",
  //   notes: "note",
  //   priority: 0,
  //   duration: 3,
  //   dueDate: new Date(2023, 4, 7, 12, 0),
  //   isComplete: true,
  //   id: 1,
  //   timeSuggestions: [["4:00 - 7:00"]],
  //   progress: 5,
  // },
  // {
  //   name: "task3",
  //   notes: "note",
  //   priority: 1,
  //   duration: 3,
  //   dueDate: new Date(2023, 4, 10, 12, 0),
  //   isComplete: true,
  //   id: 2,
  //   timeSuggestions: [["4:00 - 7:00"]],
  //   progress: 39,
  // },
];
