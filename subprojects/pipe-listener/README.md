Pipeline Listener
=================

Receives and queues service hook notifications, like `push` events from GitHub.

This listener supports multiple providers, GitHub being the first.

Usage
-----

Pipeline Listener (`pipe-listener`) is provides a HTTP REST API that providers like GitHub can notify. The API is non-blocking, after basic verification of the notification message, the API will return a success (HTTP 204) or failure (HTTP 422) status. The notification message will be queued for processing a.s.a.p. by its processor.

### GET /

Shows a simple overview page with:

- Loaded providers and their details
- Queued notifications (with basic details)
- Amount processed notifications since start-up

### POST /providers/github

GitHub provider, supports the GitHub WebHook format as explained on [Post-Receive Hooks](https://help.github.com/articles/post-receive-hooks)
