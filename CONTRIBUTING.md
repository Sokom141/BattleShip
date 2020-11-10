# Contributing to BattleShip

### Welcome!
First off, thank you for considering contributing to BattleShip, it's nice to have you around!


### Why we have these guidelines?
Following these guidelines helps to  make the contribution process easy and effective for everyone involved.


### What kinds of contributions are we looking for?
BattleShip is an open source project, and we are open to receive and discuss contributions from the community! 

There are many ways to contribute, from writing tutorials, improving the documentation, submitting bug reports and feature requests or writing code which can be incorporated into the project itself.

# Ground Rules
### Expectations for behavior
Responsibilities:
* Ensure cross-platform compatibility for every change that's accepted. Windows, Mac, Linux.
* Ensure that code that goes into the project follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
* Create issues for any major changes and enhancements that you wish to make. Discuss things transparently and get community feedback.
* Be welcoming to newcomers and encourage diverse new contributors from all backgrounds. 

# Getting started
## Your First Contribution
Unsure where to begin contributing to our project? You can start by looking through [these beginner issues](https://github.com/Sokom141/BattleShip/labels/good%20first%20issue).
> Beginner issues - issues which should only require a few lines of code, and a test or two.

### Link to resources for people who have never contributed to open source before
Here are a couple of friendly tutorials: 
* [Make a pull request](http://makeapullrequest.com/)
* [First timers only](http://www.firsttimersonly.com/)
* [How to Contribute to an Open Source Project on GitHub](https://egghead.io/series/how-to-contribute-to-an-open-source-project-on-github).

### General workflow
1. [Fork](http://help.github.com/fork-a-repo/) the project, clone your fork, and configure the remotes:
   ```bash
   # Clone your fork of the repo into the current directory
   git clone https://github.com/<your-username>/<repo-name>
   # Navigate to the newly cloned directory
   cd <repo-name>
   # Assign the original repo to a remote called "upstream"
   git remote add upstream https://github.com/Sokom141/BattleShip
   ```
   
2. Get the latest changes from upstream:
   ```bash
   git checkout master
   git pull upstream master
   git checkout develop
   git pull upstream develop
   ```
   
3. Create a new topic branch (off the main project `develop` branch) to
   contain your feature, change, or fix:
      ```bash
      git checkout -b <topic-issue-branch-name> develop
      ```
   
4. Make sure to update, or add to the tests when appropriate. Build the project with Maven and check everything 
   works correctly.
   
5. If you added or changed a feature, make sure to document it accordingly.

6. Push your topic branch up to your fork:
   ```bash
   git push origin <topic-issue-branch-name>
   ```
   
7. [Open a Pull Request](https://help.github.com/articles/using-pull-requests/)
    with a clear title and description.

8. Make sure all checks are valid and then wait for a reviewer!

   If we suggest changes then:

    * Make the required updates.

    * Re-run the test suite to ensure tests are still passing.

    * Rebase your branch and force push to your GitHub repository (this will update your Pull Request):
    ```
    git rebase develop -i
    git push -f
    ```


At this point, you're ready to make your changes! Feel free to ask for help, everyone is a beginner at first :smile_cat:

If a maintainer asks you to "rebase" your PR, they're saying that a lot of code has changed, and that you need to update your branch, so it's easier to merge.



## How to submit a contribution.
For something that is bigger than a one or two line fix:
1. Create your own fork of the code
2. Do the changes in your fork
    * Make a new branch starting from the `develop`
    * The name branch should follow this convention: `topic-issue-short-description`
        * For example if you are fixing a bug that was presented in the issue #16 you should name your branch:
            * `bug-16-user-avatar`
3. If you like the change and think the project could use it:
    * Be sure you have followed the code style for the project and added the necessary documentation.
    * Send a pull request on the `develop` branch with a meaningful description.

Small contributions such as fixing spelling errors, where the content is small enough, can be submitted by a contributor as a patch or without opening an issue.

As a rule of thumb, changes are obvious fixes if they do not introduce any new functionality or creative thinking. 
As long as the change does not affect functionality, some likely examples include the following:
* Spelling / grammar fixes
* Typo correction, white space and formatting changes
* Comment clean up
* Bug fixes that change default return values or error codes stored in constants
* Adding logging messages or debugging output
* Changes to ‘metadata’ files like .gitignore, build scripts, etc.
* Moving source files from one directory or package to another

# How to report a bug

### Security disclosures
If you find a security vulnerability, do NOT open an issue. Email mattiapizzolitto141@gmail.com or kevchi9@yahoo.it instead.

In order to determine whether you are dealing with a security issue, ask yourself these two questions:
* Can I access something that's not mine, or something I shouldn't have access to?
* Can I disable something for other people?
If the answer to either of those two questions are "yes", then you're probably dealing with a security issue. 

Note that even if you answer "no" to both questions, you may still be dealing with a security issue, so if you're unsure, just email us.

### How to file a bug report.
When filing an issue, make sure to answer these five questions:
1. What version of Java are you using?
2. What operating system and processor architecture are you using?
3. What did you do?
4. What did you expect to see?
5. What did you see instead?

# How to suggest a feature or enhancement
Open an issue on our issues list on GitHub which describes the feature you would like to see, why you need it, and how it should work.

# Code review process
### How a contribution gets accepted after it’s been submitted
When you submit a pull request there are some checks in place to help maintain the project in a good state.
Before merging any changes these steps must be respected:
* A reviewer should inspect your pull request and mark it as approved.
* The CI build with Maven should work, and the CodeQL inspector shouldn't mark any problematic code.

# Code, commit message and labeling conventions
### Code Style
Try to follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
### Commit messages
Treat commit messages as an email message that describes what you changed and why.

Use English for every message and try to be as explicit as possible.

The last part of the commit log should contain all "external references", such as which issues were fixed.
