import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Algorithim.TaskManager;
import Enums.Rating;
import Items.Task;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



public class testTask {
  private TaskManager tm;
  private Task task1;
  private Task task2;

  @BeforeEach
  public void setUp() {
    tm = new TaskManager();
    task1 = new Task("Task 1", "Notes 1", Rating.HIGH, 1.0,
        LocalDateTime.now().plusDays(1), false);
    task2 = new Task("Task 2", "Notes 2", Rating.LOW,  0.5, LocalDateTime.now().plusDays(2), false);
    //task3 = new Task("Task 3", "Notes 3", Rating.LOW, Rating.LOW, Duration.ofMinutes(30),
    //LocalDateTime.now().plusDays(2), false);
  }


  @Test
  void testAddTask() {

    tm.addTask(task1);
    tm.addTask(task2);

    assertEquals(2, tm.getTaskMap().size());
    assertEquals(task1, tm.getTaskMap().get("Task 1"));
    assertEquals(task2, tm.getTaskMap().get("Task 2"));
  }

  @Test
  void testRemoveTask() {
    TaskManager tm = new TaskManager();
    Task task1 = new Task("Task 1", "Notes 1", Rating.HIGH, 1.0,
        LocalDateTime.now().plusDays(1), false);
    Task task2 = new Task("Task 2", "Notes 2", Rating.LOW,0.5,
        LocalDateTime.now().plusDays(2), false);

    assertEquals(0, tm.getTaskMap().size());
    tm.addTask(task1);
    tm.addTask(task2);
    assertEquals(2, tm.getTaskMap().size());
    tm.removeTask("Task 1");
    assertEquals(1, tm.getTaskMap().size());
    tm.removeTask("Task 2");
    assertEquals(0, tm.getTaskMap().size());
  }


  @Test
  public void testAddTask_NullTask() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.addTask(null));
    assertEquals("Task cannot be null", exception.getMessage());
  }


  @Test
  public void testAddTask_TaskAlreadyExists() {
    tm.addTask(task1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.addTask(task1));
    assertEquals("Task with name 'Task 1' already exists", exception.getMessage());
  }


  @Test
  public void testRemoveTask_NullName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.removeTask(null));
    assertEquals("Name cannot be null", exception.getMessage());
  }


  @Test
  public void testRemoveTask_NonExistingTask() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.removeTask("Task 1"));
    assertEquals("Task with name 'Task 1' does not exist", exception.getMessage());
  }


  @Test
  public void testGetTask_NullName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.getTask(null));
    assertEquals("Name cannot be null", exception.getMessage());
  }

  @Test
  public void testGetTask_NonExistingTask() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> tm.getTask("Task1"));
    assertEquals("Task with name 'Task1' does not exist", exception.getMessage());
  }




}


