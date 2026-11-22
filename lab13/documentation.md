**Part A:**
Automated Project Rules

The ConvertHub GitHub Project already had two built-in automation workflows configured when I checked the project settings.

Item Added to Project: Automatically assigns newly added project items to the Todo status.

Pull Request Merged: Automatically changes the status of associated project items to Done when their pull requests are merged.

I verified that both workflows were enabled and configured correctly. These automations help reduce manual updates and keep the project board organized as work progresses.

**Part B: Poker Session**

For part B, I chose to estimate the following stories where my initial estimates did not fully account for the work involved:

    CH-04: Create main Java Swing window, Inital score: 3, Updated score: 2

    CH-12: Add international time zones, Inital score: 3, Updated score: 5

    CH-13: Add conversion history, Inital score: 3, Updated score: 5

    CH-16: Test usability and fix bugs, Inital score: 3, Updated score: 5

**Why the estimates changed:**

    CH-04 – Main Java Swing Window
    This isn't a crash course on Java, I can use AI to help make the swing window.
     Using AI to help make the swing window could save a lot of time. 

    CH-12 – International Time Zones
    Displaying the current time in another country seemed straightforward at first. However,
    different time zones and daylight saving adjustments introduce additional complexity.
    Plus, I don't know what API or data source to source the time data.

    CH-13 – Conversion History
    I initially underestimated the work involved in conversion history.
     The feature would need to store previous conversions, retrieve them,
      display them, and allow users to clear the history.

    CH-16 – Usability Testing and Bug Fixes
    Each conversion category, invalid input, and user interaction needs to be checked.
     This could take a long time.

**What surprised me about the estimates:**
    What surpised me what how a simple task could have so much hidden complexity. For example, in the time zone feature, I wanted to be able to display the current time in other parts of the world. But then i realized that I didn't know how to do that. I would either need to import an API with the time data, or account for the daylight savings time and country for each country supported by the app. There are a lot of countries. I'm not sure how many will be supported. I made a weather app using fire base that got weather data, maybe something similar could work there. 

**Part C:**
Located in lab13/risk-register.md