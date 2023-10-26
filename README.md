[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/beryx-gist/badass-jlink-example-richtextfx/blob/master/LICENSE)
[![Build Status](https://img.shields.io/travis/beryx-gist/badass-jlink-example-richtextfx/master.svg?label=Build)](https://travis-ci.org/beryx-gist/badass-jlink-example-richtextfx)

## JDKManagerTool ## 

It can be used to manage your locally installed JDKs , download and Install New JDK , Set Environment Variable Path. No more trawling download pages, extracting archives, messing with _HOME and PATH environment variables.

Note : Only for Windows OS. For other OS use SDKMan - https://github.com/sdkman/sdkman-cli



### Tech

It is a Non Modular Application. [Reason  : The Project uses JLink for creating Platform Specific Runtime, and for a Modular Project, Jlink does not support loading of Automatic Modules,
Since we had to use many non Modular Jars in this Project , it is required to convert them to Modular Jars .That is an additional Task. To avoid this I have decided to make a Non Modular Project] 

* Java14
* OpenJFX 14
* AfterburnerFX - MVP Framework and Dependency Injection - https://github.com/AdamBien/afterburner.fx
* Gradle 6.5.1
* Badass Runtime Plugin - For Creating Platform Specific Executable/Installer - https://github.com/beryx/badass-runtime-plugin
* H2 Embedded Database 2.1.14
* MaterialFX 11.13.5
* Javatuples 1.2
* Apache Commons
* Jaxb API
* Hibernate 5.4.33 

### Use
**Release Binaries[Installer and Portable Versions] are uploaded.**


### Dependencies for Building

* Windows OS
* Wix Toolset - https://wixtoolset.org/releases/
* JDK 14
* Gradle 6.5.1


### Build Commands
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


