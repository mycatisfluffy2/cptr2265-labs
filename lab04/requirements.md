# Part A

## Introduction/Purpose Section

The purpose of this project is to develop an application
that allows users to make calculations, track calculation history in a session,
and log notes within the app.

The application will be a sizeable box consisting of three
sections divided into two hemispheres. On the left, there will be a logs column
for keeping track of data or recording logic. On the right, the Calculations
section and history section are stacked on top of each other. The calculator
will be on top, and the history section will sit beneath it.

## 13 Functional Requirements:

| #  | Requirement                                                                                                                                              |
| -- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1  | The system shall allow the user to perform calculations using addition (+), subtraction (-), multiplication (*), and division (/).                       |
| 2  | The system shall allow the user to use parentheses to group mathematical expressions.                                                                    |
| 3  | The system shall allow the user to perform exponent calculations using the caret operator (^), such as 3^6.                                              |
| 4  | The system shall evaluate mathematical expressions according to standard mathematical order of operations.                                               |
| 5  | The system shall allow the user to enter calculator expressions using the computer keyboard.                                                             |
| 6  | The system shall provide graphical calculator controls that allow the user to enter numbers and mathematical operators without requiring keyboard input. |
| 7  | The system shall display the result of the valid calculation to the user.                                                                                |
| 8  | The system shall identify calculator expressions that contain invalid or unsupported input.                                                              |
| 9  | The system shall provide an indication to the user when a calculation contains invalid input rather than silently failing.                               |
| 10 | The system shall record calculations performed during the current calculating session.                                                                   |
| 11 | The system shall display the user’s calculation history for later reference.                                                                             |
| 12 | The system shall provide a text area where the user can write and organize notes related to their calculations.                                         |
| 13 | The system shall display the calculator, calculation history, and notes as the three primary sections of the application.                                |

## Non-Functional Requirements

### Performance

| #     | Requirement                                                                                                                                                    |
| ----- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **1** | The system shall display calculation results in less than 0.4 seconds after the user submits a valid calculation. This should be true for 99% of calculations. |
| **2** | The system shall update the calculation history immediately (within the second) after a calculation is completed.                                              |

### Usability

| #     | Requirement                                                                                                                                                                                                           |
| ----- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **1** | The system shall provide both keyboard input and graphical controls so that users are not limited to using a mouse. The original requirements specifically identify keyboard input as an important usability feature. |
| **2** | The system shall organize the calculator, history, and notes into a single interface so that users do not need to keep a separate text document open while using the calculator.                                      |
| **3** | The system shall use a user interface layout that keeps the history section compact by displaying it as a scrollable list.                                                                                            |

### Reliability

| #     | Requirement                                                                                                                                                       |
| ----- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **1** | The system shall retain the calculation history for the duration of the current calculating session so that previous calculations remain available for reference. |
| **2** | The system shall provide an error indication when the user enters an invalid calculation rather than producing an unexplained or misleading result.               |

### Maintainability

| #     | Requirement                                                                                                                                                                     |
| ----- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **1** | The system shall be developed using the Waterfall development model and shall progress through requirements, design, development, testing, deployment, and optimization stages. |
| **2** | The completed application shall be polished and presentable to be included in a software development portfolio.                                                                 |
