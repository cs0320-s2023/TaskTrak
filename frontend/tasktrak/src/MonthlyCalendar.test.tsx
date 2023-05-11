import { render, fireEvent, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { BrowserRouter as Router } from "react-router-dom";
import MonthlyCalendar from "./MonthlyCalendar";
import { CalendarItem } from "./CalendarItem";
import { auth } from "./firebase/config";
import React from "react";

jest.mock("./firebase/config", () => ({
  auth: {
    currentUser: {
      getIdToken: jest.fn(() => Promise.resolve("mockTokenId")),
    },
  },
}));

const setCurrentDate = jest.fn();
const setCalendarItems = jest.fn();
const currentDate = new Date();
const calendarItems: CalendarItem[] = []; // add mock calendar items if needed

describe("MonthlyCalendar", () => {
  beforeEach(() => {
    render(
      <Router>
        <MonthlyCalendar
          currentDate={currentDate}
          setCurrentDate={setCurrentDate}
          calendarItems={calendarItems}
          setCalendarItems={setCalendarItems}
        />
      </Router>
    );
  });

  test("renders MonthlyCalendar component", () => {
    expect(screen.getByText("Switch to Week View")).toBeInTheDocument();
  });

  test("toggles between week and month view", () => {
    fireEvent.click(screen.getByText("Switch to Week View"));
    expect(screen.getByText("Switch to Month View")).toBeInTheDocument();

    fireEvent.click(screen.getByText("Switch to Month View"));
    expect(screen.getByText("Switch to Week View")).toBeInTheDocument();
  });

  //   test("adds appointment to calendarItems", () => {
  //     // assuming an 'Add appointment' button that opens a form to add appointment
  //     fireEvent.click(screen.getByText("Add appointment"));

  //     // assuming fields in the form to add appointment
  //     fireEvent.change(screen.getByPlaceholderText("Title"), {
  //       target: { value: "New Title" },
  //     });
  //     fireEvent.change(screen.getByPlaceholderText("Start Date"), {
  //       target: { value: "2023-05-20" },
  //     });
  //     fireEvent.change(screen.getByPlaceholderText("End Date"), {
  //       target: { value: "2023-05-21" },
  //     });

  //     // assuming a 'Save' button to save the appointment
  //     fireEvent.click(screen.getByText("Save"));

  //     // check if setCalendarItems has been called with the new appointment
  //     expect(setCalendarItems).toHaveBeenCalledWith(
  //       expect.arrayContaining([expect.objectContaining({ title: "New Title" })])
  //     );
  //   });
});
