[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/beryx-gist/badass-jlink-example-richtextfx/blob/master/LICENSE)
[![Build Status](https://img.shields.io/travis/beryx-gist/badass-jlink-example-richtextfx/master.svg?label=Build)](https://travis-ci.org/beryx-gist/badass-jlink-example-richtextfx)

## WorkspaceManagerTool ## 

Application that manages your Java Environments , Workspaces , IDEs ,SDKs .etc easily.
No more trawling download pages, extracting archives, messing with _HOME and PATH environment variables.

Note : Only Windows OS suppored.



### Tech
* Java14
* OpenJFX 14
* Gradle 6.5.1
* Badass Runtime Plugin - For Creating Platform Specific Executable/Installer
* H2 Embedded Database 2.1.14
* MaterialFX 11.13.5
* Javatuples 1.2
* Apache Commons
* Jaxb API
* Hibernate 5.4.33 


### Dependencies for Building

* Windows OS
* Wix Toolset - https://wixtoolset.org/releases/
* JDK 14
* Gradle 6.5.1



### Use
**Run Directly [No need of Wix Toolset to be installed]:**
```
gradlew run
```
**Creating platform-specific application installers: [Creates Windows Executable and Installers]**
```
gradlew jpackage
```

## Development

Want to contribute? Great! Thank you for showing interest in this Project.


## License

Apache 2.0

**Free Software, Hell Yeah!**


