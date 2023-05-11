import { render, fireEvent, waitFor, screen } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import TaskList from "./TaskList";
import React from "react";

jest.mock("firebase/auth", () => ({
  getAuth: jest.fn(() => ({
    currentUser: {
      getIdToken: jest.fn(() => Promise.resolve("fake_token")),
    },
  })),
}));

describe("TaskList", () => {
  it("renders without crashing", () => {
    render(<TaskList tasks={[]} setTasks={jest.fn()} />);
    expect(screen.getByText("New Task")).toBeInTheDocument();
  });

  it("adds a new task", async () => {
    const setTasks = jest.fn();
    render(<TaskList tasks={[]} setTasks={setTasks} />);

    fireEvent.click(screen.getByText("New Task"));

    fireEvent.change(screen.getByLabelText("Task Name"), {
      target: { value: "Test Task" },
    });
    fireEvent.change(screen.getByLabelText("Hours"), {
      target: { value: "1" },
    });
    fireEvent.change(screen.getByLabelText("notes"), {
      target: { value: "Test Notes" },
    });

    fireEvent.click(screen.getByText("Save"));
  });
});
