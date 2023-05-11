import React from "react";
import { render, fireEvent, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { TaskMenu } from "./TaskCard";
import { auth } from "./firebase/config";
import { CalendarItem, Task } from "./CalendarItem";

jest.mock("./firebase/config", () => ({
  auth: {
    currentUser: {
      getIdToken: jest.fn(() => Promise.resolve("mockTokenId")),
    },
  },
}));

describe("<TaskMenu />", () => {
  let fakeTasks: Task[];
  let fakeCalendarItems: CalendarItem[];
  let setCalendarItems = jest.fn();

  beforeEach(() => {
    fakeTasks = [
      {
        name: "Task1",
        dueDate: new Date(2023, 3, 23),
        priority: 2,
        progress: 20,
        duration: 2,
        timeSuggestions: [
          ["08:00", "10:00"],
          ["12:00", "14:00"],
        ],
        notes: "Some notes",
        isComplete: false,
        id: 0,
      },
      {
        name: "Task2",
        dueDate: new Date(2023, 3, 25),
        priority: 1,
        progress: 50,
        duration: 1.5,
        timeSuggestions: [
          ["09:00", "10:30"],
          ["13:00", "14:30"],
        ],
        notes: "Some other notes",
        isComplete: false,
        id: 1,
      },
    ];

    fakeCalendarItems = []; // Start with an empty calendar

    setCalendarItems = jest.fn();

    describe("TaskMenu", () => {
      beforeEach(() => {
        global.fetch = jest.fn(() =>
          Promise.resolve({
            json: () => Promise.resolve({}),
            ok: true,
          } as Response)
        );
      });

      afterEach(() => {
        jest.restoreAllMocks();
      });

      test("renders without crashing", () => {
        render(
          <TaskMenu
            tasks={fakeTasks}
            calendarItems={fakeCalendarItems}
            setCalendarItems={setCalendarItems}
          />
        );
      });

      test("displays tasks sorted by due date and priority", () => {
        render(
          <TaskMenu
            tasks={fakeTasks}
            calendarItems={fakeCalendarItems}
            setCalendarItems={setCalendarItems}
          />
        );

        const tasks = screen.getAllByRole("heading", { level: 5 }); // Assuming the task name is a h5 heading
        expect(tasks[0]).toHaveTextContent("Task1"); // Task1 has an earlier due date and higher priority
        expect(tasks[1]).toHaveTextContent("Task2");
      });

      test("opens and closes the dialog", async () => {
        render(
          <TaskMenu
            tasks={fakeTasks}
            calendarItems={fakeCalendarItems}
            setCalendarItems={setCalendarItems}
          />
        );

        const timeSuggestionButton = screen.getAllByRole("button", {
          name: /08:00 - 10:00/,
        })[0];
        fireEvent.click(timeSuggestionButton);
        expect(screen.getByRole("dialog")).toBeInTheDocument();

        const closeButton = screen.getByRole("button", { name: /close/i });
        fireEvent.click(closeButton);
        expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
      });

      test('"Add Task" button calls the addTaskToCalendar function', async () => {
        render(
          <TaskMenu
            tasks={fakeTasks}
            calendarItems={fakeCalendarItems}
            setCalendarItems={setCalendarItems}
          />
        );

        const timeSuggestionButton = screen.getAllByRole("button", {
          name: /08:00 - 10:00/,
        })[0];
        fireEvent.click(timeSuggestionButton);

        const addTaskButton = screen.getByRole("button", { name: /Add Task/ });
        fireEvent.click(addTaskButton);

        // Wait for promises to resolve
        await new Promise((resolve) => setImmediate(resolve));

        // Check if the mock fetch function was called
        expect(global.fetch).toHaveBeenCalledWith(
          "http://localhost:3030/createEvent?title=Task1&startDate=2023-04-23T08:00:00.000Z&endDate=2023-04-23T10:00:00.000Z&id=0&notes=Some notes&isAllDay=false&isRepeated=false&tokenID=mockTokenId",
          { method: "POST" }
        );

        // Check if setCalendarItems was called
        expect(setCalendarItems).toHaveBeenCalledWith([
          ...fakeCalendarItems,
          {
            title: "Task1",
            allDay: false,
            startDate: new Date(2023, 3, 23, 8, 0),
            endDate: new Date(2023, 3, 23, 10, 0),
            repeat: "false",
            id: 0,
            priority: 2,
            notes: "Some notes",
          },
        ]);
      });
    });
  });
});
