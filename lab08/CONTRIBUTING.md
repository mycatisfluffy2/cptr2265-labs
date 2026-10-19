# Contributing and Branching Policy

## Overview

This repository uses GitHub Flow to manage lab assignments and collaborative changes. Changes must be developed on branches and submitted through pull requests.

## Branches

- `main`: The stable branch containing reviewed and completed work. Direct pushes are prohibited by repository policy.
- `develop`: The integration branch for ongoing semester assignments.
- `feature/<description>`: Short-lived branches for individual features, labs, or improvements.
- `fix/<description>`: Short-lived branches for bug fixes.

## Workflow

1. Update the local branch from the remote repository before starting work.
2. Create a descriptive feature or fix branch from the appropriate base branch.
3. Make small, focused commits with meaningful messages.
4. Push the branch to GitHub.
5. Open a pull request targeting `develop` for normal integration work, or `main` when required by the assignment or release workflow.
6. Describe the purpose, changes, and testing performed in the pull request.
7. Request a peer review and address feedback before merging.
8. Merge the pull request only after required approvals and checks are satisfied.
9. Delete the feature branch after merging when it is no longer needed.

