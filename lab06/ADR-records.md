# MVC Pattern

Separates presentation and interaction from the system data.

The system is structured into three logical components that interact with each other (**Model, View, and Controller**).

I could use the Model to handle the calculator logic, the view to show dynamic forms and data, and the controller to receive inputs and assign operations to the model.

### Conclusion

This sounds exactly like something that could work within my calculator project.

This is what I’ll pick for now.

---

# Layered Architecture pattern

Organizes the system into layers, with related functionality associated with each layer.

Used when building new facilities on top of existing systems;

Used when the development is spread across several teams with each team responsibility for a layer of functionality;

Used when there is a requirement for multilevel security.

### Conclusion

I am building a new system, not adding onto a new system. The development is not spread across multiple teams. My Calculator has no authentication or security.

Even on a smaller scale I still don’t need additional separation within my project. This would add un-needed complexity.

These use cases don’t match the needs of my project, and I don’t want to add unneeded complexity to my project. I’ll move on for now.

---

# The Repository pattern

All data in a system is managed in a central repository that is accessible to all system components. Components do not interact directly, only through the repository.

Used when you have a system in which large volumes of information are generated that must be stored for a long time.

### Conclusion

I want user data to be private and unique to each session and only saved to the user’s local computer. This pattern doesn’t seem to meet the needs of project. I should keep a copy of the project in a git repository, but that is completely unrelated.

I’m working with short sessions, and I don’t need to share data across multiple devices.

I’ll move on.
