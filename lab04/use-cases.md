# Part B

## Use Case 1: Perform a Calculation

**Name:** Perform a Calculation

**Actor:** User

### Preconditions

1. The calculator application is open.
2. The calculator is ready to accept user input.

### Main Flow

1. The user enters a mathematical expression using the keyboard or graphical calculator controls**.**
2. The system takes the user’s input.
3. The system evaluates the expression according to the standard order of operations.
4. The system displays the calculation result.
5. The system adds the completed calculation to the history section.

### Alternate flow

1. If the user enters an invalid expression, the system shall notify the user that the input is invalid.
2. If the user enters an expression containing unsupported input, the system shall not produce a calculation result and shall notify the user.

### Post Conditions

1. The user's calculation history remains available for reference during the current session.
2. No calculation history is deleted by viewing or scrolling through the history.

---

## Use Case 2: Review Calculation History

**Name:** Review Calculation History

**Actor:** User

### Preconditions

1. The calculator application is open.
2. The calculator is ready to accept user input.

### Main Flow

1. The user views the calculation history section.
2. The system displays previous calculations in a list.
3. The user scrolls through the history as needed.

### Alternate Flows

1. If the calculation history contains more entries than can fit in the available space, the user shall be able to scroll through the list.
2. If no calculations have been completed during the current session, the history section shall contain no previous calculations to display.

### Post Conditions

1. The user's calculation history remains available for reference during the current session.
2. No calculation history is deleted by viewing or scrolling through the history.

---

## Use Case 3: Record and Review Notes

**Name:** Record and Review Notes

**Actor:** User

### Preconditions

1. The calculator application is open.
2. The calculator is ready to accept user input.

### Main Flow

1. The user selects the notes section.
2. The user enters text into the notes section.
3. The system displays the entered text in the notes section.
4. The notes are visible while making calculations.

### Post Conditions

1. If the user does not enter any notes, the notes section remains available for future use.
2. If the user enters incorrect information, the user can edit or replace the text in the notes section.

### Alternate flow

1. The user's notes are available during the current session.
2. The user can review or edit the notes while using the calculator.

### Post Conditions

1. The user’s notes are available during the current session.
2. The user can review or edit the notes while using the calculator.
