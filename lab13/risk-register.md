# ConvertHub – Risk Register

**Project:** ConvertHub Desktop Conversion Application  
**Project Type:** Java Desktop Application  
**Target Release Date:** January 29, 2027  
**Prepared By:** Individual Project Owner

## 1. Project Overview

ConvertHub is a proposed Java desktop application designed to provide users with a convenient location for performing common conversions. Planned features include temperature, weight, length, distance, volume, and international time-zone conversions.

The application will use Java Swing for its graphical interface and will be packaged as an executable JAR file.

This risk register identifies potential problems that could affect the project's schedule, functionality, usability, or final release.

## 2. Risk Rating Scale

| Rating | Probability | Impact |
|---|---|---|
| Low | Unlikely to occur | Minor inconvenience |
| Medium | Could reasonably occur | Noticeable project disruption |
| High | Likely to occur | Major project disruption or failure |

## 3. Identified Project Risks

| ID | Risk Description | Probability | Impact |
|---|---|---|---|
| R1 | Incorrect conversion formulas could produce inaccurate results. | Medium | High |
| R2 | Time-zone differences and daylight saving changes could cause incorrect displayed times. | Medium | High |
| R3 | Additional feature requests could expand the project beyond its original scope. | High | Medium |
| R4 | The executable JAR may not run correctly on computers with different Java versions or configurations. | Medium | High |
| R5 | Conversion history could fail to save or display previous entries correctly. | Medium | Medium |
| R6 | The application interface could become confusing or difficult for users to navigate. | Medium | Medium |
| R7 | Development delays could prevent the project from meeting its January 29 release target. | Medium | High |
| R8 | Insufficient testing could allow bugs to remain in the final application. | Medium | High |
| R9 | Differences in Java development environments could cause unexpected compatibility problems. | Medium | Medium |

## 4. Risk Mitigation Strategies

### R1 – Incorrect Conversion Formulas

**Mitigation Strategy:**
- Verify conversion formulas using reliable mathematical references.
- Test each conversion with known values.
- Test conversions in both directions.
- Include decimal values and negative temperatures where applicable.

### R2 – Incorrect International Time-Zone Information

**Mitigation Strategy:**
- Use Java's built-in `java.time` API and `ZoneId` functionality.
- Avoid manually calculating time differences.
- Test locations with different time zones and daylight saving rules.
- Verify displayed times against reliable time references.

### R3 – Project Scope Expansion

**Mitigation Strategy:**
- Keep the original release focused on the six planned feature categories.
- Record additional feature requests as future enhancements.
- Avoid adding features that are not necessary for the initial release.
- Review the project backlog before accepting new work.

### R4 – Executable JAR Compatibility

**Mitigation Strategy:**
- Select and document a supported Java version.
- Test the packaged JAR outside the development environment.
- Include instructions explaining the Java runtime requirement.
- Test the application on another compatible computer if available.

### R5 – Conversion History Problems

**Mitigation Strategy:**
- Define how history entries will be stored before implementation.
- Test saving, retrieving, and clearing history.
- Handle missing or inaccessible history files gracefully.
- Keep conversion functionality independent of history storage.

### R6 – Confusing Application Interface

**Mitigation Strategy:**
- Use consistent navigation and clearly labeled conversion categories.
- Keep the interface simple and organized.
- Test common tasks from a user's perspective.
- Improve confusing labels and layouts before release.

### R7 – Development Schedule Delays

**Mitigation Strategy:**
- Divide development into smaller milestones.
- Prioritize essential conversion features before optional enhancements.
- Review progress regularly.
- Reserve time before the release date for testing and bug fixes.

### R8 – Insufficient Testing

**Mitigation Strategy:**
- Create a testing checklist for every conversion category.
- Test valid and invalid input values.
- Test the application after major changes.
- Complete a final testing round before packaging the release.

### R9 – Java Environment Compatibility

**Mitigation Strategy:**
- Document the minimum supported Java version.
- Use standard Java libraries where possible.
- Avoid unnecessary external dependencies.
- Test the application using the intended runtime environment.

## 5. Risk Monitoring

Risks will be reviewed at each project milestone.

If a risk becomes more likely or its potential impact increases, its probability and impact ratings should be updated.

High-impact risks will receive additional attention before the application reaches its final testing and release stages.

## 6. Conclusion

Identifying these risks early provides an opportunity to prevent avoidable problems during development.

The most important areas to monitor are conversion accuracy, time-zone reliability, application compatibility, project scheduling, and adequate testing.

By using the mitigation strategies identified in this register, the ConvertHub project will have a clearer path toward a successful release.