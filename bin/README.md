# ATCU4G Automation Project

**Author:** Suraj Bhalerao  
**Date of Creation:** 01/01/2025  

---

## Initial Setup (One-Time) 

1.  **Get the Repository URL:** Copy the repository URL (HTTPS or SSH).

2.  **Clone the Repository (Local Setup):** Open your terminal and navigate to your desired project directory. Clone the repository using:

    ```bash
    	git clone <repository_url>
    ```
    **Note:** Replace `<repository_url>` with the URL of the project you want to clone to your local repository.

---

## Daily Workflow

1.  **Pull Changes (Start of Day/Before Making Changes):** Always start by pulling the latest changes from the remote repository to avoid conflicts:

    ```bash
    git pull origin main  # Or git pull origin <your_main_branch_name> if it's not 'main', e.g., 'master'
    ```

2.  **Create a Branch (For Each Feature/Bug Fix):** Create a new branch for each feature or bug fix to isolate changes:

    ```bash
    git checkout -b <feature_name>  # Example: git checkout -b fix-login-bug
    ```

3.  **Make Changes and Commit:** Make your changes, stage them, and commit with a descriptive message:

    ```bash
    git add .               # Stage all changes
    git add <specific_file> # Stage a specific file
    git commit -m "Descriptive commit message" # Example: git commit -m "Fixed login form validation"
    ```

4.  **Push Your Branch:** Push your branch to the remote repository:

    ```bash
    git push origin <feature_name>
    ```

5.  **Create a Pull Request (PR):** On the platform's website, create a pull request to merge your branch into the main branch. Provide a clear title and description for the PR, explaining the changes made.

6.  **Code Review:** Team members review the code in the pull request, providing feedback and suggestions. Address any feedback and push updated commits to your branch (which will automatically update the PR).

7.  **Merge the Pull Request:** Once the code review is approved, the pull request is merged into the main branch.

8.  **Delete Your Branch (Optional, but Recommended):** After the PR is merged, delete your local and remote branches:

    ```bash
    git checkout main
    git pull origin main
    git branch -d <feature_name>          # Delete local branch
    git push origin --delete <feature_name> # Delete remote branch
    ```

---

## Important Considerations

*   **Descriptive Commit Messages:** Use clear and concise commit messages that explain the purpose of the changes.
*   **Keep Branches Up-to-Date:** Regularly pull changes from the main branch into your feature branches to minimize merge conflicts:

    ```bash
    git checkout <feature_name>
    git pull origin main
    ```

*   **Resolve Conflicts Carefully:** If conflicts arise during a merge or pull, resolve them carefully and test your changes thoroughly.
*   **Communicate with Your Team:** Keep your team informed about your progress and any potential issues.
*   **Use a Consistent Branching Strategy:** Stick to a consistent branching strategy (e.g., Gitflow) for better organization.

---

## Common Git Commands

*   `git status`: Shows the status of your changes.
*   `git log`: Shows the commit history.
*   `git diff`: Shows the differences between files.
*   `git branch`: Lists branches.
*   `git checkout`: Switches branches.
*   `git merge`: Merges branches.

-------------------------------------------------------------------------------------------------------------------------

# Creating and Merging Branches in Git

This document outlines how to create a new branch from the main branch in Git, make changes, and then merge those changes back into the main branch.

## 1. Creating a New Branch

*   **Ensure you are on the main branch:**

    ```bash
    git checkout main
    ```

*   **Create a new branch and switch to it:**

    ```bash
    git checkout -b <new_branch_name>
    ```

    Replace `<new_branch_name>` with a descriptive name for your branch (e.g., `feature/new-login`, `bugfix/issue-123`).

---

## 2. Making Changes

*   **Perform your work:** Edit files, add new files, etc.

*   **Stage your changes:**

    ```bash
    git add .         # Stages all changes
    git add <file>    # Stages a specific file
    ```

*   **Commit your changes:**

    ```bash
    git commit -m "A descriptive commit message"
    ```

---

## 3. Merging Back into the Main Branch

*   **Switch back to the main branch:**

    ```bash
    git checkout main
    ```

*   **Update your local main branch:** It's best practice to update your local main branch with the remote main branch before merging.

    ```bash
    git pull origin main
    ```

*   **Merge your branch into main:**

    ```bash
    git merge <new_branch_name>
    ```

*   **Resolve any merge conflicts:** If conflicts arise (changes to the same lines of code in both branches), Git will mark them in the affected files. Manually edit these files to resolve the conflicts, then stage and commit the changes.

*   **Push the changes to the remote repository:**

    ```bash
    git push origin main
    ```

---

## Example Workflow

```bash
git checkout main                               # Switch to main
git checkout -b feature/new-button             # Create and switch to a new branch

# Make changes to files
git add .                                      # Stage changes
git commit -m "Added a new button to the UI"  # Commit changes
git checkout main                              # Switch back to main
git pull origin main                           # Update local main
git merge feature/new-button                   # Merge the new branch
git push origin main                           # Push the changes
```


------------------------------------------------------------------------------------------------------

# Git Collaboration Workflow for a Team of 4

This guide outlines how to collaborate on a Git repository in a team of four, addressing common issues and providing a structured process to maintain the remote repository without conflicts.

## 1. Git Workflow

A good Git workflow is essential to avoid conflicts and ensure that everyone works smoothly on the project. One of the most common workflows is the **Git Flow** or **Feature Branch Workflow**.

### Steps:

