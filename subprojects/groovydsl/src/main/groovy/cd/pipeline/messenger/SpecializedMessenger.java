package cd.pipeline.messenger;

public interface SpecializedMessenger extends Messenger {
    boolean accepts(MessageContext context);
}
