# Contributing
### Creating/taking issues
When contributing to this repository, in most cases an issue should be used. If you are creating an issue, please stick to the **[issue creation templates](.github/ISSUE_TEMPLATE)**.
Once you have created your issue or taken an existing issue, please leave a comment on it so other people know that it is being worked on. Similarly, if you decide to drop the issue, it is appreciated if you comment to let people know that it is avaiable again (also include any findings you may have that may help others to solve the issue)

### Committing
When creating a commit, you should follow the standards below - [commit messages](#commit-messages).

### Raising pull requests
After making the necessary changes, you can raise a pull request. Pull requests should follow the **[pull request templates](.github/PULL_REQUEST_TEMPLATE)** to make it easy to keep track of what is included in it. Pull requests should always succesfully build (unless specifically told otherwise) - this is to ensure the mainline is not broken for others who wish to use Máni or contribute to it.
If a pull request includes tests and these tests do not pass, please ensure an issue is logged to make them pass.

---
#### Commit messages
To make it obvious what each commit includes and/or fixes, please stick to the following structure when writing the commit messages (take care to inlcude line spaces where shown)


    HEADER - #issueNo

    Detailed description of changes

    FOOTER

- **HEADER** - The HEADER should be a short message describing the change (often the same as the issue name you are fixing)
  - e.g. Fix ClassCastException when opening file
- **#issueNo** - The issueNo must be included if this commit is directly working on a logged issue - this is not always the case so is not always required. Not to be confused with pull request number
- **Body of message** - The main body of the commit message should be a breif summary of why the change was needed and a description of what change was made.
  - e.g. Opening file was throwing ClassCastException - String cannot be cast to File. Updated file construction to take string as filename parameter.
- **FOOTER** - The FOOTER should be included to link to any other issues that should also be fixed by your changes (e.g issues to make tests pass) or other issues it contributes to.
  - e.g. Fixes - "Opening file throws error" (#1)


---
**If you have any questions about any of the procedures for contributing, please don't hesitate to contact us!**
