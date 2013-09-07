package org.pipelinelabs.pipeline.listen.api;

public interface Provider<T extends Notification> {

    /**
     * Get the name of this provider.
     *
     * @return Name of this provider
     */
    String getName();

    /**
     * Get the name of this provider, to be used in the uri.
     * <p/>
     * Must be pass the following regex <code>^[a-z]*$</code>.
     *
     * @return Name of this provider, to be used in the uri
     */
    String getNameForUri();

    /**
     * Type of notification this provider supports.
     *
     * @return Supported type of notification
     */
    Class<T> supports();
}
