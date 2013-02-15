## What's wrong with existing CI tools
I hear you think "Another one? There are so many existing ones already!?!" and you're completely right. But if you have ever managed multiple projects/jobs in any existing CI tool, you've probably noticed that the existing tools:

* Force you to copy jobs even though they only differ in some specific configuration properties
* That provide "template"-based job configurations for minimizing job configuration duplication, often only only up to a specific point. Still forcing you to manually verify all jobs that extend/use a template that you've updated to see if the change is applied correctly, which mostly is not the case and thereby loosing the benefits of the template-base job configuration
* Except Travis-CI as far as I know, are mostly GUI-based for configuring jobs. Some provide a REST-api, a CLI tool and/or provide scripting functionality for configuring and controlling the jobs from outside the GUI of the tool meaning you have to write your own tools/scripts around your CI tool to make the tool easier to work for your situation
* Provide no way of experimenting:

  * Plugin upgrade? How can you test if a new plugin version still works as the previous one? Upgrade, restart server, start builds and wait for any failing builds...? Sadly this is the reality for existing tools and it's really not uncommon for new plugin versions to be broken!
  * Using svn, maven, ant, gradle, git, or any other external tools to get your job work? How do you control those tools? Can you have multiple versions of the same tool? Can you really control the environment your job is executed in/on? As far as I've found noone of the existing tools, that you can run in-house, help with this. Travis-CI does good job of providing jobs with specific versions of tools, but then again, they fully control their environments (there is no in-house version of Travis-CI)
* Have a lot of plugins. Are these plugins really needed? Do they provide some special functionality? Or are they really just some extra configuration options for your jobs, wrappers around external (CLI) tools or visualizing a reporting file from a specific tool
* Provide mostly no change history on job configuration. Configuring jobs can be tricky and often doesn't work as expected the first way around, in existing tools this mean clicking around the GUI (or write tools/scripts around provided API' where available). Something worked a sec ago, it doesn't now, what did you change? No way of telling

## Practice what we preach
For our code and infrastructure we've best practices like:
* Configuration-as-Code
* Infrastructure-as-Code
* Version Control
* Keep It Simple Stupid
* Don't Repeat Yourself
* Single Responsibility Principle

And we focus on applying this practices as good and useful as possible. But it seems like when it comes to CI, all this is thrown overboard. We just accept these CI tools which do not follow the same best practices. While these CI tools automate most of the things that happens with our code and infrastructure til it's on production (and beyond?). Isn't this a bit crooked/awry/weird?

## This CI server
So because of all the things above, I want to set things right by developing this new CI server.

### Why write my own
I've looked at many different CI tools and, where available, I've looked at their code, played with their GUI's and inspected their directory structures. And I came to the conclusion that those tools are simply implemented differently in a way it's very hard to make right (in my opinion of course). So I've decided to start with a clean-slate to not have to think about any backwards-compatibility issues of any kind and just go for the best solution possible with the features we want.
I'm writing this tool on the JVM by choice, currently in Java but the JVM provides the possibility to use the best language for the job. Meaning I could be using Groovy in some parts, JRuby in others, Jython, Scala, or any other language available on the JVM.

### Focus points
In summary the focus points of this CI server:
* Everything version-controlled:
  * Configuration-as-Code
  * Infrastructure-as-Code
  * Audit-able
* Server runs as daemon without any GUI:
  * The daemon provides an API
  * A CLI client will be created that uses the provided daemon API
  * A Web GUI will be created that uses the provided daemon API
* Without interrupting any of your teams, experiment with existing job configurations:
  * Freely
  * With new versions of plugins
  * With new versions of external tools
  * With new configurations of external tools
* Have scriptable job configurations to minimize the use of plugins
* Have plugins that really provide special functionality
* Distributed (in-house and/or in the cloud)
* Lightweight
* Provides hooks to monitor the daemons