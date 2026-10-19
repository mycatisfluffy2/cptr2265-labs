# HOW TO RUN

In the midterm folder,
1. Open a terminal or command prompt in the folder containing the downloaded file.
2. Run: java -jar NotesLogsFinanceWorkspace.jar

# Notes & Logs — Java Finance Workspace

A GitHub-inspired dark desktop calculator and notes workspace built with Java Swing.

## Requirements
- Java 17 or newer recommended (Java 11+ should work)

## Launch
### Easiest (Java 11+)
Open a terminal in this folder and run:
`java NotesLogsApp.java`

### Compile then run
`javac NotesLogsApp.java`
`java NotesLogsApp`

## Features
- Two-column workspace: editable notes on the left, calculator and history stacked on the right
- Calculator supports +, -, *, /, parentheses, decimals and unary minus
- Completed calculations are automatically added to scrollable history
- Insert results/history entries into notes
- Copy all notes to the clipboard
- Clear history with confirmation

## Notes
This is a source-code starter project, not a native Windows executable. It requires Java installed. For a double-clickable `.jar`, compile with `javac NotesLogsApp.java` and package with a manifest; the included source can also be run directly with modern Java.
