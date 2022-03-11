# StatusInformation

[![Execution](https://github.com/bensofficial/StatusInformation/actions/workflows/execution.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/execution.yml)
[![Compilation](https://github.com/bensofficial/StatusInformation/actions/workflows/compilation.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/compilation.yml)
[![Tests](https://github.com/bensofficial/StatusInformation/actions/workflows/tests.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/tests.yml)
[![CodeQL](https://github.com/bensofficial/StatusInformation/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/codeql-analysis.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4a362a6b8a5d496f93200f81d5a62446)](https://www.codacy.com/gh/bensofficial/StatusInformation/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bensofficial/StatusInformation&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/4a362a6b8a5d496f93200f81d5a62446)](https://www.codacy.com/gh/bensofficial/StatusInformation/dashboard?utm_source=github.com&utm_medium=referral&utm_content=bensofficial/StatusInformation&utm_campaign=Badge_Coverage)

Automated sending of status information

## Executing and Building
Running tests and jacoco:
````Shell
mvn test
````

Running SpotBugs:
````Shell
mvn clean compile
mvn spotbugs:spotbugs spotbugs:gui
````

Building jar:
````Shell
mvn clean install
````
Executing the jar: (_Copying the jar is relevant due to the location of the configuration file._)
````Shell
cp target/StatusInformation-1.0-SNAPSHOT.jar .
java -jar StatusInformation-1.0-SNAPSHOT.jar
````

### For execution with GitHub Actions
For execution with GitHub Actions you can add your configuration file as a secret in GitHub:
Then you can execute the application like normal.

## Update for new version
See [here](https://semver.org) for additional information of version naming.

1.	``pom.xml``
2.	``.github/workflows/execution.yml``
3.	``README.md``
4.	``src/org/benjaminschmitz/statusinformation/Configuration.java``
5.	Create new release

## Additional information
- [CODE_OF_CONDUCT.md](https://github.com/bensofficial/StatusInformation/blob/main/.github/CODE_OF_CONDUCT.md)
- [CONTRIBUTING.md](https://github.com/bensofficial/StatusInformation/blob/main/.github/CONTRIBUTING.md)
- Feel free to contact the project manager: dev@benjamin-schmitz.org

Thanks to [celll](http://celll.de/) for their holidays api.
 
The project started on July 9 2021 by [@bensofficial](https://github.com/bensofficial).

