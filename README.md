![GitHub repo size](https://img.shields.io/github/repo-size/Alin0u/KI_Newsletter)
![GitHub license](https://img.shields.io/github/license/Alin0u/KI_Newsletter)
![GitHub top language](https://img.shields.io/github/languages/top/Alin0u/KI_Newsletter)
![GitHub last commit](https://img.shields.io/github/last-commit/Alin0u/KI_Newsletter)

# KI_Newsletter

#### Contents

- [KI_Newsletter](#aki_newsletter)
- [System Details](#system-details)
- [Branching Model and Naming](#branching-model-and-naming)
- [Commit Messages](#commit-messages)
- [Versioning](#versioning)
- [Templates](#templates)
- [Authors](#authors)
- [Terms of Use](#terms-of-use)

## System Details

Version 1.0.0

Programming languages: JavaScript, HTML, CSS

Frameworks: Spring, Angular

## Installation

### Dependencies
- node Version 20.9.0
- Maven 3.9.5
- Spring 3.1.5

### Package Installation

The program can be installed locally using the following instructions:
[Installation](INSTRUCTIONS.md)

## Branching Model and Naming

We use a simplified version of [Gitflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)

| Name        | Description                                                                                                 |
|:------------|:------------------------------------------------------------------------------------------------------------|
| `main`      | The master branch contains the production code and stores the official release history.                     |
| `develop`   | The development branch contains pre-production code and serves as an integration branch for features.        |
| _`feature`_ | Each new feature should be placed in its own branch, which can be moved to the central repository for backup/collaboration. |

## Commit Messages

```
Format:
<type>(<scope>): <subject> [<issue number>]

Beispiel:
feat(Save Button): Implemented save button [MF-1]
```


The following types are used:

* ci: Changes to our CI configuration files or scripts
* docs: Documentation changes
* feat: A new feature
* fix: A bug fix
* refactor: Code changes to improve readability
* test: Adding or adjusting tests

_For more information:
[Angular Commit Message Guidelines](https://www.conventionalcommits.org/en/v1.0.0/)_

## Versioning

* Major Release: Change to the software (not compatible with previous versions)
* Minor Release: New functionality (backward compatible)
* Patch: Bug fix

Format: 1.0.0

## Templates
We used the following websites as aids and templates for our Readme/Project. A big thank you to the creators of these pages.

- [Open Source Readme](https://github.com/cfpb/open-source-project-template/blob/main/README.md)
- [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)
- [Versioning](https://semver.org/)
- [How to be a good Open Source Project Owner](https://www.freecodecamp.org/news/ultimate-owners-guide-to-open-source/)

In addition to the above-mentioned sites, we have also drawn inspiration from other GitHub repositories.

## Authors
Created by:
- [@smokisimon](https://github.com/smokisimon) (bissisi1)
- [@Alin0u](https://www.github.com/alin0u) (spangali)

## Contribution
This project was created as part of the project work for the part-time studies in the 7th semester at the Zurich University of Applied Sciences in Zurich.

## Terms of Use
[License](LICENSE)
