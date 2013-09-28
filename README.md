Pipeline
========

A Continuous Delivery (CD) tool, which we and all stakeholders of the CD pipeline love to use. Licensed under the [MIT License][0]

> free, open-source  
> Continuous Delivery tool  
> that's simple to use for hobbyists,  
> flexible enough for startups  
> and powerful enough for the enterprise

Find the latest release on the [releases][6] page. And follow the [installation][8] and [configuration][9] documentation to get started.

State of the project
--------------------
This project is currently in a very early state of development. Development happens in a LEAN way, bit by bit adding features and improving based on user experiences and feedback. Give your feedback at the [user/dev forum][3].

Features
--------
- From the ground-up, pipeline-based
- Pipeline configuration in pipeline domain language (DSL)
- Version controlled pipeline configuration
- Support for public and private git repositories
    - hosted on [Github](http://github.com), triggered via [webhook][7]

Resources
---------
- Pipeline Homepage -- http://pipelinelabs.org
- GitHub Organization -- http://github.pipelinelabs.org
- Documentation -- http://docs.pipelinelabs.org
- User/Dev Forum -- [http://forum.pipelinelabs.org][3]
- Issue Tracker -- http://issues.pipelinelabs.org

Follow us on Twitter [@pipelinelabs](https://twitter.com/pipelinelabs)

If you have any ideas, comments or questions, please direct them to the [user/dev forum][3].
All feedback is appreciated!

### Presentations
- Ignite talk "Continuous Integration and Delivery tools, do we really like using them?"
    - [Preparation run recording][4]
    - [@DevOpsDays Amsterdam, June 14th 2013 recording][5]

Roadmap
-------
Some ideas on epics/topics we want to have implemented along the way, in no particular order:

- Configuration service that provides generic configuration to all pipelines
    - Also supporting a secure way for sensitive configuration data
- Native packages for different OS'es, for easy installation
    - Also providing scripts to run services as system daemons/services
- Extensive logging and monitoring functionality
- Extend pipeline configuration with environment and execution information for simpler maintenance by not depending on specific server configurations, the pipeline takes care of everything in a versioned manner. For example:
    - Environment variables to make available to the whole pipeline, specific stages or specific commands
    - System applications and versions which are required to run the pipeline are installed and configured automatically
    - ...
- Different GUI's, for the different types of users and usages
    - All communicating with one single API against the system
- Support for different authentication and authorization schemes

Building Pipeline
--------------
The only prerequisite is that you have JDK 7 or higher installed.

After cloning the project, type `./gradlew clean build` (Windows: `gradlew clean build`). All build dependencies,
including [Gradle](http://www.gradle.org) itself, will be downloaded automatically (unless already present).

Contributing
------------
Feel free to contribute! Follow these steps:

First time only:

- Fork the repo (`upstream`) to your GitHub account (`origin`).
- Clone `origin` as your `local` repo

Every other time:

- Update both `origin` and `local` repos from `upstream`
- Create new branch for a feature or bug fix
- Commit often :)
- Once when work is done, push local changes to your `origin`
- Send us a pull request (PR)

We will pickup up from there :)

Special thanks to
-----------------
- [Andrew Oberstar](http://www.andrewoberstar.com) for being the very first code contributor and all-round providing great support and feedback
- [Martin Kovachki](http://www.linkedin.com/in/martinkov) for awesome logo designs
- [Tricode](http://www.tricode.nl) for sponsoring developer-time and resources


[0]: http://github.pipelinelabs.org/blob/master/LICENSE
[3]: http://forum.pipelinelabs.org
[4]: http://www.youtube.com/watch?v=shF_v5shzjU
[5]: http://www.youtube.com/watch?v=-StobwMgRNE
[6]: https://github.com/pipelinelabs/pipeline/releases
[7]: https://help.github.com/articles/post-receive-hooks
[8]: http://docs.pipelinelabs.org/User-Guide%3A-Set-up-with-Central-Repository
[9]: http://docs.pipelinelabs.org/User-Guide%3A-Project-Pipeline-Configuration