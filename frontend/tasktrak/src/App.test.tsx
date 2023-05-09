import { render, fireEvent, waitFor, screen } from "@testing-library/react";
import App from "./App";
import React from "react";
import MonthlyCalendar from "./MonthlyCalendar";
import { sampleCalendarItems, CalendarItem } from "./CalendarItem";
import { auth } from "./firebase/config";
import { BrowserRouter as Router } from "react-router-dom";
import ReactDOM from "react-dom";
import { describe, it, expect } from "vitest";
import { propTypes } from "react-bootstrap/esm/Image";

it("renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<App />, div);
  ReactDOM.unmountComponentAtNode(div);
});
// it("renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(<MonthlyCalendar
//     currentDate={new Date()}
//     setCurrentDate={(currentDate: Date) => new Date()}
//     calendarViewMenu={}
//     calendarItems={sampleCalendarItems}
//     setCalendarItems={(calendarItem: CalendarItem) => [...sampleCalendarItems, calendarItem]}
//   />, div);
//   ReactDOM.unmountComponentAtNode(div);
// });

// it("sets the initial date correctly", () => {
//   const { getByText } = render(<App />);
//   const currentDate = new Date();
//   expect(getByText(currentDate.getDate().toString())).toBeInTheDocument();
// });

// it("switches between month and week view modes", () => {
//   const { getByText } = render(<App />);
//   const switchButton = getByText("Switch to Week View");
//   fireEvent.click(switchButton);
//   expect(getByText("Switch to Month View")).toBeInTheDocument();
//   fireEvent.click(switchButton);
//   expect(getByText("Switch to Week View")).toBeInTheDocument();
// });
// it("returns true when a user is logged in", () => {
//   const { getByText } = render(<App />);
//   auth.currentUser = { email: "test@example.com" };
//   expect(getByText("Log Out")).toBeInTheDocument();
//   expect(getByText("Sign In")).not.toBeInTheDocument();
// });

// it("returns false when no user is logged in", () => {
//   const { getByText } = render(<App />);
//   auth.currentUser = null;
//   expect(getByText("Log Out")).not.toBeInTheDocument();
//   expect(getByText("Sign In")).toBeInTheDocument();
// });
// it("renders the AuthProvider component when no user is logged in", () => {
//   const { getByTestId } = render(<App />);
//   expect(getByTestId("auth-provider")).toBeInTheDocument();
// });

// it("renders the TaskList component with the correct tasks", () => {
//   const { getByText } = render(<App />);
//   sampleTasks.forEach((task) => {
//     expect(getByText(task.title)).toBeInTheDocument();
//   });
// });
// it("renders the TaskMenu component with the correct tasks", () => {
//   const { getByText } = render(<App />);
//   sampleTasks.forEach((task) => {
//     expect(getByText(task.title)).toBeInTheDocument();
//   });
// });
