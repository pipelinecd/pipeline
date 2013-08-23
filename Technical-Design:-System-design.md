# Architecture Principles

- Keep it Simple, Stupid
- Don't Repeat Yourself
- Follow the Unix Philosophy:

  > Write programs that do one thing, and do it well. Write programs that work together.    
  > -- http://www.linfo.org/unix_philosophy.html

  Each program being a micro-service of the complete picture.
- Each micro-service:
    - Has its own git repo
    - Has its own versioning/release 
    - Exposes:
        - Metrics
        - Logging
        - API
    - Packaged as a single executable jar
    - Installable as you would with any other application, via an OS-dependant package, incl:
        - its configuration
        - its command script
        - its OS dependend "start as daemon" scripts (eg. unix rc.d scripts)

## Todo
- Determine universal dataformat and protocol for the micro-services to communicate, candidates are:
    - text streams
    - atom/json over http
    - ..?

# Design Roadmap

## v1
Starting with a single application that does it all in a single process, calling external commands from the `Runner`.
```
 +---------------------------------------------------+
 |  Client                                           |
 |---------------------------------------------------|
 |                        +----+                     |
 |  +------------+  uses  |    | uses  +----------+  |
 |  | Groovy DSL |<-------+Main+------>|  Output  |  |
 |  +--------+---+        +-+--+       +----------+  |
 |           |              |                        |
 |           |uses          |uses                    |
 |           |              |                        |
 |  +--------v--------------v---------------------+  |
 |  |                Command-Query API            |  |
 |  +---------------------------------------------+  |
 |                          ^                        |
 |                          |impl.                   |
 |                          |                        |
 |  +-----------------------+---------------------+  |
 |  |                     Runner                  |  |
 |  +---------------------------------------------+  |
 +---------------------------------------------------+
```

## v2
Start moving out the `Runner` to its own application, introducing a `Coordinator` to coordinate the runner processes.
```
 +----------------------------------------+
 |  Client                                |
 |----------------------------------------|
 |                  +----+                |
 |  +------------+  |    |  +----------+  |
 |  | Groovy DSL |  |Main|  |  Output  |  |
 |  +------------+  |    |  +----------+  |
 |                  |    |                |
 |  +---------------+----+-------------+  |
 |  |          Command-Query API       |  |
 |  +-----------------+----------------+  |
 |                    |                   |
 |  +-----------------v----------------+  |
 |  |            Coordinator           |  |
 |  +-----------------+----------------+  |
 +--------------------|-------------------+
                      |
    +-----------------v----------------+
    |              Runner(s)           |-+
    +----------------------------------+ |+
     +-----------------------------------+|
      +-----------------------------------+
```

## v3
Move out the `Coordinator` to its own application as it proved to work in v2.
```
 +----------------------------------------+
 |  Client                                |
 |----------------------------------------|
 |                  +----+                |
 |  +------------+  |    |  +----------+  |
 |  | Groovy DSL |  |Main|  |  Output  |  |
 |  +------------+  |    |  +----------+  |
 |                  |    |                |
 |  +---------------+----+-------------+  |
 |  |          Command-Query API       |  |
 |  +-----------------+----------------+  |
 |                    |                   |
 +--------------------|-------------------+
                      |
    +-----------------v----------------+
    |            Coordinator           |
    +-----------------+----------------+
                      |
    +-----------------v----------------+
    |              Runner(s)           |-+
    +----------------------------------+ |+
     +-----------------------------------+|
      +-----------------------------------+
```

## v..
...

(diagrams simply drawn with http://www.asciiflow.com)