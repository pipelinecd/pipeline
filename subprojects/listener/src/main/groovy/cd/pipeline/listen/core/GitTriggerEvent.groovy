package cd.pipeline.listen.core

class GitTriggerEvent {

    final String url
    final String branch

    GitTriggerEvent(String url, String branch) {
        this.branch = branch
        this.url = url
    }
}
