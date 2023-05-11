import React from "react";
import { render, fireEvent, waitFor, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { BrowserRouter as Router } from "react-router-dom";
import App from "./App";
import { auth } from "./firebase/config";
import MonthlyCalendar from "./MonthlyCalendar";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { sampleCalendarItems } from "./CalendarItem";

jest.mock("./firebase/config", () => ({
  auth: {
    onAuthStateChanged: jest.fn(),
    signOut: jest.fn(),
    currentUser: null,
  },
}));
const setCurrentDate = jest.fn();
const setCalendarItems = jest.fn();

describe("App", () => {
  beforeEach(() => {
    render(
      <Router>
        <App />
        <MonthlyCalendar
          currentDate={new Date()}
          setCurrentDate={setCurrentDate}
          calendarItems={sampleCalendarItems}
          setCalendarItems={setCalendarItems}
        />
      </Router>
    );
  });

  test("renders App component and child components", () => {
    expect(screen.getByText("calendar")).toBeInTheDocument();
    expect(screen.getByText("tasks")).toBeInTheDocument();
    expect(screen.getByText("Sign In")).toBeInTheDocument();
  });

  test("switches between Calendar and Task views", () => {
    fireEvent.click(screen.getByText("tasks"));
    expect(screen.queryByText("Log Out")).toBeNull();

    fireEvent.click(screen.getByText("calendar"));
    expect(screen.queryByText("Log Out")).toBeNull();
  });

  test("calls onAuthStateChanged on component mount", () => {
    expect(auth.onAuthStateChanged).toHaveBeenCalledTimes(1);
  });
});

// test("handles Log In and Log Out buttons", async () => {
//   (auth.currentUser as any) = { uid: "123" };
//   fireEvent.click(screen.getByText("Sign In"));

//   await waitFor(() => {
//     expect(screen.getByText("Log Out")).toBeInTheDocument();
//   });

//   fireEvent.click(screen.getByText("Log Out"));

//   await waitFor(() => {
//     expect(screen.getByText("Sign In")).toBeInTheDocument();
//   });
// });
// it("renders without crashing", () => {
//   const div = document.createElement("div");
//   const mockOnChange = jest.fn();
//   const mockViewMenu = jest.fn();
//   ReactDOM.render(
//     <MonthlyCalendar
//       currentDate={new Date()}
//       setCurrentDate={(currentDate: Date) => new Date()}
//       calendarItems={sampleCalendarItems}
//       setCalendarItems={mockOnChange}
//     />,
//     div
//   );
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
