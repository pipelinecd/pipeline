package cd.pipeline.listen.core.git

class GithubUtils {

    private static final String SSH_URL_FORMAT = 'git@github.com:%s.git'

    static String toGithubSshUrl(String uri) throws URISyntaxException {
        return toGithubSshUrl(new URI(uri))
    }

    static String toGithubSshUrl(URI uri) {
        return String.format(SSH_URL_FORMAT, cleanUriPath(uri))
    }

    private static String cleanUriPath(URI uri) {
        def path = uri.path
        return path.replaceAll(/\/+/, '/')
    }
}
