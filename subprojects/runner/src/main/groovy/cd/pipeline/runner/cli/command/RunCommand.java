package cd.pipeline.runner.cli.command;

import cd.pipeline.runner.cli.dsl.PipelineScriptRunner;
import cd.pipeline.runner.internal.ServiceRegistry;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Parameters(
        commandNames = {RunCommand.NAME}
        , commandDescription = "Run pipeline"
)
public class RunCommand implements Command {
    public static final String NAME = "run";

    @Parameter(
            description = "Pipeline configuration to run"
            , listConverter = FileConverter.class
            , required = true
            , arity = 1
    )
    private List<File> files = new ArrayList<File>(2);

    private final PrintStream outputConsumer;
    private final ServiceRegistry registry;

    public RunCommand(final PrintStream outputConsumer, ServiceRegistry registry) {
        this.outputConsumer = outputConsumer;
        this.registry = registry;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean canHandle(final String commandName) {
        return getName().equals(commandName);
    }

    @Override
    public void handle() {
        final File file = files.get(0);
        final String script;
        try {
            script = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            final String msg = String.format("Could not read script [%s]", file.getAbsolutePath());
            throw new IllegalArgumentException(msg, e);
        }
        final PipelineScriptRunner pipeline = new PipelineScriptRunner(registry, outputConsumer, script);
        pipeline.run();
    }
}
