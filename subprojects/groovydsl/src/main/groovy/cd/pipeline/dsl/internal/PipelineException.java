package cd.pipeline.dsl.internal;

public class PipelineException extends RuntimeException {
    public PipelineException() {
        super();
    }

    public PipelineException(String message) {
        super(message);
    }

    public PipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineException(Throwable cause) {
        super(cause);
    }
}
