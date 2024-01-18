# TaskTrak

## Collaborators:

Joel Kim (jkim631)
Zach Leontiou (zleontio)
Rayhan Meghji (rmeghji)
Ricky Ornelas (rornela)

## GitHub Repository:
https://github.com/cs0320-s2023/TaskTrak

## Demo Video:

[![TaskTrak Demo](https://github.com/cs0320-s2023/TaskTrak/assets/90580552/726d1a5f-433a-4a2f-b708-c4bd25edead9)](https://youtu.be/QSsfYRu87Mc?si=jx2ekvcz9i7yqYCQ)

Click the image to view the TaskTrak demo video!

## Usage instructions:
Clone the repo: https://github.com/cs0320-s2023/TaskTrak

After cloning the repository, we utilized IntelliJ to initialize our backend server. This was accomplished by accessing the pom.xml file situated within the 'backend' folder of the primary TaskTrak Folder.

Upon the project's successful opening, navigate to the 'main' folder located within 'src' and initiate the server. A "server started" message in the terminal will confirm the server's successful operation.

Next, we deployed Visual Studio Code to launch the frontend that interfaces with the backend. In Visual Studio Code, locate and open the 'frontend' file nested within the TaskTrak folder. Utilize the terminal commands "cd frontend" and "cd tasktrak" to traverse into the appropriate subfolder.

Once you've reached the correct subfolder, initiate the command "npm start". This will spark the frontend operation of our program, leveraging the local server managing the backend functionalities.

At this juncture, you will receive a prompt to either sign in or create an account. Once authenticated, you will be directed to our intuitive calendar where you can create and manage events with the option of daily, weekly, or monthly views.

A 'task' tab is located at the top for you to add non-time-specific tasks or commitments, such as homework or study periods. After adding a task, our task card component will update to showcase relevant information, which includes personalized time suggestions based on your previously inputted data.

When you select one of these time suggestions, a prompt will appear for you to create an event based on the suggested date and time. Doing so will automatically update your main calendar, ensuring your tasks and commitments are consistently and accurately tracked.

## Description:

TaskTrak is a program that combines a task manager and interactive calendar, allowing users to
integrate their time management needs into one digital location. The user can create both events
and tasks, each of which contain parameters that the user can input (name, notes, due date,
start time, prioriy, etc.) Tasks are assigned an estimated time of completion by the user, and the
program generates scheduling suggestions for each task based on the free time available during the current day.

## External Tools:

User data is stored within a FireBase database. This if for retrieval of user information
relating to the task manager/calendar. Each user has a user token ID which is used for retrieval
and organization of data.

## Privacy:

No personal user data is collected other than an email and password (inputted when the user
first logs into the program). These are stored within the firebase database. Users are not intended
to have access to any other user data.

## User interface

Getting Started
Sign Up: To get started, new users must sign up for an account. This process will require providing
information such as an email address and password.

Log In: Once signed up, users can log in using their registered email address and password.
This step is necessary for accessing the full functionality of the application.

Calendar Page
Upon successful login, users will be directed to the main calendar page.
The calendar page serves as the central hub for managing your schedule and staying organized.
It provides various viewing options to cater to your preference:

Monthly View: This view displays the entire month at a glance, allowing you to get a comprehensive
overview of your scheduled events and activities.

Weekly View: The weekly view provides a more detailed representation of your schedule, focusing on
a specific week. It enables you to plan your week effectively and allocate time accordingly.

Daily View: Located on the right side of the page, the daily view offers an in-depth look at your
agenda for a selected day. It helps you manage your time efficiently by providing a detailed
breakdown of events and tasks.

On the calendar page, you will find the Create Event button, which allows you to easily schedule
new events. When clicked, the button opens a form where you can fill in the following details:

Event Name: Provide a descriptive name for the event, making it easy to identify and remember.

Notes: Include any additional information or notes related to the event.
This could include agenda details, meeting topics, or any other relevant information.

Start and End Time: Specify the start and end time for the event.
This helps you allocate specific time slots for your activities and ensures accurate scheduling.

All-Day Event: If the event spans the entire day, you can mark it as an all-day event.
This option is useful for occasions like birthdays, holidays, or full-day conferences.

Once you have filled in the event details, click the Create or Save button to add the event to your
calendar. The newly created event will be displayed in the appropriate view
(monthly, weekly, or daily) based on the selected date.

Feel free to create as many events as you need to organize your schedule effectively
and keep track of your commitments.

## Task Creation
In addition to managing events, our application provides a dedicated Task feature to help you stay
on top of your to-do list and accomplish your goals efficiently. To access the Task page,
simply navigate to the top of the screen and click on the "Tasks" tab.

On the Task page, you can create new tasks by following these steps:

Task Name: Start by giving your task a descriptive name that accurately represents the task at hand.
This will make it easier to identify and prioritize later.

Priority: Assign a priority level to your task to indicate its importance or urgency.
You can choose from options like high, medium, or low, depending on the task's relative significance.

Notes: Include any additional notes or details that will help you better understand the task or
provide context for its completion. This can be helpful for tasks that require specific instructions
or background information.

Due Date: Specify the deadline or due date for your task. Setting a due date helps you stay
organized and ensures that tasks are completed in a timely manner.

Estimated Time of Completion: Estimate the amount of time you expect the task to take.
This helps in planning and scheduling your day effectively.

Once you have entered all the necessary information, click the "Save" button to add the
task to your list. Your newly created task will be saved and displayed.

## Testing

Unit tests/ integration test:

The backend functions are tested with unit tests that involved intergration. Given the nature of
the program, most functionality methods involve the user of other methods, especially in
relation to the timing aspects of the program. The functions are tested in the same manner as
they are used within the program, allowing for an accurate simulation of how the logic behind
the suggestions and objects will work (both seperately and integrated)

The frontend components are tested using jest and React testing libraries as well as mocks to ensure that the components are rendering correctly and with the correct functionality logic based on a wide variety of user input. While a lot of our functionality utilizes backend, by utiilizing mock state change functions, we were able to write tests for our App.tsx, TaskList, and MonthlyCalendar components, which are the core of our user-facing web app functionality.

## Improvements for future development

Suggestions for Future Development

Integrate with External Calendars:
Consider integrating our application with popular external calendar platforms such as Google
Calendar, Outlook, or Apple Calendar. This would allow users to sync their events and tasks
seamlessly across multiple platforms, providing a unified scheduling experience.

Recurring Events and Tasks: Implement the ability to create recurring events and tasks.
This feature would be especially useful for events or tasks that repeat on a regular basis,
such as weekly meetings, monthly reminders, or daily routines.

Collaborative Task Management: Enhance the collaboration capabilities of the task management
feature. Enable users to assign tasks to other users, set deadlines, and track progress
collaboratively. This would be beneficial for team projects or shared responsibilities.

Advanced Reminders and Notifications: Expand the options for reminders and notifications.
Allow users to set custom reminders, choose notification channels (e.g., email, SMS, push
notifications), and provide more flexibility in managing notifications based on individual
preferences.

Further sorting capabilites: Allow the user to sort tasks by priority and have more control over
how the tasks are displayed on screen

Customizable Views and Themes: Offer users the ability to customize their calendar views and
choose from a variety of themes to personalize their experience. This would allow users to tailor
the appearance of the application according to their preferences and create a visually pleasing
environment.
