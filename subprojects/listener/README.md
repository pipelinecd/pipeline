Pipeline Listener
=================

Receives and queues service hook notifications, like `push` events from GitHub.

This listener supports the following provider:

- github.com

Usage
-----

Pipeline Listener (`pipe-listener`) provides a HTTP REST API that providers like GitHub can notify. The API is non-blocking, after basic verification of the notification message, the API will return a success (HTTP 204) or failure (HTTP 422) status. The notification message will be queued for processing a.s.a.p. by its processor.

### POST /providers/github

GitHub provider, supports the GitHub WebHook format as explained on [Post-Receive Hooks](https://help.github.com/articles/post-receive-hooks)
