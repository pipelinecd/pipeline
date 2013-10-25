package org.pipelinelabs.pipeline.dsl.internal;

import groovy.lang.Closure;
import org.pipelinelabs.pipeline.api.Pipeline;
import org.pipelinelabs.pipeline.config.DefaultPipeline;
import org.pipelinelabs.pipeline.dsl.AnnounceDsl;
import org.pipelinelabs.pipeline.dsl.MessengerDsl;
import org.pipelinelabs.pipeline.dsl.StageDsl;
import org.pipelinelabs.pipeline.event.PipeEvent;
import org.pipelinelabs.pipeline.messenger.MessageContext;
import org.pipelinelabs.pipeline.util.ConfigureUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultPipelineDsl implements InternalPipelineDsl {
    private final AnnounceDsl announce = new DefaultAnnounceDsl();
    private final MessengerDsl messenger = new DefaultMessengerDsl();
    private Set<InternalStageDsl> stages = new LinkedHashSet<>();
    private List<PipeEvent> events = new ArrayList<>();
    private boolean hasFailedStage = false;

    @Override
    public StageDsl stage(final String name, final Closure closure) {
        final InternalStageDsl stage = new DefaultStageDsl(name);
        if (hasFailedStage) {
            return stage;
        }
        try {
            ConfigureUtil.configure(stage, closure);
            stages.add(stage);
            events.add(new PipeEvent("ProjectName", "Success", stage.getDescription(), ""));
            return stage;
        } catch (PipelineException e) {
            hasFailedStage = true;
            events.add(new PipeEvent("ProjectName", "Failure", stage.getDescription(), e.getMessage()));
            return stage;
        }
    }

    @Override
    public void echo(final Object value) {
        System.out.print(value);
    }

    @Override
    public void echo(final String format, final Object... values) {
        System.out.printf(format, values);
    }

    @Override
    public Pipeline export() {
        final Pipeline pipeline = new DefaultPipeline();
        for (InternalStageDsl stage : stages) {
            pipeline.add(stage.export());
        }
        return pipeline;
    }

    @Override
    public AnnounceDsl announce(Closure closure) {
        ConfigureUtil.configure(announce, closure);
        for (MessageContext context : announce.toContexts()) {
            messenger.toMessenger().process(context, events.get(events.size() - 1));
        }
        return announce;
    }

    @Override
    public MessengerDsl messenger(Closure closure) {
        ConfigureUtil.configure(messenger, closure);
        return messenger;
    }
}
