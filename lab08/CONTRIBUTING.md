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

## Commit Messages

Commit messages must clearly explain the change, such as `docs: document GitHub Flow policy` or `feat: add lab setup instructions`. Messages such as `fix stuff`, `changes`, or `update` should be avoided.

## Pull Request Requirements

- All changes targeting `main` must use a pull request.
- At least one approving review is required for changes to `main`.
- Pull requests should have a descriptive title and explain what changed and how it was tested.
- Conflicts must be resolved and the resulting changes verified before merging.
- Contributors should review classmates' work constructively and communicate clearly.

## Merge Conflicts

When conflicts occur, identify the conflicting sections, discuss or evaluate the intended changes, resolve the conflict, and test the result. Commit and push the resolution before completing the merge.

## Repository Protection

The `main` branch must have GitHub branch protection configured to require pull requests and at least one approving review. Force pushes and branch deletion should remain disabled.