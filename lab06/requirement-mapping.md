# Non-Functional Requirements Mapped to ADR Decisions

## Reliability

### Requirement 1

> **The system shall retain the calculation history for the duration of the current calculating session so that previous calculations remain available for reference.**

**ADR Decision:**
I chose to avoid the **Repository Pattern** because the project does not need to permanently store records or share data with other devices. Instead, I used the MVC Pattern which can handel all state and user data locally via the Model.

---

## Performance

### Requirement 2

> **The system shall display calculation results in less than 0.4 seconds after the user submits a valid calculation. This should be true for 99% of calculations.**

**ADR Decision:**
Ruling out the **Repository Pattern** allows the project to perform calculations locally instead of relying on a central repository. This means the user does not need to rely on a stable internet connection, and components can interact directly with each other.

### Requirement 3

> **The system shall update the calculation history immediately (within one second) after a calculation is completed.**

**ADR Decision:**
Ruling out the **Repository Pattern** allows the project to retrieve calculation history locally, which is more reliable than retrieving it from a remote repository. Instead, I use the **MVC (Model-View-Controller) Pattern**, which consists of three logical components—the Model, View, and Controller—that interact with each other locally.
