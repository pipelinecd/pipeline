package nl.ikoodi.io.cy.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;
import nl.ikoodi.io.cy.runner.PipelineScriptRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Parameters(
        commandNames = {RunCommand.NAME}
        , commandDescription = "Run, baby run!"
)
public class RunCommand implements Command {
    public static final String NAME = "run";

    @Parameter(
            description = ""
            , listConverter = FileConverter.class
            , required = true
            , arity = 1
    )
    private List<File> files = new ArrayList<File>(2);

    private PrintStream outputConsumer;

    public RunCommand(final PrintStream outputConsumer) {
        this.outputConsumer = outputConsumer;
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
        final PipelineScriptRunner pipeline = new PipelineScriptRunner(outputConsumer, script);
        pipeline.run();
    }
}
