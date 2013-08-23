Central repository use-case
===============
When a user pushes to a central git repository, a pipeline is triggered based on the configuration that is located within the that git repository.

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
