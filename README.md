# StatusInformation

[![Execution](https://github.com/bensofficial/StatusInformation/actions/workflows/execution.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/execution.yml)
[![Compilation](https://github.com/bensofficial/StatusInformation/actions/workflows/compilation.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/compilation.yml)
[![Tests](https://github.com/bensofficial/StatusInformation/actions/workflows/tests.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/tests.yml)
[![CodeQL](https://github.com/bensofficial/StatusInformation/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/bensofficial/StatusInformation/actions/workflows/codeql-analysis.yml)

Automated sending of status information

### Executing and Building
Running tests:
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
mvn clean compile assembly:single
````
#### For execution with GitHub Actions
For execution with GitHub Actions you can add the following values as secrets in GitHub:
- ``DSB_USERNAME``
- ``DSB_PASSWORD``
- ``TELEGRAM_CHANNEL_ID``
- ``TELEGRAM_BOT_TOKEN``

After building you can use these as a parameter:
````yml
        env: # Environment variable
          DSB_USERNAME: ${{ secrets.DSB_USERNAME }}
          DSB_PASSWORD: ${{ secrets.DSB_PASSWORD }}
          TELEGRAM_CHANNEL_ID: ${{ secrets.TELEGRAM_CHANNEL_ID }}
          TELEGRAM_BOT_TOKEN: ${{ secrets.TELEGRAM_BOT_TOKEN }}
        run: java -jar target/StatusInformation-1.0.0-jar-with-dependencies.jar $DSB_USERNAME $DSB_PASSWORD $TELEGRAM_CHANNEL_ID $TELEGRAM_BOT_TOKEN
````
### Update for new version
See [here](https://semver.org) for additional information of version naming.

1. ``pom.xml``
2. ``.github/workflows/execution.yml``
3. ``README.md``
4. ``src/org/benjaminschmitz/statusinformation/Configuration.java``
5. Create new release
### Additional information
- [CODE_OF_CONDUCT.md](https://github.com/bensofficial/StatusInformation/blob/main/.github/CODE_OF_CONDUCT.md)
- [CONTRIBUTING.md](https://github.com/bensofficial/StatusInformation/blob/main/.github/CONTRIBUTING.md)
- Feel free to contact the project manager: dev@benjamin-schmitz.org
 
The project started on July 9 2021 by [@bensofficial](https://github.com/bensofficial).
