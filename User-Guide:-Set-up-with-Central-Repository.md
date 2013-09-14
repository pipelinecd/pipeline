_THIS IS WORK-IN-PROGRESS, we're working towards having Pipeline function as described here_

Using Pipeline with a Central Repository
===============
_Currently only git-based repositories are supported_  
When a user pushes to a central code repository like git, a pipeline is triggered based on the configuration that is located within the that git repository.

```
    +----------+        +----------+
    |   User   |        |          |
    |          | +----> | git repo |
    | git push |        |          |
    +----------+        +-+--------+
                          |
                          | git post-hook calls
                          |   via web-call
                          |
 +------------------------|-----------+
 |                        |           |
 |                        v           |
 |  +----------+       +----------+   |
 |  | Pipeline |<------| Pipeline |   |
 |  | Runner   |       | Listener |   |
 |  +----+-----+       +----------+   |
 |       |                            |
 |       v                            |
 |    Sent status                     |
 |    email                           |
 |                 +------------------+
 |                 |Runs on one server|
 +-----------------+------------------+
```
The simple set-up requires that the central code repository supports post-hooks as these are executed after code is committed/pushed to the repository. Git is such type of code repository.

Requirements
============
- A Linux-based server to run Pipeline on  
  **Note:** This server must be accessible via HTTP(S) from your central code repository
  - Java 7+
  - Git _(as currently only git is supported)_
  - _(Windows platform currently not tested, therefor may or may not work)_
- A central code repository that supports post-hooks  
  eg. github.com
- Any tools your code requires for compiling, testing, etc (its pipeline)  
  **Note:** When working with Java code, it must currently support Java 7 as Pipeline itself and your project pipeline will use the same Java runtime

Installation
============
Installing Pipeline for this simple set-up is done as follows:

1. Download the Pipeline installation package
2. Unpack the installation package to a location of choice
3. Add `{unpack-location}/bin/` to your environment `PATH` variable
4. Symlink `{unpack-location}/etc/init.d/pipeline` to `/etc/init.d/pipeline`  
    ```
    ln -s {unpack-location}/etc/init.d/pipeline /etc/init.d/pipeline
    ```
5. Add Pipeline to your default init runlevel to auto-start Pipeline on server startup:  
    In Ubuntu:
    ```
    sudo update-rc.d /etc/init.d/pipeline defaults
    ```
6. Test if you can access Pipeline, by opening your browser to url:  
    [http://{ip}:{port}/status](http://{ip}:{port}/status)
7. Register the Pipeline post-hook with the central code repository:
  - For github this means, configure the Pipeline post hook url as `Service Hooks > Web Hook URLS`:  
        [http://{ip}:{port}/hooks/post](http://{ip}:{port}/hooks/post)
8. That's it! On your next push to the central code repository, your pipeline should be triggered

Configuration
=============
1. In the root of your code repository, create a `project.pipe` script
2. Configure the pipeline. 
- **Note:** Configuration is explained on [project pipeline configuration](User-Guide%3A-Project-Pipeline-Configuration)