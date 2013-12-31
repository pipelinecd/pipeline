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
  - Micro-service theory presentations:
      - From Macro To Micro - Sam Newman  
        http://www.youtube.com/watch?v=2ofzdPXeQ6o
      - Implementing Micro service architectures - Fred George  
        https://vimeo.com/79866979
      - Micro Services: Java, the Unix Way - James Lewis  
        http://www.infoq.com/presentations/Micro-Services

## Todo
- Determine universal dataformat and protocol for the micro-services to communicate, candidates are:
    - text streams
    - atom/json over http
    - ..?

# Design Roadmap

Namings in this roadmap are work-in-progress, just like the designs

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
(now called `pipe` or `pipe-runner`)

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
```
                                         +-----------------------+
                                         |      pipe-poll        |
                                         |-----------------------|
                                         |              CSV      |
                                         |      SVN+-+    +      |
                                         |           |    |      |
                 Gitlab webhook          |           v    v      |
                         +               |       +--+Providers   |
                         |               |       |               |
        GitHub webhook   |               |  +-----------+        |
                   +     |  +--------------+| pipe-poll |        |
                   |     |  |            |  +-----------+        |
                   +--+  |  |            +-----------------------+
                      |  |  |
          +-----------|--|--|------------------------------------------+
          |           |  |  |    pipe-listen                           |
          |-----------|--|--|------------------------------------------|
          |           |  |  |                                          |
          |           v  v  v                                          |
          |          +-------------+                                   |
          |          | pipe-listen +-------------+Providers            |
          |          +--------+----+               +  +  +             |
          |                   |                +---+  |  +-----+       |
          |                   |                |      |        v       |
          |                   |                v      |    Pipe-poller |
          |                   |             GitHub    |                |
          |                   |                       v                |
          |                   |                      Gitlab            |
          |                   |                                        |
          +-------------------|----------------------------------------+
                              |
                              |                       +-----------------------------------------+
                              |                       |             pipe-monitor                |
                              v                       |-----------------------------------------|
          +----------------------------------+        | +---------------+                       |
          |    event-queue / message-bus     |<---------+ pipe-monitor  +-----+Publishers       |
          +----------------------------------+        | +---------------+         +    +        |
                  ^                    ^              |                           |    v        |
                  |                    |              |                           v    Graphite |
                  |                    |              |                       Ganglia           |
 +----------------|---------+          |              +-----------------------------------------+
 | pipe-messenger |         |          |
 |----------------|---------|    +-----|-----------------------------------+
 |        +-------+------+  |    |     |       pipe-worker                 |
 |        |pipe-messenger|  |    |-----|-----------------------------------|
 |        +-----+--------+  |    |     |                                   |
 |              |           |    |  +--+--------+                          |
 |              +           |    |  |pipe-worker+-------+scm-providers     |
 |          Publishers      |    |  +-+---------+            +  +  +       |
 |          + +   +  +      |    |    |                    +-+  |  +-+     |
 |       +--+ |   |  +-+    |    |    |                    v    |    v     |
 |       v    |   |    v    |    |    |                  Git    v    CSV   |
 |  Campfire  |   |   Email |    |    |                        SVN         |
 |            |   |         |    |    |                                    |
 |            v   v         |    +----|------------------------------------+
 |       Gitlab  GitHub     |         |
 +--------------------------+         |
                                   +--|---------------------+
                                   |  | pipe-runner         |
                                   |--|---------------------|
                                   |  |                     |
                                   |  +>                    |
                                   |                        |
                                   +------------------------+
```

(diagrams simply drawn with http://www.asciiflow.com)