1. **Clone the repository**: Everyone clones the repository on their local machine.
   ```bash
   git clone <repository_url>
   ```

2. **Create a branch for your task**: Each team member works on a separate branch for the feature or bug fix they are working on. This avoids working directly on the `main` (or `master`) branch and reduces conflicts.
   ```bash
   git checkout -b feature/your-feature-name
   ```

## 2. Daily Process: Commit and Push Changes

Every team member should follow this process every day before they start working and at the end of their workday.

### Before you start working on a task:

1. **Pull the latest changes** from the `main` branch (or `master`) to your local repository to ensure you’re working with the most recent version of the project.
   ```bash
   git checkout main
   git pull origin main
   ```

2. **Switch to your feature branch** if you're not already on it.
   ```bash
   git checkout feature/your-feature-name
   ```

### While working:

- **Commit frequently**: Commit your changes frequently, ideally after completing small tasks. This avoids large, complex merges later on and keeps the commit history clean and understandable.
   ```bash
   git add .
   git commit -m "Add feature X or fix bug Y"
   ```

### Before pushing:

1. **Pull the latest changes** from `main` again to make sure there are no new changes in the remote repository that you might be missing.
   ```bash
   git checkout main
   git pull origin main
   ```

2. **Rebase your feature branch** onto `main` to ensure that your branch has the latest changes from the `main` branch.
   ```bash
   git checkout feature/your-feature-name
   git rebase main
   ```

3. **Resolve conflicts (if any)**: If there are any merge conflicts during the rebase process, Git will prompt you to fix them. After fixing, mark the conflicts as resolved.
   ```bash
   git add <file-with-conflict>
   git rebase --continue
   ```

4. **Push your changes** to the remote repository.
   ```bash
   git push origin feature/your-feature-name
   ```

## 3. Opening a Pull Request (PR)

Once your feature or task is complete and pushed to the remote repository, you can open a **pull request (PR)** to merge your changes into the `main` branch.

### PR Process:

1. Go to your Git hosting service (GitHub, GitLab, etc.), and create a pull request for your feature branch to merge into `main`.
2. **Review and discuss the PR**: Your team should review the PR for potential issues, bugs, or improvements.
3. **Merge the PR**: After approval, the PR can be merged into `main`.

## 4. Issues to Watch Out For and How to Resolve Them

### 1. Merge Conflicts

Merge conflicts occur when two people change the same part of the code at the same time. To avoid or deal with them:

- **Regularly pull the latest changes** from the `main` branch into your feature branch.
- **Communicate with your team** if you notice someone is working on a similar part of the codebase.

#### How to resolve merge conflicts:

- When you encounter a merge conflict, Git will mark the conflicts in the affected files. You need to manually resolve these conflicts by deciding which changes to keep.
- After resolving the conflicts, you need to add the files to staging and continue the merge or rebase process.
   ```bash
   git add <file-with-conflict>
   git rebase --continue
   ```

### 2. Divergence between local and remote branches:

Sometimes, your local branch may get too far behind or ahead of the remote branch, causing issues with pushing or pulling. To resolve this:

- **Rebase your local branch** with the remote `main` branch to avoid large differences.
- If necessary, use `git pull --rebase` to keep your local history clean.

### 3. Incorrect Merge or Broken Code:

If a merge is done incorrectly or the code breaks after a merge:

- **Git revert**: You can revert to a previous commit if necessary, or reset your branch to a known stable commit using `git reset`.
   ```bash
   git reset --hard <commit-hash>
   ```

### 4. Large or Multiple Commits:

Avoid creating a mess of commits in your branch. Instead:

- **Squash commits** when the feature is done. This condenses multiple commits into one clean commit when merging your feature into the main branch.
   ```bash
   git rebase -i main
   ```

- Use **clear commit messages**: Each commit should be self-contained and explain why a change was made.

## 5. Best Practices

Here are some best practices to follow when collaborating on a Git repository:

- **Feature Branches**: Always create a new branch for each feature or bug fix to ensure that `main` stays stable.
- **Keep Commit Messages Clear**: Write clear, concise, and informative commit messages to make it easier for everyone to understand changes.
- **Review PRs**: Before merging any changes to the main branch, review them as a team to ensure quality and avoid bugs.
- **Use Continuous Integration (CI)**: Set up automated testing to run on every PR to catch issues early.
- **Document**: Keep your team updated on important changes or procedures by writing documentation in the repository.

## 6. Automating with CI/CD

Use **Continuous Integration (CI)** tools (such as GitHub Actions, GitLab CI, or Jenkins) to automatically run tests and lint your code whenever someone pushes to a branch or opens a PR. This reduces the chance of broken code being merged and ensures the codebase stays clean.

## 7. Summary of Daily Process

1. **Before starting work**: Pull from `main`, switch to your feature branch.
2. **While working**: Commit changes regularly, push when done.
3. **End of day**: Pull latest changes from `main`, rebase your feature branch, resolve conflicts if any, push changes.
4. **Open a PR**: Once your task is complete, open a PR to merge into `main`.
5. **Review and merge PR**: Team reviews and merges PR into `main`.

By following this workflow, communicating with your team, and handling conflicts effectively, you’ll maintain a healthy and productive Git workflow.

---------------------------------------------------------------------------------------------------------------------------

**Notes:**
* Ensure consistent formatting and grammar.
* Maintain clarity in instructions to improve user comprehension.
* If possible, automate workflows for improved efficiency.

---

